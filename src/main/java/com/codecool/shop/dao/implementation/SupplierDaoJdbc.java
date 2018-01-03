package com.codecool.shop.dao.implementation;

import com.codecool.shop.ConnectionManager;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao{

    private static SupplierDaoJdbc instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoJdbc() {
    }

    public static SupplierDaoJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
        }
        return instance;
    }


    @Override
    public void add(Supplier supplier) {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("INSERT INTO supplier (name, description) VALUES(?,?);");
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getDescription());
            ps.execute();

            ps =(ConnectionManager.getConnection()).prepareStatement("SELECT MAX(id) FROM supplier;");
            ResultSet rs = ps.executeQuery();
            rs.next();
            supplier.setId(rs.getInt(0));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Supplier find(int id) {
        Supplier supplier = null;
        String name;
        String description;

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("SELECT * FROM supplier WHERE id = ?;");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            rs.next();
            name = rs.getString(1);
            description = rs.getString(2);

            supplier = new Supplier(name, description);
            supplier.setId(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }


    @Override
    public void remove(int id) {
        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("DELETE FROM supplier WHERE id = ?;");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> listOfSuppliers = new ArrayList<>();
        Supplier supplier = null;
        String name;
        String description;

        try {
            PreparedStatement ps = (ConnectionManager.getConnection()).prepareStatement("SELECT * FROM supplier");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                name = rs.getString(1);
                description = rs.getString(2);

                supplier = new Supplier(name, description);
                supplier.setId(rs.getInt(0));
                listOfSuppliers.add(supplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfSuppliers;
    }
 }

