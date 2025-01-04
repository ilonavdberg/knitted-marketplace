package com.knitted.marketplace.services;

import com.knitted.marketplace.dtos.item.ItemRequestDto;
import com.knitted.marketplace.exception.exceptions.InvalidStatusChangeException;
import com.knitted.marketplace.exception.exceptions.ItemAlreadySoldException;
import com.knitted.marketplace.exception.exceptions.ItemPublicationValidationException;
import com.knitted.marketplace.exception.exceptions.RecordNotFoundException;
import com.knitted.marketplace.mappers.ItemMapper;
import com.knitted.marketplace.models.item.*;
import com.knitted.marketplace.repositories.ItemRepository;
import com.knitted.marketplace.utils.Parser;
import com.knitted.marketplace.utils.validation.ItemValidator;
import com.knitted.marketplace.utils.validation.ValidationResult;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(ItemRequestDto request) {
        Item item = ItemMapper.toItem(request);
        item.setStatus(ItemStatus.NOT_PUBLISHED); //set initial status

        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long id, ItemRequestDto request) {
        Item item = getItem(id);
        Item updatedItem = ItemMapper.toItem(request);

        item.setTitle(updatedItem.getTitle());
        item.setDescription(updatedItem.getDescription());
        item.setPrice(updatedItem.getPrice());
        item.setCategory(updatedItem.getCategory());
        item.setSubcategory(updatedItem.getSubcategory());
        item.setTargetgroup(updatedItem.getTargetgroup());
        item.setClothingSize(updatedItem.getClothingSize());
        item.addPhotos(updatedItem.getPhotos());

        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItemStatus(Long id, ItemStatus newStatus) {
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

            case SOLD:
                if (!item.getStatus().equals(ItemStatus.PUBLISHED)) {
                    throw new InvalidStatusChangeException(item.getStatus().toString(), newStatus.toString());
                }
                item.setStatus(ItemStatus.SOLD);
                break;
        }

        return itemRepository.save(item);
    }

    @Transactional
    public Item getItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
        Hibernate.initialize(item.getPhotos());
        return item;
    }

    @Transactional
    public Page<Item> getItemsForSale(String keyword, String category, String subcategory, String target, String priceRange, String sizes, Pageable pageable) {
        // create basic filters
        Specification<Item> spec = buildCommonSpecification(category, subcategory, priceRange, target, sizes);

        // add filter: only items for sale
        spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), ItemStatus.PUBLISHED));

        //add optional filter: search keyword
        if (!keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + keyword + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + keyword + "%")
                    ));
        }

        Page<Item> itemPage = itemRepository.findAll(spec, pageable);

        itemPage.getContent().forEach(item -> Hibernate.initialize(item.getPhotos()));

        return itemPage;
    }

    @Transactional
    public Page<Item> getItemsForShop(Long shopId, String status, String category, String subcategory, String priceRange, String target, String sizes, Pageable pageable) {
        // create basic filters
        Specification<Item> spec = buildCommonSpecification(category, subcategory, priceRange, target, sizes);

        // add filter: only items in shop with id
        spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("shop").get("id"), shopId));

        // add optional filter: item status
        if (!status.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), ItemStatus.fromString(status)));
        }

        Page<Item> itemPage = itemRepository.findAll(spec, pageable);

        itemPage.getContent().forEach(item -> Hibernate.initialize(item.getPhotos()));

        return itemPage;
    }

    private Specification<Item> buildCommonSpecification(
            String category,
            String subcategory,
            String priceRange,
            String target,
            String sizes
    ) {
        Specification<Item> spec = Specification.where(null);

        // Filter by category
        Category categoryEnum = Category.fromString(category);
        if (!category.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("category"), categoryEnum));
        }

        // Filter by subcategory
        if (!subcategory.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("subcategory"), Subcategory.fromString(subcategory)));
        }

        // Filter by price range
        if (priceRange.contains(",")) {
            String[] priceLimits = priceRange.split(",");
            Double minPrice = !priceLimits[0].isEmpty() ? Double.parseDouble(priceLimits[0]) : null;
            Double maxPrice = priceLimits.length > 1 ? Double.parseDouble(priceLimits[1]) : null;

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

        // Filter by target group
        if (categoryEnum.equals(Category.CLOTHING) && !target.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("targetgroup"), TargetGroup.fromString(target)));
        }

        // Filter by size
        if (categoryEnum.equals(Category.CLOTHING) && !sizes.isEmpty()) {
            List<ClothingSize> sizeList = Parser.toSizeList(sizes);
            spec = spec.and((root, query, criteriaBuilder) -> root.get("clothing_size").in(sizeList));
        }

        return spec;
    }

}
