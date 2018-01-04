package com.codecool.shop.dao.implementation;


import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import static com.codecool.shop.ConnectionManager.getConnection;

public class ProductDaoJdbc implements ProductDao {

    private List<Product> DATA = new ArrayList<>();
    private static ProductDaoJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductDaoJdbc() {
    }

    public static ProductDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductDaoJdbc();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO products (name, default_price, default_currency, supplier_id, product_category_id, description) VALUES(?,?,?,?,?,?);");
            ps.setString(1, product.getName());
            ps.setDouble(2, Double.parseDouble(product.getPriceWithoutCurrency()));
            ps.setString(3, String.valueOf(product.getDefaultCurrency()));
            ps.setInt(4, product.getSupplier().getId());
            ps.setInt(5, product.getProductCategory().getId());
            ps.setString(6, product.getDescription());
            ps.execute();
            ps =(com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) as id FROM products;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            product.setId(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
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
                name = rs.getString(1);
                defaultPrice = rs.getFloat(2);
                defaultCurrency = rs.getString(3);
                supplier = getSupplyer(rs.getInt(4));
                productCategory = getProductCategory(rs.getInt(5));
                description = rs.getString(6);
                product = new Product(name, defaultPrice, defaultCurrency, description, productCategory, supplier);
                product.setId(id);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    private Supplier getSupplyer(int supplyerId) {
        Supplier supplier = null;
        String name;
        String description;

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT * FROM supplier WHERE id = ?;");
            ps.setInt(1, supplyerId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                name = rs.getString(1);
                description = rs.getString(2);

                supplier = new Supplier(name, description);
                supplier.setId(supplyerId);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    private ProductCategory getProductCategory(int productCategoryId) {
        ProductCategory productCategory = null;
        String name;
        String department;
        String description;

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT * FROM product_category WHERE id = ?;");
            ps.setInt(1, productCategoryId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                name = rs.getString(1);
                department = rs.getString(2);
                description = rs.getString(3);

                productCategory = new ProductCategory(name, department, description);
                productCategory.setId(productCategoryId);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productCategory;
    }

    @Override
    public void remove(int id) {
        //i might need to still include this line below aswell to remove product from the DATA arraylist too
        DATA.remove(find(id));

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("DELETE FROM products WHERE id = ?;");
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> listOfProducts = new ArrayList<>();
        Product product = null;
        String name;
        float defaultPrice;
        String defaultCurrency;
        Supplier supplier;
        ProductCategory productCategory;
        String description;

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                name = rs.getString(1);
                defaultPrice = rs.getFloat(2);
                defaultCurrency = rs.getString(3);
                supplier = getSupplyer(rs.getInt(4));
                productCategory = getProductCategory(rs.getInt(5));
                description = rs.getString(6);
                product = new Product(name, defaultPrice, defaultCurrency, description, productCategory, supplier);
                product.setId(rs.getInt(1));

                supplier = new Supplier(name, description);
                supplier.setId(rs.getInt(1));
                listOfProducts.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfProducts;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return getAll().stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return getAll().stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }
}
