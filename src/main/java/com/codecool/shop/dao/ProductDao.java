package com.codecool.shop.dao;

import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

/** Data access object interface to handle products
 * @since 1.0
 */

public interface ProductDao {

    void add(Product product) throws DaoConnectionException, DaoException;
    Product find(int id) throws DaoConnectionException, DaoException;
    void remove(int id) throws DaoException;

    List<Product> getAll() throws DaoConnectionException, DaoException;
    List<Product> getBy(Supplier supplier) throws DaoException;
    List<Product> getBy(ProductCategory productCategory) throws DaoConnectionException, DaoException;

}
