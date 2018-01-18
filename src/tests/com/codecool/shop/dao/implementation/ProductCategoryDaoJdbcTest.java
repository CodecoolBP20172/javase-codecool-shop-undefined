package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoJdbcTest {

    private ProductCategoryDao productCategoryDataStore;
    private ProductCategory prdCategory;

    @BeforeEach
    public void init(){
        productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        prdCategory = new ProductCategory("mate", "mate2", "mate3");
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("DROP TABLE IF EXISTS products CASCADE;\n" +
                    "CREATE TABLE products\n" +
                    "(\n" +
                    "  id SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  name varchar(40),\n" +
                    "  default_price FLOAT,\n" +
                    "  default_currency varchar(40),\n" +
                    "  supplier_id INT NOT NULL,\n" +
                    "  product_category_id INT NOT NULL,\n" +
                    "  description varchar(400)\n" +
                    ");\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS product_category CASCADE;\n" +
                    "CREATE TABLE product_category (\n" +
                    "  id SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  name varchar(40),\n" +
                    "  department varchar(40),\n" +
                    "  description varchar(400)\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS supplier CASCADE;\n" +
                    "CREATE TABLE supplier\n" +
                    "(\n" +
                    "  id SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  name varchar(40),\n" +
                    "  description varchar(400)\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS orders CASCADE;\n" +
                    "CREATE TABLE orders\n" +
                    "(\n" +
                    "  id SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  customer_id INT NOT NULL,\n" +
                    "  subtotal FLOAT\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS customer CASCADE;\n" +
                    "CREATE TABLE customer\n" +
                    "(\n" +
                    "  id SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  first_name varchar(40),\n" +
                    "  last_name varchar(40),\n" +
                    "  phone_number varchar(40),\n" +
                    "  email varchar(40),\n" +
                    "  bill_country varchar(40),\n" +
                    "  bill_city varchar(40),\n" +
                    "  bill_zip INT,\n" +
                    "  bill_address varchar(40),\n" +
                    "  ship_country varchar(40),\n" +
                    "  ship_city varchar(40),\n" +
                    "  ship_zip INT,\n" +
                    "  ship_address varchar(40)\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "DROP TABLE IF EXISTS line_item CASCADE;\n" +
                    "CREATE TABLE line_item\n" +
                    "(\n" +
                    "  id SERIAL PRIMARY KEY NOT NULL,\n" +
                    "  order_id INT NOT NULL,\n" +
                    "  product_id INT NOT NULL,\n" +
                    "  quantity INT\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "ALTER TABLE ONLY products\n" +
                    "  ADD CONSTRAINT fk_product_category_id FOREIGN KEY (product_category_id) REFERENCES product_category(id);\n" +
                    "\n" +
                    "ALTER TABLE ONLY products\n" +
                    "  ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id);\n" +
                    "\n" +
                    "ALTER TABLE ONLY orders\n" +
                    "  ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer(id);\n" +
                    "\n" +
                    "ALTER TABLE ONLY line_item\n" +
                    "  ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(id);\n" +
                    "\n" +
                    "ALTER TABLE ONLY line_item\n" +
                    "  ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(id);\n" +
                    "\n" +
                    "SET statement_timeout = 0;" +
            "SET lock_timeout = 0;" +
            "SET client_encoding = 'UTF8';" +
            "SET standard_conforming_strings = on;" +
            "SET check_function_bodies = false;" +
            "SET client_min_messages = warning;" +
            "SET row_security = off;" +

            "SET search_path = public, codecoolshop;" +


            "INSERT INTO product_category (name, department, description) VALUES ('Wands', 'Magical Tools', 'A magical piece of wood used to create magic.');" +
            "INSERT INTO product_category (name, department, description) VALUES ('Other Items', 'Magical Tools', 'Any other item created by magic.');" +
            "INSERT INTO product_category (name, department, description) VALUES ('Magical Sweets', 'Food', 'The best magical delicacies.');" +


            "INSERT INTO supplier (name, description) VALUES ('Olivander', 'The best wands ever created.');" +
            "INSERT INTO supplier (name, description) VALUES ('Unspeakables Co.', '-');" +
            "INSERT INTO supplier (name, description) VALUES ('Florian Fortescue Sweets', 'After dominating the icecream business, he branched out into regular sweets.');" +



            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Magic Wand - Model 1', 300, 'USD', 1, 1, 'High quality 39.8cm long wand for great wizards');" +
            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Magic Wand - Model 2', 350, 'USD', 1, 1, 'High quality 39.8cm long wand for brave wizards');" +
            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Magic Wand - Model 3', 350, 'USD', 1, 1, 'High quality 39.8cm long wand for evil wizards');" +
            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Time-Turner Necklace', 44, 'USD', 2, 2, 'The time-turner is centered with a miniature hourglass.');" +
            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Marauders Map', 30, 'USD', 2, 2, 'With this item, you will always know where your friends are.');" +
            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Wizarding Chess Set', 100, 'USD', 2, 2, 'A simple chess game with an epic twist.');" +
            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Exploding Bon-Bons', 8, 'USD', 3, 3, 'White chocolate with an orange and pineapple flavour truffle center.');" +
            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Every Flavoured Beans', 9, 'USD', 3, 3, 'Up to 20 flavours that range from delicious to disgusting.');" +
            "INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES ('Chocolate Frog', 8, 'USD', 3, 3, 'A delicious frog shaped confection of solid milk chocolate.');"

);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoConnectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Add test")
    public void addTest() throws Exception {
        productCategoryDataStore.add(prdCategory);

        assertEquals(4, productCategoryDataStore.getAll().size());
    }

    @Test
    @DisplayName("Find existing product category test")
    public void findExistingProductCategoryTest() throws Exception {
        productCategoryDataStore.add(prdCategory);
        prdCategory.setId(productCategoryDataStore.getAll().size());

        assertEquals(prdCategory.toString(), productCategoryDataStore.find(productCategoryDataStore.getAll().size()).toString());
       }

    @Test
    @DisplayName("Find non existing product category test")
    public void findNonExistingProductCategoryTest() throws Exception {
        productCategoryDataStore.add(prdCategory);
        prdCategory.setId(productCategoryDataStore.getAll().size());

        assertNull(productCategoryDataStore.find(productCategoryDataStore.getAll().size()+1));
    }

    @Test
    @DisplayName("Remove test")
    public void removeTest() throws Exception {
        productCategoryDataStore.add(prdCategory);

        assertEquals(4, productCategoryDataStore.getAll().size());

        productCategoryDataStore.remove(productCategoryDataStore.getAll().size());
        assertEquals(3, productCategoryDataStore.getAll().size());
    }

    @Test
    public void getAllTest() throws Exception {
        productCategoryDataStore.add(prdCategory);

        assertEquals(4, productCategoryDataStore.getAll().size());
    }
}