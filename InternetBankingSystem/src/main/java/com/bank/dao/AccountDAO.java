package com.bank.dao;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // ── GET ACCOUNT BY CUSTOMER ID ────────────────────────────────────
    public Account getAccountByCustomerId(int customerId) {
        String sql = "SELECT * FROM account WHERE customer_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapAccount(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // ── GET ACCOUNT BY ACCOUNT NO ─────────────────────────────────────
    public Account getAccountByNo(String accountNo) {
        String sql = "SELECT * FROM account WHERE account_no = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapAccount(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // ── FUND TRANSFER ─────────────────────────────────────────────────
    public boolean transferFunds(String fromAccount, String toAccount, double amount) {
        String debitSql   = "UPDATE account SET balance = balance - ? WHERE account_no = ? AND balance >= ?";
        String creditSql  = "UPDATE account SET balance = balance + ? WHERE account_no = ?";
        String txSql      = "INSERT INTO transaction (account_no, type, amount, description) VALUES (?, ?, ?, ?)";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // Debit sender
            PreparedStatement ps1 = con.prepareStatement(debitSql);
            ps1.setDouble(1, amount);
            ps1.setString(2, fromAccount);
            ps1.setDouble(3, amount);
            int rows = ps1.executeUpdate();
            if (rows == 0) { con.rollback(); return false; } // Insufficient balance

            // Credit receiver
            PreparedStatement ps2 = con.prepareStatement(creditSql);
            ps2.setDouble(1, amount);
            ps2.setString(2, toAccount);
            int rows2 = ps2.executeUpdate();
            if (rows2 == 0) { con.rollback(); return false; } // Invalid receiver account

            // Log sender transaction
            PreparedStatement ps3 = con.prepareStatement(txSql);
            ps3.setString(1, fromAccount);
            ps3.setString(2, "DEBIT");
            ps3.setDouble(3, amount);
            ps3.setString(4, "Transfer to " + toAccount);
            ps3.executeUpdate();

            // Log receiver transaction
            PreparedStatement ps4 = con.prepareStatement(txSql);
            ps4.setString(1, toAccount);
            ps4.setString(2, "CREDIT");
            ps4.setDouble(3, amount);
            ps4.setString(4, "Transfer from " + fromAccount);
            ps4.executeUpdate();

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

    // ── DEPOSIT ───────────────────────────────────────────────────────
    public boolean deposit(String accountNo, double amount) {
        String updateSql = "UPDATE account SET balance = balance + ? WHERE account_no = ?";
        String txSql     = "INSERT INTO transaction (account_no, type, amount, description) VALUES (?, 'CREDIT', ?, 'Deposit')";
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps1 = con.prepareStatement(updateSql);
            ps1.setDouble(1, amount);
            ps1.setString(2, accountNo);
            ps1.executeUpdate();
            PreparedStatement ps2 = con.prepareStatement(txSql);
            ps2.setString(1, accountNo);
            ps2.setDouble(2, amount);
            ps2.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) { try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) { try { con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    // ── WITHDRAW ──────────────────────────────────────────────────────
    public boolean withdraw(String accountNo, double amount) {
        String updateSql = "UPDATE account SET balance = balance - ? WHERE account_no = ? AND balance >= ?";
        String txSql     = "INSERT INTO transaction (account_no, type, amount, description) VALUES (?, 'DEBIT', ?, 'Withdrawal')";
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);
            PreparedStatement ps1 = con.prepareStatement(updateSql);
            ps1.setDouble(1, amount);
            ps1.setString(2, accountNo);
            ps1.setDouble(3, amount);
            int rows = ps1.executeUpdate();
            if (rows == 0) { con.rollback(); return false; }
            PreparedStatement ps2 = con.prepareStatement(txSql);
            ps2.setString(1, accountNo);
            ps2.setDouble(2, amount);
            ps2.executeUpdate();
            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) { try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); } }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) { try { con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
        }
    }

    // ── TRANSACTION HISTORY ───────────────────────────────────────────
    public List<Transaction> getTransactionHistory(String accountNo) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE account_no = ? ORDER BY date DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountNo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransactionId(rs.getInt("transaction_id"));
                t.setAccountNo(rs.getString("account_no"));
                t.setType(rs.getString("type"));
                t.setAmount(rs.getDouble("amount"));
                t.setDate(rs.getTimestamp("date"));
                t.setDescription(rs.getString("description"));
                list.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ── HELPER ────────────────────────────────────────────────────────
    private Account mapAccount(ResultSet rs) throws SQLException {
        Account a = new Account();
        a.setAccountNo(rs.getString("account_no"));
        a.setCustomerId(rs.getInt("customer_id"));
        a.setAccountType(rs.getString("account_type"));
        a.setBalance(rs.getDouble("balance"));
        return a;
    }
}
