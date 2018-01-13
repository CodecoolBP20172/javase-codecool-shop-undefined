package com.codecool.shop.dao;

import com.codecool.shop.model.Supplier;

import java.util.List;
/** Data access object interface to handle suppliers
 * @since 1.0
 */
public interface SupplierDao {

    void add(Supplier supplier);
    Supplier find(int id);
    void remove(int id);

    List<Supplier> getAll();
}
