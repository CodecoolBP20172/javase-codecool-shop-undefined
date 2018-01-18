package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;

import java.util.List;

/** Data access object interface to maintain product categories in the database.
 * @since 1.0
 */
public interface ProductCategoryDao {

    void add(ProductCategory category);
    ProductCategory find(int id);
    void remove(int id);

    List<ProductCategory> getAll();

    List<String> getAllNames();

    Integer getIdByName(String name);
}
