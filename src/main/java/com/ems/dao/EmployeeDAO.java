package com.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ems.model.Employee;
import com.ems.util.DBConnection;

public class EmployeeDAO {

    public List<Employee> getAll(int page, int pageSize, String sortBy) {
        List<Employee> list = new ArrayList<>();
        if (sortBy == null) {
            sortBy = "id";
        }
        String col = List.of("name","department","salary").contains(sortBy) ? sortBy : "id";
        String sql = "SELECT * FROM employees ORDER BY " + col + " LIMIT ? OFFSET ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(new Employee(rs.getInt("id"), rs.getString("name"),
                    rs.getString("department"), rs.getDouble("salary"),
                    rs.getString("email"), rs.getString("username")));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Employee getByUsername(String username) {
        String sql = "SELECT * FROM employees WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Employee(rs.getInt("id"), rs.getString("name"),
                    rs.getString("department"), rs.getDouble("salary"),
                    rs.getString("email"), rs.getString("username"));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean add(Employee e) {
        String sql = "INSERT INTO employees(name,department,salary,email,username) VALUES(?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getName()); ps.setString(2, e.getDepartment());
            ps.setDouble(3, e.getSalary()); ps.setString(4, e.getEmail());
            ps.setString(5, e.getUsername());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    public boolean update(Employee e) {
        String sql = "UPDATE employees SET name=?,department=?,salary=?,email=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getName()); ps.setString(2, e.getDepartment());
            ps.setDouble(3, e.getSalary()); ps.setString(4, e.getEmail());
            ps.setInt(5, e.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM employees WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM employees";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}