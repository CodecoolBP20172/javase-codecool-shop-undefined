package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {

    static ProductDao unit;

    private ProductCategory testCategory = new ProductCategory("Test name", "Test department", "Test description");
    private ProductCategory testCategory2 = new ProductCategory("Test name", "Test department", "Test description");
    private Supplier testSupplier = new Supplier("Test name", "Test description");
    private Supplier testSupplier2 = new Supplier("Test name", "Test description");
    private Product product = new Product("TestAdd", 100, "USD", "Test description", testCategory, testSupplier);
    private Product product2 = new Product("TestAdd", 100, "USD", "Test description", testCategory2, testSupplier2);

    @BeforeEach
    public void init(){
        unit = ProductDaoMem.getInstance();
        unit.getAll().clear();
    }

    @Test
    @DisplayName("Tests add method with one element")
    void testAdd(){
        unit.add(product);
        assertEquals(1, unit.getAll().size());
        assertTrue(unit.getAll().contains(product));
    }

    @Test
    @DisplayName("Tests add method with two element")
    void testAdd2(){
        unit.add(product);
        unit.add(product2);

        //guarantee that the list contains only the added object --> size, content
        assertEquals(2, unit.getAll().size());
        assertTrue(unit.getAll().contains(product));
        assertTrue(unit.getAll().contains(product2));
    }

    @Test
    @DisplayName("Tests find method")
    void testFind() {
        //assert we get null when the array is empty
        assertEquals(null, unit.find(1));

        unit.add(product);
        unit.add(product2);

        int product2Id = product2.getId();
        //test that we can find the object at the given index
        assertEquals(product2, unit.find(product2Id));
    }

    @Test
    @DisplayName("Tests remove method")
    void testRemove() {
        unit.remove(2);
        assertEquals(0, unit.getAll().size());

        unit.add(product);
        unit.add(product2);

        unit.remove(product.getId());
        assertFalse(unit.getAll().contains(product));
        assertTrue(unit.getAll().contains(product2));
    }

    @Test
    @DisplayName("Tests getAll method")
    void testGetAll(){
        assertEquals(0, unit.getAll().size());

        unit.add(product);
        unit.add(product2);

        assertEquals(2, unit.getAll().size());
        assertTrue(unit.getAll().contains(product));
        assertTrue(unit.getAll().contains(product2));
    }

    @Test
    @DisplayName("Tests getBy supplier")
    void testGetBySupplier(){
        assertEquals(0, unit.getAll().size());

        unit.add(product);
        unit.add(product2);

        assertEquals(2, unit.getAll().size());
        assertTrue(unit.getBy(testSupplier).contains(product));
        assertTrue(unit.getBy(testSupplier2).contains(product2));
    }

    @Test
    @DisplayName("Tests getBy product category")
    void testGetByProductCategory(){
        assertEquals(0, unit.getAll().size());

        unit.add(product);
        unit.add(product2);

        assertEquals(2, unit.getAll().size());
        assertTrue(unit.getBy(testCategory).contains(product));
        assertTrue(unit.getBy(testCategory2).contains(product2));
    }
}