package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoJdbcTest {

    static ProductDao unit;

    private ProductCategory testCategory = new ProductCategory("Test", "Test", "Test");
    private ProductCategory testCategory2 = new ProductCategory("Test", "Test", "Test");
    private Supplier testSupplier = new Supplier("Test", "Test");
    private Supplier testSupplier2 = new Supplier("Test", "Test");
    private Product product = new Product("TestAdd", 100, "USD", "Test description", testCategory, testSupplier);
    private Product product2 = new Product("TestAdd", 100, "USD", "Test description", testCategory2, testSupplier2);

    @BeforeEach
    public void init() throws DaoException {
        unit = ProductDaoJdbc.getInstance();
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
                    "INSERT INTO supplier (name, description) VALUES ('Test', 'Test');\n" +
                    "INSERT INTO supplier (name, description) VALUES ('Test', 'Test');\n" +
                    "INSERT INTO product_category (name, department, description) VALUES ('Test', 'Test', 'Test');\n" +
                    "INSERT INTO product_category (name, department, description) VALUES ('Test', 'Test', 'Test');\n");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setIds();
    }

    @Test
    @DisplayName("Tests add method with one element")
    void testAdd() throws DaoException {
        unit.add(product);
        int productId = product.getId();
        assertEquals(1, unit.getAll().size());
        boolean found = false;
        for (Product one : unit.getAll()) {
            if(one.getId() == productId){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    @DisplayName("Tests add method with two element")
    void testAdd2() throws DaoException {
        unit.add(product);
        unit.add(product2);

        int productId = product.getId();
        int productId2 = product2.getId();
        //guarantee that the list contains only the added object --> size, content
        assertEquals(2, unit.getAll().size());
        boolean found = false;
        for (Product one : unit.getAll()) {
            if (one.getId() == productId) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        found = false;
        for (Product one : unit.getAll()) {
            if (one.getId() == productId2) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    @DisplayName("Tests find method")
    void testFind() throws DaoException {
        //assert we get null when the array is empty
        assertEquals(null, unit.find(1));
        unit.add(product);
        unit.add(product2);

        int product2Id = product2.getId();
        //test that we can find the object at the given index
        assertEquals(product2Id, (unit.find(product2Id)).getId());
    }

    @Test
    @DisplayName("Tests remove method")
    void testRemove() throws DaoException {
        unit.remove(2);
        assertEquals(0, unit.getAll().size());
        unit.add(product);
        unit.add(product2);

        int productId = product.getId();
        int product2Id = product2.getId();

        unit.remove(product.getId());
        boolean found = false;
        for (Product one : unit.getAll()) {
            if(one.getId() == productId){
                found = true;
                break;
            }
        }
        assertFalse(found);

        found = false;
        for (Product one : unit.getAll()) {
            if(one.getId() == product2Id){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    @DisplayName("Tests getAll method")
    void testGetAll() throws DaoException {
        assertEquals(0, unit.getAll().size());
        unit.add(product);
        unit.add(product2);

        int productId = product.getId();
        int product2Id = product2.getId();

        assertEquals(2, unit.getAll().size());
        boolean found = false;
        for (Product one : unit.getAll()) {
            if(one.getId() == productId){
                found = true;
                break;
            }
        }
        assertTrue(found);

        found = false;
        for (Product one : unit.getAll()) {
            if(one.getId() == product2Id){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    @DisplayName("Tests getBy supplier")
    void testGetBySupplier() throws DaoException {
        assertEquals(0, unit.getAll().size());
        unit.add(product);
        unit.add(product2);

        int supplierId = product.getSupplier().getId();
        int supplier2Id = product2.getSupplier().getId();

        assertEquals(2, unit.getAll().size());
        boolean found = false;
        for (Product one : unit.getAll()) {
            if(one.getSupplier().getId() == supplierId && one.getId() == 1){
                found = true;
                break;
            }
        }
        assertTrue(found);

        found = false;
        for (Product one : unit.getAll()) {
            if(one.getSupplier().getId() == supplier2Id && one.getId() == 2){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    @DisplayName("Tests getBy product category")
    void testGetByProductCategory() throws DaoException {
        assertEquals(0, unit.getAll().size());
        unit.add(product);
        unit.add(product2);

        int productCategoryId = product.getProductCategory().getId();
        int productCategory2Id = product2.getProductCategory().getId();

        assertEquals(2, unit.getAll().size());
        boolean found = false;
        for (Product one : unit.getAll()) {
            if(one.getProductCategory().getId() == productCategoryId && one.getId() == 1){
                found = true;
                break;
            }
        }
        assertTrue(found);

        found = false;
        for (Product one : unit.getAll()) {
            if(one.getProductCategory().getId() == productCategory2Id && one.getId() == 2){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    private void setIds() {
        testCategory.setId(1);
        testCategory2.setId(2);
        testSupplier.setId(1);
        testSupplier2.setId(2);
    }
}