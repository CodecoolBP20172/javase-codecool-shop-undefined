package com.codecool.shop.dao.implementation;


import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.BaseModel;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codecool.shop.ConnectionManager.getConnection;

public class ProductDaoJdbc implements ProductDao {
    private Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);



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
            logger.info("New product: name={} added to products table in the database", product.getName());

            ps =(com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) as id FROM products;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            product.setId(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error while adding product to the database. Message: {}", e.getMessage());

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
            e.printStackTrace();
            logger.error("Error while looking for product with id: {} in the database. Message: {}", id, e.getMessage());

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
                name = rs.getString(2);
                description = rs.getString(3);

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
                name = rs.getString(2);
                department = rs.getString(3);
                description = rs.getString(4);

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
            logger.error("Error while removing product with id: {} from the database. Message: {}", id, e.getMessage());

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
            e.printStackTrace();
            logger.error("Error while querying all products from the database. Message: {}", e.getMessage());

        }
        return listOfProducts;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return getAll().stream().filter(t -> t.getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> listOfProducts = new ArrayList<>();
        Product product = null;
        String name;
        String description;
        Double defaultPrice;
        String defaultCurrency;
        Supplier supplier;


        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT products.id, products.name, products.description, default_price, default_currency, supplier.name AS supplier_name, supplier.description AS  supplier_description FROM products\n" +
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
            e.printStackTrace();
            logger.error("Error while querying products by product category: name={} from the database. Message: {}", productCategory, e.getMessage());

        }
        return listOfProducts;
    }
}
