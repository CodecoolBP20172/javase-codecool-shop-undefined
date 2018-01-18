package com.codecool.shop.dao.implementation;


import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.exception.DaoConnectionException;
import com.codecool.shop.exception.DaoException;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** ProductDaoJdbc class which implements methods to handle products in the database
 * @author      Anikó Barát
 * @version     1.0
 * @since       1.0
 */
public class ProductDaoJdbc implements ProductDao {
    private Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);



    private List<Product> DATA = new ArrayList<>();
    private static ProductDaoJdbc instance = null;

    /** A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoJdbc() {
    }

    /** A static method which creates or return an instance
     *@return ProductDaoJdbc instance
     */
    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }


    /** Method to add new products to the database
     *@param product
     */
    @Override
    public void add(Product product) throws DaoException {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES(?,?,?,?,?,?);");
            ps.setString(1, product.getName());
            ps.setDouble(2, Double.parseDouble(product.getPriceWithoutCurrency()));
            ps.setString(3, String.valueOf(product.getDefaultCurrency()));
            ps.setInt(4, product.getSupplier().getId());
            ps.setInt(5, product.getProductCategory().getId());
            ps.setString(6, product.getDescription());
            ps.execute();
            logger.info("New product: name={} added to products table in the database", product.getName());

            ps =(com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) as id FROM products;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            product.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    /** Method to find product in the database by its id
     *@param id
     *@return Product
     */

    @Override
    public Product find(int id) throws DaoException {
        Product product = null;
        String name;
        float defaultPrice;
        String defaultCurrency;
        Supplier supplier;
        ProductCategory productCategory;
        String description;

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT * FROM products WHERE id = ?;");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                name = rs.getString(2);
                defaultPrice = rs.getFloat(3);
                defaultCurrency = rs.getString(4);
                supplier = getSupplyer(rs.getInt(5));
                productCategory = getProductCategory(rs.getInt(6));
                description = rs.getString(7);
                product = new Product(name, defaultPrice, defaultCurrency, description, productCategory, supplier);
                product.setId(id);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return product;
    }
    /** Method to get a supplier from the database by its id
     *@param supplyerId
     *@return Supplier instance
     */
    private Supplier getSupplyer(int supplyerId) throws DaoException {
        Supplier supplier = null;
        String name;
        String description;

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("SELECT * FROM supplier WHERE id = ?;");
            ps.setInt(1, supplyerId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                name = rs.getString(2);
                description = rs.getString(3);

                supplier = new Supplier(name, description);
                supplier.setId(supplyerId);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return supplier;
    }

    /** Method to get a ProductCategory from the database by its id
     *@param productCategoryId
     *@return ProductCategory instance
     */

    private ProductCategory getProductCategory(int productCategoryId) throws DaoException {
        ProductCategory productCategory = null;
        String name;
        String department;
        String description;

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("SELECT * FROM product_category WHERE id = ?;");
            ps.setInt(1, productCategoryId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                name = rs.getString(2);
                department = rs.getString(3);
                description = rs.getString(4);

                productCategory = new ProductCategory(name, department, description);
                productCategory.setId(productCategoryId);
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return productCategory;
    }

    /** Method to remove a product from the database by its id
     *
     *@param id
     *
     */
    @Override
    public void remove(int id) throws DaoException {
        //i might need to still include this line below aswell to remove product from the DATA arraylist too
        DATA.remove(find(id));

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("DELETE FROM products WHERE id = ?;");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    /** Method to return all products in a list
     *
     *@return List containing Product objects
     *
     */
    @Override
    public List<Product> getAll() throws DaoException {
        List<Product> listOfProducts = new ArrayList<>();
        Product product = null;
        String name;
        float defaultPrice;
        String defaultCurrency;
        Supplier supplier;
        ProductCategory productCategory;
        String description;

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                name = rs.getString(2);
                defaultPrice = rs.getFloat(3);
                defaultCurrency = rs.getString(4);
                supplier = getSupplyer(rs.getInt(5));
                productCategory = getProductCategory(rs.getInt(6));
                description = rs.getString(7);
                product = new Product(name, defaultPrice, defaultCurrency, description, productCategory, supplier);
                product.setId(rs.getInt(1));

                supplier = new Supplier(name, description);
                supplier.setId(rs.getInt(1));
                listOfProducts.add(product);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return listOfProducts;
    }

    /** Method to return products in a list from the database by a supplier
     *
     *@param supplier
     *@return List
     *
     */
    @Override
    public List<Product> getBy(Supplier supplier) throws DaoException {
        return getAll().stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    /** Method to return products in a list from the database by a ProductCategory
     *
     *@param productCategory
     *@return List
     *
     */
    @Override
    public List<Product> getBy(ProductCategory productCategory) throws DaoException {
        List<Product> listOfProducts = new ArrayList<>();
        Product product = null;
        String name;
        String description;
        Double defaultPrice;
        String defaultCurrency;
        Supplier supplier;


        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("SELECT products.id, products.name, products.description, default_price, default_currency, supplier.name AS supplier_name, supplier.description AS  supplier_description FROM products\n" +
                    "JOIN supplier ON products.supplier_id = supplier.id\n" +
                    "WHERE product_category_id = 1;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                name = rs.getString("name");
                description = rs.getString("description");
                defaultPrice = rs.getDouble("default_price");
                defaultCurrency = rs.getString("default_currency");

                supplier = new Supplier(rs.getString("supplier_name"), rs.getString("supplier_description"));

                product = new Product(name, defaultPrice, defaultCurrency, description, productCategory, supplier);
                product.setId(rs.getInt("id"));
                listOfProducts.add(product);
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
        return listOfProducts;
    }
}
