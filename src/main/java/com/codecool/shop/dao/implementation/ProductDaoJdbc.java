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
                supplier = DATA.get(id-1).getSupplier();
                productCategory = DATA.get(id-1).getProductCategory();
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

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        return DATA;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return DATA.stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return DATA.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
    }
}
