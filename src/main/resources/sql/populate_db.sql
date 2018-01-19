SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, codecoolshop;

-- Product-category data

INSERT INTO product_category (name, department, description) VALUES ('Wands', 'Magical Tools', 'A magical piece of wood used to create magic.');
INSERT INTO product_category (name, department, description) VALUES ('Other Items', 'Magical Tools', 'Any other item created by magic.');
INSERT INTO product_category (name, department, description) VALUES ('Magical Sweets', 'Food', 'The best magical delicacies.');

-- Supplier data

INSERT INTO supplier (name, description) VALUES ('Olivander', 'The best wands ever created.');
INSERT INTO supplier (name, description) VALUES ('Unspeakables Co.', '-');
INSERT INTO supplier (name, description) VALUES ('Florian Fortescue Sweets', 'After dominating the icecream business, he branched out into regular sweets.');

-- Products data

INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Magic Wand - Model 1', 300, 'USD', 1, 1, 'High quality 39.8cm long wand for great wizards');
INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Magic Wand - Model 2', 350, 'USD', 1, 1, 'High quality 39.8cm long wand for brave wizards');
INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Magic Wand - Model 3', 350, 'USD', 1, 1, 'High quality 39.8cm long wand for evil wizards');
INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Time-Turner Necklace', 44, 'USD', 2, 2, 'The time-turner is centered with a miniature hourglass.');
INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Marauders Map', 30, 'USD', 2, 2, 'With this item, you will always know where your friends are.');
INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Wizarding Chess Set', 100, 'USD', 2, 2, 'A simple chess game with an epic twist.');
INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Exploding Bon-Bons', 8, 'USD', 3, 3, 'White chocolate with an orange and pineapple flavour truffle center.');
INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Every Flavoured Beans', 9, 'USD', 3, 3, 'Up to 20 flavours that range from delicious to disgusting.');
INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Chocolate Frog', 8, 'USD', 3, 3, 'A delicious frog shaped confection of solid milk chocolate.');
