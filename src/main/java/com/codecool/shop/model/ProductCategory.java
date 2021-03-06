package com.codecool.shop.model;

import java.util.ArrayList;

/**
 * Represents a product category.
 * The instance contains the department the product category belongs to
 * and the list of products belonging to the product category.
 *
 * @since 1.0
 */
public class ProductCategory extends BaseModel {
    private String department;
    private ArrayList<Product> products;

    public ProductCategory(String name, String department, String description) {
        super(name);
        this.department = department;
        this.products = new ArrayList<>();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * Builds a string from the instance variables of the product category
     * including id, name, department and description.
     *
     * @return String with the information of the product category.
     */
    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }
}