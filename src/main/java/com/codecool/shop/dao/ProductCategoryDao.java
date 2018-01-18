package com.codecool.shop.dao;

import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

/** Data access object interface to maintain product categories in the database.
 * @since 1.0
 */
public interface ProductCategoryDao {

    void add(ProductCategory category) throws DaoConnectionException, DaoException;
    ProductCategory find(int id) throws DaoConnectionException, DaoException;
    void remove(int id) throws DaoException;

    List<ProductCategory> getAll() throws DaoConnectionException, DaoException;

}
