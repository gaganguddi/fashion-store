package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.ProductVariant;

public interface ProductVariantDAO {

	boolean addVariant(ProductVariant variant);

	ProductVariant getVariantById(int variantId);

	ProductVariant getVariantByProductAndSize(int productId, String size);

	List<ProductVariant> getVariantsByProductId(int productId);

	boolean updateVariant(ProductVariant variant);

	boolean updateStock(int variantId, int quantity);

	boolean decreaseStock(int variantId, int quantity);

	boolean increaseStock(int variantId, int quantity);

	boolean deleteVariant(int variantId);
}