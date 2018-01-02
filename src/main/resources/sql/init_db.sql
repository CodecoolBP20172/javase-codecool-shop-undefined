DROP TABLE IF EXISTS products;
CREATE TABLE products
(
id SERIAL PRIMARY KEY NOT NULL,
name varchar(40),
default_price FLOAT,
default_currency CHAR,
supplier_id INT NOT NULL ,
product_category_id INT NOT NULL
);

DROP TABLE IF EXISTS product_category;
CREATE TABLE product_category (
id SERIAL PRIMARY KEY NOT NULL,
name varchar(40),
department CHAR,
description CHAR
);


DROP TABLE IF EXISTS supplier;
CREATE TABLE supplier
(
id SERIAL PRIMARY KEY NOT NULL,
name varchar(40),
decription CHAR
);


DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
id SERIAL PRIMARY KEY NOT NULL,
customer_id INT NOT NULL,
subtotal FLOAT
);


DROP TABLE IF EXISTS customer;
CREATE TABLE customer
(
  id SERIAL PRIMARY KEY NOT NULL,
  first_name varchar(40),
  last_name varchar(40),
  phone_number CHAR,
  email CHAR,
  bill_country CHAR,
  bill_city CHAR,
  bill_zip INT,
  bill_address CHAR,
  ship_country CHAR,
  ship_city CHAR,
  ship_zip INT,
  ship_address CHAR
);


DROP TABLE IF EXISTS line_item;
CREATE TABLE line_item
(
  id SERIAL PRIMARY KEY NOT NULL,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT
);



ALTER TABLE ONLY products
ADD CONSTRAINT fk_product_category_id FOREIGN KEY (product_category_id) REFERENCES product_category(id);

ALTER TABLE ONLY products
ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id);

ALTER TABLE ONLY orders
ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);

ALTER TABLE ONLY line_item
ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE ONLY line_item
ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(id);
