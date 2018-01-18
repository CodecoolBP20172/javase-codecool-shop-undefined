package com.codecool.shop.dao.implementation;


import com.codecool.shop.connection.ConnectionManager;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private Logger logger = LoggerFactory.getLogger(ProductCategoryDaoJdbc.class);

    private List<ProductCategory> DATA = new ArrayList<>();
    private static ProductCategoryDaoJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private ProductCategoryDaoJdbc() {
    }

    public static ProductCategoryDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc();
        }
        return instance;
    }

    /**
     * Adds a new product category to the database.
     *
     * @param category the category to add.
     */
    @Override
    public void add(ProductCategory category) {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO product_category (name, department, description) VALUES(?,?,?);");
            ps.setString(1, category.getName());
            ps.setString(2, category.getDepartment());
            ps.setString(3, category.getDescription());
            ps.execute();
            logger.debug("Product category {} successfully added to product_category table in the database", category.getName());
        } catch (SQLException e) {
            logger.error("Error while adding product category to the database. Message: {}", e.getMessage());
        }
    }

    /**
     * Finds product category by id.
     *
     * @param id the id of the product category.
     * @return the product category.
     */
    @Override
    public ProductCategory find(int id) {
        ProductCategory productCategory = null;
        String name;
        String department;
        String description;

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT * FROM product_category WHERE id = ?;");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                name = rs.getString(2);
                department = rs.getString(3);
                description = rs.getString(4);
                logger.debug("Successfully found {} product_category in the database", name);
                productCategory = new ProductCategory(name, department, description);
                productCategory.setId(id);
            } else {
                logger.debug("Product category is not in the database");
                return null;
            }

        } catch (SQLException e) {
            logger.error("Error while finding product category in the database. Message: {}", e.getMessage());
        }
        return productCategory;
    }

    /**
     * Removes product category by id from the database.
     *
     * @param id the id of the product category to remove.
     */
    @Override
    public void remove(int id) {
        //i might need to still include this line below as well to remove product from the DATA arraylist too
        DATA.remove(find(id));

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("DELETE FROM product_category WHERE id = ?;");
            ps.setInt(1, id);
            ps.execute();
            logger.debug("Product category (id: {id}) successfully removed from the database", id);
        } catch (SQLException e) {
            logger.error("Error while removing product category from the database. Message: {}", e.getMessage());
        }
    }

    /**
     * Returns all product categories from the database as a list.
     *
     * @return List of all product categories.
     */
    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> listOfProductCategories = new ArrayList<>();
        ProductCategory productCategory;
        String name;
        String department;
        String description;

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT * FROM product_category");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                name = rs.getString(2);
                department = rs.getString(3);
                description = rs.getString(4);

                productCategory = new ProductCategory(name, department, description);
                productCategory.setId(rs.getInt(1));
                listOfProductCategories.add(productCategory);
            }

        } catch (SQLException e) {
            logger.error("Error while accessing database product_category table. Message: {}", e.getMessage());
        }
        logger.info("Successfully returned all product categories from the database");
        return listOfProductCategories;
    }

    @Override
    public List<String> getAllNames() {
        List<String> listOfProductCategories = new ArrayList<>();
        String name;

        try {
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT name FROM product_category");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                name = rs.getString("name");
                listOfProductCategories.add(name);
            }

        } catch (SQLException e) {
        }
        return listOfProductCategories;
    }

    @Override
    public Integer getIdByName(String prodCatName) {

        try {
            System.out.println("rdxxxxxxxxxxucxjiodrcxijkjjbdfhjdfkgfsdjfdsh" + prodCatName);
            PreparedStatement ps = (com.codecool.shop.connection.ConnectionManager.getConnection()).prepareStatement("SELECT id FROM product_category WHERE name = ?");
            ps.setString(1, prodCatName);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("id");
            }

        } catch (SQLException e) {
        }
        return null;
    }



}
