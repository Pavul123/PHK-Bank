package com.bank.dao;

import com.bank.model.Customer;
import com.bank.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // ── LOGIN ─────────────────────────────────────────────────────────
    public Customer login(String email, String password) {
        String sql = "SELECT * FROM customer WHERE email = ? AND password = ? AND status = 'ACTIVE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapCustomer(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ── REGISTER ──────────────────────────────────────────────────────
    public boolean register(Customer c, String accountNo, String accountType) {
        String custSql = "INSERT INTO customer (name, phone, email, address, password, " +
                         "security_question, answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String accSql  = "INSERT INTO account (account_no, customer_id, account_type, balance) " +
                         "VALUES (?, ?, ?, 0.00)";
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(custSql, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, c.getName());
            ps1.setString(2, c.getPhone());
            ps1.setString(3, c.getEmail());
            ps1.setString(4, c.getAddress());
            ps1.setString(5, c.getPassword());
            ps1.setString(6, c.getSecurityQuestion());
            ps1.setString(7, c.getAnswer());
            ps1.executeUpdate();

            ResultSet keys = ps1.getGeneratedKeys();
            int customerId = 0;
            if (keys.next()) customerId = keys.getInt(1);

            PreparedStatement ps2 = con.prepareStatement(accSql);
            ps2.setString(1, accountNo);
            ps2.setInt(2, customerId);
            ps2.setString(3, accountType);
            ps2.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    // ── GET BY ID ─────────────────────────────────────────────────────
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapCustomer(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // ── GET ALL ───────────────────────────────────────────────────────
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY customer_id DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapCustomer(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ── UPDATE PROFILE ────────────────────────────────────────────────
    public boolean updateProfile(Customer c) {
        String sql = "UPDATE customer SET name=?, phone=?, address=? WHERE customer_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getAddress());
            ps.setInt(4, c.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ── CHANGE PASSWORD ───────────────────────────────────────────────
    public boolean changePassword(int customerId, String oldPwd, String newPwd) {
        String sql = "UPDATE customer SET password=? WHERE customer_id=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPwd);
            ps.setInt(2, customerId);
            ps.setString(3, oldPwd);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ── FREEZE / ACTIVATE ─────────────────────────────────────────────
    public boolean updateStatus(int customerId, String status) {
        String sql = "UPDATE customer SET status=? WHERE customer_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, customerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ── HELPER ────────────────────────────────────────────────────────
    private Customer mapCustomer(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setName(rs.getString("name"));
        c.setPhone(rs.getString("phone"));
        c.setEmail(rs.getString("email"));
        c.setAddress(rs.getString("address"));
        c.setPassword(rs.getString("password"));
        c.setSecurityQuestion(rs.getString("security_question"));
        c.setAnswer(rs.getString("answer"));
        c.setStatus(rs.getString("status"));
        return c;
    }
}
