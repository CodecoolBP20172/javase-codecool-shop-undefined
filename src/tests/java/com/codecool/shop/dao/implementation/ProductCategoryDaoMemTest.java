package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

class ProductCategoryDaoMemTest {


    private ProductCategoryDao productCategoryDataStore;

    @Before
    public void setUp() throws Exception {
        productCategoryDataStore = ProductCategoryDaoMem.getInstance();

    }


    @Test
    public void addTest() {

        ProductCategory hogwarts = new ProductCategory("Hogwarts", "Magical items", "Items for wizards");
        productCategoryDataStore.add(hogwarts);

        assertEquals(hogwarts, productCategoryDataStore.find(productCategoryDataStore.getAll().size()));

    }

}