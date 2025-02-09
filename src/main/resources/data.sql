-- Users
INSERT INTO users (id, user_picture_id, password, username)
VALUES
    (1, (SELECT id FROM image_files WHERE filename = 'user_avatar_1.jpg'), '$2a$10$GwzR/kWVeBnoPHGAYyY8.upNqF0s3FysFkp5gs2xQRXY4rDahb0qK', 'yarayarn'),
    (2, (SELECT id FROM image_files WHERE filename = 'user_avatar_2.jpg'), '$2a$10$GwzR/kWVeBnoPHGAYyY8.upNqF0s3FysFkp5gs2xQRXY4rDahb0qK', 'reactahook'),
    (3, (SELECT id FROM image_files WHERE filename = 'user_avatar_3.jpg'), '$2a$10$GwzR/kWVeBnoPHGAYyY8.upNqF0s3FysFkp5gs2xQRXY4rDahb0qK', 'elizapostman'),
    (4, (SELECT id FROM image_files WHERE filename = 'user_avatar_4.jpg'), '$2a$10$GwzR/kWVeBnoPHGAYyY8.upNqF0s3FysFkp5gs2xQRXY4rDahb0qK', 'claracould');

INSERT INTO user_roles (user_id, roles)
VALUES
    (1, 'USER'),
    (2, 'USER'),
    (3, 'USER'),
    (4, 'USER');

SELECT setval(pg_get_serial_sequence('users', 'id'), coalesce(max(id), 1), true) FROM users;

-- Addresses
INSERT INTO addresses (id, street, house_number, door, zipcode, city)
VALUES
    (1, 'Javalaan', '21', null, '1888YY', 'Scriptstad'),
    (2, 'Nodepad', '12', null, '2345AZ', 'Developerdam'),
    (3, 'Gitweg', '2', null, '1234AB', 'Techstad'),
    (4, 'Codecampus', '234', 'a', '8899TY', 'Fullstackerdorp');

SELECT setval(pg_get_serial_sequence('addresses', 'id'), coalesce(max(id), 1), true) FROM addresses;

-- Contacts
INSERT INTO contacts (id, address_id, user_id, dtype, first_name, last_name, email, phone)
VALUES
    (1, 1, 1, 'Customer', 'Yara', 'Yarn', 'yarayarn@gmail.com', '0612345678'),
    (2, 2, 2, 'Customer', 'Reacta', 'Hook', 'reacta.hook@gmail.com' , '0614725836'),
    (3, 3, 3, 'Customer', 'Eliza', 'Postman', 'e.postman@gmail.com', '0696385214'),
    (4, 4, 4, 'Customer', 'Clara', 'Cloud', 'cloud.clara@gmail.com', '0658624579');

SELECT setval(pg_get_serial_sequence('contacts', 'id'), coalesce(max(id), 1), true) FROM contacts;

-- Shops
INSERT INTO shops (id, owner_id, shop_picture_id, description, name)
VALUES
    (1, 1, (SELECT id FROM image_files WHERE filename = 'shop_logo_1.jpg'),
     'Discover unique, handcrafted items made with love by me. Perfect for adding charm to your life',
     'Knitting Handmade'),
    (2, 2, (SELECT id FROM image_files WHERE filename = 'shop_logo_2.jpg'),
     'Explore our collection of beautifully crafted, one-of-a-kind creations by skilled artisans. Each item is made with care and attention to detail, offering you a unique way to bring warmth and character to your home, wardrobe, or gift-giving. ',
     'Knitting with Love');

INSERT INTO user_roles (user_id, roles)
VALUES
    (1, 'SHOP_OWNER'),
    (2, 'SHOP_OWNER');

SELECT setval(pg_get_serial_sequence('shops', 'id'), coalesce(max(id), 1), true) FROM shops;

