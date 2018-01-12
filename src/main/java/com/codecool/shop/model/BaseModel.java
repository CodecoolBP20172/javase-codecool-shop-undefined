package com.codecool.shop.model;


import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseModel {
    private Logger logger = LoggerFactory.getLogger(BaseModel.class);

    protected int id;
    //protected int cartId;
    protected String name;
    protected String description;

    public BaseModel(String name) {
        this.name = name;
        logger.info("Base model instance successfully created with name: {}", name);
    }

    public BaseModel(String name, String description) {
        this.name = name;
        this.description = description;
        logger.info("Base model instance successfully created with name: {}, description: {}", name, description);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(this);
                if (value != null) {
                    sb.append(field.getName() + ":" + value + ",");
                }
            } catch (IllegalAccessException e) {
                logger.error("Error while building string. Message: {}", e.getMessage());
            }
        }
        return sb.toString();
    }
}
