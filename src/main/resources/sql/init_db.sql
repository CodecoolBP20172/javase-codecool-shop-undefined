DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products
(
  id SERIAL PRIMARY KEY NOT NULL,
  name varchar(40),
  default_price FLOAT,
  default_currency varchar(40),
  supplier_id INT NOT NULL,
  product_category_id INT NOT NULL,
  description varchar(400)
);

DROP TABLE IF EXISTS product_category CASCADE;
CREATE TABLE product_category (
  id SERIAL PRIMARY KEY NOT NULL,
  name varchar(40),
  department varchar(40),
  description varchar(400)
);


DROP TABLE IF EXISTS supplier CASCADE;
CREATE TABLE supplier
(
  id SERIAL PRIMARY KEY NOT NULL,
  name varchar(40),
  description varchar(400)
);


DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE orders
(
  id SERIAL PRIMARY KEY NOT NULL,
  customer_id INT NOT NULL,
  cart_id INT NOT NULL
);

DROP TABLE IF EXISTS carts CASCADE;
CREATE TABLE carts
(
  id SERIAL PRIMARY KEY NOT NULL,
  customer_id INT NOT NULL
);

DROP TABLE IF EXISTS line_item CASCADE;
CREATE TABLE line_item
(
  id SERIAL PRIMARY KEY NOT NULL,
  cart_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT
);

DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE customer
(
  id SERIAL PRIMARY KEY NOT NULL,
  first_name varchar(40),
  last_name varchar(40),
  phone_number varchar(40),
  email varchar(40),
  bill_country varchar(40),
  bill_city varchar(40),
  bill_zip INT,
  bill_address varchar(40),
  ship_country varchar(40),
  ship_city varchar(40),
  ship_zip INT,
  ship_address varchar(40)
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
  ADD CONSTRAINT fk_order_id FOREIGN KEY (cart_id) REFERENCES carts(id);

ALTER TABLE ONLY orders
  ADD CONSTRAINT fk_order_id FOREIGN KEY (cart_id) REFERENCES carts(id);

ALTER TABLE ONLY carts
  ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);
