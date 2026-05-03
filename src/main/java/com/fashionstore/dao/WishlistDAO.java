package com.fashionstore.dao;

import com.fashionstore.model.WishlistItem;
import java.util.List;

public interface WishlistDAO {
    boolean addToWishlist(int userId, int productId);
    boolean removeFromWishlist(int userId, int productId);
    List<WishlistItem> getWishlistByUserId(int userId);
    int getWishlistCountByUserId(int userId);
    boolean isInWishlist(int userId, int productId);
}
