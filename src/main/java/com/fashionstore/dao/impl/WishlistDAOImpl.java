package com.fashionstore.dao.impl;

import com.fashionstore.dao.WishlistDAO;
import com.fashionstore.model.WishlistItem;
import com.fashionstore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAOImpl implements WishlistDAO {

    @Override
    public boolean addToWishlist(int userId, int productId) {
        // Ensure wishlist exists for user
        int wishlistId = getOrCreateWishlistId(userId);
        if (wishlistId == -1) return false;

        String sql = "INSERT IGNORE INTO wishlist_items (wishlist_id, product_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, wishlistId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeFromWishlist(int userId, int productId) {
        String sql = "DELETE wi FROM wishlist_items wi JOIN wishlist w ON wi.wishlist_id = w.wishlist_id WHERE w.user_id = ? AND wi.product_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<WishlistItem> getWishlistByUserId(int userId) {
        List<WishlistItem> list = new ArrayList<>();
        String sql = "SELECT wi.*, p.product_name, p.price, p.image_url FROM wishlist_items wi " +
                     "JOIN wishlist w ON wi.wishlist_id = w.wishlist_id " +
                     "JOIN products p ON wi.product_id = p.product_id " +
                     "WHERE w.user_id = ? ORDER BY wi.added_at DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WishlistItem item = new WishlistItem();
                item.setWishlistItemId(rs.getInt("wishlist_item_id"));
                item.setWishlistId(rs.getInt("wishlist_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setAddedAt(rs.getTimestamp("added_at"));
                item.setProductName(rs.getString("product_name"));
                item.setPrice(rs.getDouble("price"));
                item.setImageUrl(rs.getString("image_url"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int getWishlistCountByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM wishlist_items wi JOIN wishlist w ON wi.wishlist_id = w.wishlist_id WHERE w.user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean isInWishlist(int userId, int productId) {
        String sql = "SELECT 1 FROM wishlist_items wi JOIN wishlist w ON wi.wishlist_id = w.wishlist_id WHERE w.user_id = ? AND wi.product_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getOrCreateWishlistId(int userId) {
        String checkSql = "SELECT wishlist_id FROM wishlist WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("wishlist_id");
            } else {
                String insertSql = "INSERT INTO wishlist (user_id) VALUES (?)";
                try (PreparedStatement ips = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ips.setInt(1, userId);
                    ips.executeUpdate();
                    ResultSet grs = ips.getGeneratedKeys();
                    if (grs.next()) {
                        return grs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
