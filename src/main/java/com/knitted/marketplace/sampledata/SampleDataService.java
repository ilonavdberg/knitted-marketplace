package com.knitted.marketplace.sampledata;

import org.springframework.stereotype.Service;


@Service
public class SampleDataService {
    private final ShopDataGenerator shopDataGenerator;


    public SampleDataService(ShopDataGenerator shopDataGenerator) {

        this.shopDataGenerator = shopDataGenerator;
    }

    public void loadSampleData() {
        System.out.println("Initializing loading sample data...");
        System.out.println("Start loading shop data");
        shopDataGenerator.generate();
        System.out.println("Finished loading sample data...");
    }

//    private void generateShopData() {
//
//        Shop shop1 = new Shop();
//        shop1.setName("Knitting Handmade");
//        shop1.setDescription("Lorem ipsum odor amet, consectetuer adipiscing elit. Ipsum odio enim etiam venenatis et sagittis.");
//        System.out.println("starting with image mapping...");
//        shop1.setShopPicture(ImageMapper.toImage("static/sampledata/images/knitting_handmade.jpg"));
//        shopRepository.save(shop1);
//
//        Shop shop2 = new Shop();
//        shop2.setName("Knitting with Love");
//        shop2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
//        shop2.setShopPicture(ImageMapper.toImage("static/sampledata/images/knitting_with_love.jpg"));
//        shopRepository.save(shop2);
//
//        Shop shop3 = new Shop();
//        shop3.setName("B's Knits");
//        shop3.setDescription("Lorem ipsum odor amet, consectetuer adipiscing elit. Augue odio phasellus varius metus; malesuada consectetur. Leo magnis penatibus tristique; penatibus natoque class.");
//        shop3.setShopPicture(ImageMapper.toImage("static/sampledata/images/b_knits.jpg"));
//        shopRepository.save(shop3);
//    }

//    protected void generateItemData() {
//        List<Shop> shops = shopRepository.findAll();
//        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
//
//        for (int i = 0; i < 30; i++) {
//            Long id = (long) (i + 1);
//
//            Item item = new Item();
//            item.setTitle("Item " + i);
//            item.setDescription(loremIpsum);
//            item.setPrice(generateRandomPrice(10.0, 50.0));
//            item.setShop(shops.get(i % shops.size()));
//
//            // Reattach the Shop entity to the current session
//            Shop managedShop = entityManager.merge(shops.get(i % shops.size()));
//            item.setShop(managedShop);
//
//            Category category = pickRandomEnum(Category.class);
//            item.setCategory(category);
//            item.setSubcategory(pickRandomSubcategory(category));
//            item.setTargetgroup(pickRandomEnum(TargetGroup.class));
//            item.setClothingSize(pickRandomEnum(ClothingSize.class));
//            item.addPhotos(Collections.singletonList(ImageMapper.toImage("static/sampledata/images/item_photo_default.jpg")));
//
//            itemRepository.save(item);
//            itemService.updateItemStatus(id, ItemStatus.PUBLISHED);
//        }
//
//    }

//    private double generateRandomPrice(double min, double max) {
//        return ThreadLocalRandom.current().nextDouble(min, max);
//    }
//
//    private <E extends Enum<E>> E pickRandomEnum(Class<E> enumClass) {
//        E[] values = enumClass.getEnumConstants();
//        return values[ThreadLocalRandom.current().nextInt(values.length)];
//    }
//
//    private Subcategory pickRandomSubcategory(Category category) {
//        List<Subcategory> subcategories = new ArrayList<>(category.getSubcategories());
//        if (subcategories.isEmpty()) {
//            return Subcategory.UNSPECIFIED; // Or handle it as needed
//        }
//        return subcategories.get(ThreadLocalRandom.current().nextInt(subcategories.size()));
//    }
}
