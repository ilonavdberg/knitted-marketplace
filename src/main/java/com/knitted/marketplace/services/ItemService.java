package com.knitted.marketplace.services;

import com.knitted.marketplace.exception.exceptions.InvalidStatusChangeException;
import com.knitted.marketplace.exception.exceptions.ItemAlreadySoldException;
import com.knitted.marketplace.exception.exceptions.ItemPublicationValidationException;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.repositories.ItemRepository;
import com.knitted.marketplace.utils.validation.ItemValidator;
import com.knitted.marketplace.utils.validation.ValidationResult;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void createItem(Item item) {
        item.setStatus(ItemStatus.NOT_PUBLISHED);
        itemRepository.save(item);
    }

    public void updateItem(Long id, Item updatedItem) {
        Item item = getItem(id);

        item.setTitle(updatedItem.getTitle());
        item.setDescription(updatedItem.getDescription());
        item.setPrice(updatedItem.getPrice());
        item.setCategory(updatedItem.getCategory());
        item.setSubcategory(updatedItem.getSubcategory());
        item.setTargetgroup(updatedItem.getTargetgroup());
        item.setClothingSize(updatedItem.getClothingSize());
        item.addPhotos(updatedItem.getPhotos());

        itemRepository.save(item);
    }

    @Transactional
    public void updateItemStatus(Long id, ItemStatus newStatus) {
        Item item = getItem(id);
        if (item.getStatus() == ItemStatus.SOLD) {
            throw new ItemAlreadySoldException("Item has already been sold. Status cannot be changed.");
        }

        switch (newStatus) {
            case PUBLISHED:
                // Check if item has the correct status
                if (!item.getStatus().equals(ItemStatus.NOT_PUBLISHED)) {
                    throw new InvalidStatusChangeException(item.getStatus().toString(), newStatus.toString());
                }

                // Check if item meets the validation criteria
                ValidationResult check = ItemValidator.validateForPublishing(item);
                if (!check.isValid()) {
                    throw new ItemPublicationValidationException(id, check.errors());
                }

                item.setStatus(ItemStatus.PUBLISHED);
                break;

            case NOT_PUBLISHED:
                if (!item.getStatus().equals(ItemStatus.PUBLISHED)) {
                    throw new InvalidStatusChangeException(item.getStatus().toString(), newStatus.toString());
                }
                item.setStatus(ItemStatus.NOT_PUBLISHED);
                break;

            case ARCHIVED:
                item.setStatus(ItemStatus.ARCHIVED);
                break;
        }

        itemRepository.save(item);
    }

    @Transactional
    public Item getItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
        Hibernate.initialize(item.getPhotos());
        return item;
    }

    @Transactional
    public Page<Item> getItemsForSale(String category, String subcategory, String target, String priceRange, Pageable pageable) {
        // standard filter: only get published items
        Specification<Item> spec = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), ItemStatus.PUBLISHED));

        //optional filters
        if (!category.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category"), Category.fromString(category)));
        }

        if (!subcategory.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("subcategory"), Subcategory.fromString(subcategory)));
        }

        if (!target.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("targetgroup"), TargetGroup.fromString(target)));
        }

        if (priceRange.contains(",")) {
            String[] priceLimits = priceRange.split(",");
            Double minPrice = !priceLimits[0].isEmpty() ? Double.parseDouble(priceLimits[0]) : null;
            Double maxPrice = !priceLimits[1].isEmpty() ? Double.parseDouble(priceLimits[1]) : null;

            if (minPrice != null && maxPrice != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.between(root.get("price"), minPrice, maxPrice));
            } else if (minPrice != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            } else if (maxPrice != null) {
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
        }

        Page<Item> itemPage = itemRepository.findAll(spec, pageable);

        itemPage.getContent().forEach(item -> Hibernate.initialize(item.getPhotos()));

        return itemPage;
    }
}
