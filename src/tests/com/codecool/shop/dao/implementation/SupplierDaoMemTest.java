package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class SupplierDaoMemTest {

    static SupplierDao unit;

    @BeforeEach
    public void init() throws DaoException {
        unit = SupplierDaoMem.getInstance();
        unit.getAll().clear();
    }


    @Test
    @DisplayName("Tests add method with one element")
    void testAdd() throws DaoException {
        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);

        assertEquals(1, unit.getAll().size());
        assertTrue(unit.getAll().contains(supplier));

    }


    @Test
    @DisplayName("Tests add method with two element")
    void testAdd2() throws DaoException {
        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);

        Supplier supplier2 = new Supplier("2TestAdd", "Second data to test add method");
        unit.add(supplier2);

        //guarantee that the list contains only the added object --> size, content
        assertEquals(2, unit.getAll().size());
        assertTrue(unit.getAll().contains(supplier));
        assertTrue(unit.getAll().contains(supplier2));
    }


    @Test
    @DisplayName("Tests find method")
    void testFind() throws DaoException {
        //assert we get null when the array is empty
        assertEquals(null, unit.find(1));

        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);

        Supplier supplier2 = new Supplier("2TestAdd", "Second data to test add method");
        unit.add(supplier2);

        int supplier2Id = supplier2.getId();
        //test that we can find the object at the given index
        assertEquals(supplier2, unit.find(supplier2Id));
    }


    @Test
    @DisplayName("Tests remove method")
    void testRemove() throws DaoException {

        unit.remove(2);
        assertEquals(0, unit.getAll().size());

        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);

        Supplier supplier2 = new Supplier("2TestAdd", "Second data to test add method");
        unit.add(supplier2);

        unit.remove(supplier.getId());
        assertFalse(unit.getAll().contains(supplier));
        assertTrue(unit.getAll().contains(supplier2));
    }


    @Test
    @DisplayName("Tests getAll method")
    void testGetAll() throws DaoException {

        assertEquals(0, unit.getAll().size());

        Supplier supplier = new Supplier("TestAdd", "Data to test add method");
        unit.add(supplier);

        Supplier supplier2 = new Supplier("2TestAdd", "Second data to test add method");
        unit.add(supplier2);

        assertEquals(2, unit.getAll().size());
        assertTrue(unit.getAll().contains(supplier));
        assertTrue(unit.getAll().contains(supplier2));
    }
}