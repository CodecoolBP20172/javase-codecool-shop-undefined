package com.codecool.shop.dao.implementation;

import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoJdbcTest {

    static SupplierDao unit;

    @BeforeEach
    public void init(){
        unit = SupplierDaoJdbc.getInstance();
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
                    "\n");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Tests add method with one element")
    void testAdd(){
        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);
        int supplierId = supplier.getId();

        assertEquals(1, unit.getAll().size());

        boolean found = false;
        for (Supplier one : unit.getAll()) {
            if(one.getId() == supplierId){
                found = true;
                break;
            }
        }
        assertTrue(found);
    }


    @Test
    @DisplayName("Tests add method with two element")
    void testAdd2(){
        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);
        int supplierId = supplier.getId();

        Supplier supplier2 = new Supplier("2TestAdd", "Second data to test add method");
        unit.add(supplier2);
        int supplierId2 = supplier2.getId();
        //garantee that the list contains only the added object --> size, content
        assertEquals(2, unit.getAll().size());
        boolean found = false;
        for (Supplier one : unit.getAll()) {
            if(one.getId() == supplierId){
                found = true;
                break;
            }
        }
        assertTrue(found);

        found = false;
        for (Supplier one : unit.getAll()) {
            if(one.getId() == supplierId2){
                found = true;
                break;
            }
        }
        assertTrue(found);    }


    @Test
    @DisplayName("Tests find method")
    void testFind() {
        //assert we get null when the array is empty
        assertEquals(null, unit.find(1));

        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);

        Supplier supplier2 = new Supplier("2TestAdd", "Second data to test add method");
        unit.add(supplier2);

        int supplier2Id = supplier2.getId();
        //test that we can find the object at the given index
        assertEquals(supplier2Id, (unit.find(supplier2Id)).getId());
    }


    @Test
    @DisplayName("Tests remove method")
    void testRemove() {

        unit.remove(2);
        assertEquals(0, unit.getAll().size());

        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);
        int supplierId = supplier.getId();

        Supplier supplier2 = new Supplier("2TestAdd", "Second data to test add method");
        unit.add(supplier2);
        int supplierId2 = supplier2.getId();

        unit.remove(supplier.getId());
        boolean found = false;
        for (Supplier one : unit.getAll()) {
            if(one.getId() == supplierId){
                found = true;
                break;
            }
        }
        assertFalse(found);

        found = false;
        for (Supplier one : unit.getAll()) {
            if(one.getId() == supplierId2){
                found = true;
                break;
            }
        }
        assertTrue(found);    }


    @Test
    @DisplayName("Tests getAll method")
    void testGetAll(){

        assertEquals(0, unit.getAll().size());

        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);
        int supplierId = supplier.getId();

        Supplier supplier2 = new Supplier("2TestAdd", "Second data to test add method");
        unit.add(supplier2);
        int supplierId2 = supplier2.getId();

        assertEquals(2, unit.getAll().size());
        boolean found = false;
        for (Supplier one : unit.getAll()) {
            if(one.getId() == supplierId){
                found = true;
                break;
            }
        }
        assertTrue(found);

        found = false;
        for (Supplier one : unit.getAll()) {
            if(one.getId() == supplierId2){
                found = true;
                break;
            }
        }
        assertTrue(found);    }
}