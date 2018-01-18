package com.codecool.shop.dao;

import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Supplier;

import java.util.List;
/** Data access object interface to handle suppliers
 * @since 1.0
 */
public interface SupplierDao {

    void add(Supplier supplier) throws DaoConnectionException, DaoException;
    Supplier find(int id) throws DaoConnectionException, DaoException;
    void remove(int id) throws DaoConnectionException, DaoException;

    List<Supplier> getAll() throws DaoConnectionException, DaoException;
}
