package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductCategoryDaoMemTest {

    private ProductCategoryDao productCategoryDataStore;
    private ProductCategory hogwarts;
    private ProductCategory testHogwarts;

    @BeforeEach
    public void setUp() throws Exception {
        productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        productCategoryDataStore.getAll().clear();
        hogwarts = new ProductCategory("Hogwarts", "Magical items", "Items for wizards");
        testHogwarts = new ProductCategory("TestHogwarts", "Magical items", "Items for wizards");
    }

    @Test
    @DisplayName("Add method test")
    public void addTest() throws Exception{

        productCategoryDataStore.add(hogwarts);

        assertEquals(hogwarts, productCategoryDataStore.find(productCategoryDataStore.getAll().size()));
    }

    @Test
    @DisplayName("Find method test")
    public void findTest() throws Exception{

        productCategoryDataStore.add(hogwarts);
        productCategoryDataStore.add(testHogwarts);

        assertEquals(hogwarts, productCategoryDataStore.find(1));
        assertEquals(testHogwarts, productCategoryDataStore.find(2));
        assertNull(productCategoryDataStore.find(productCategoryDataStore.getAll().size()+1));
    }

    @Test
    @DisplayName("Remove method test")
    public void removeTest() throws Exception{

        productCategoryDataStore.add(hogwarts);
        productCategoryDataStore.add(testHogwarts);
        productCategoryDataStore.remove(2);

        assertNull(productCategoryDataStore.find(2));
        assertEquals(1, productCategoryDataStore.getAll().size());
    }

    @Test
    @DisplayName("GetAll method test")
    public void getAllTest() throws Exception{

        productCategoryDataStore.add(hogwarts);
        productCategoryDataStore.add(testHogwarts);

        assertEquals(2, productCategoryDataStore.getAll().size());

        productCategoryDataStore.remove(1);
        assertEquals(1, productCategoryDataStore.getAll().size());
    }
}