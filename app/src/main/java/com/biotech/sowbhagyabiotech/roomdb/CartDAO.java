package com.biotech.sowbhagyabiotech.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItems cartItems);

    @Update
    void update(CartItems cartItems);

    @Delete
    void delete(CartItems cartItems);

    @Query("DELETE FROM cart_items where typeOfCart like :cartType")
    public void deleteTable(String cartType);

    @Query("DELETE FROM cart_items where product_id like :id")
    public void deleteProduct(String id);

    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItems>> getAll();

    @Query("SELECT * FROM cart_items WHERE deviceID LIKE :deviceID AND " +
            "typeOfCart LIKE :cartType ORDER by createdDate DESC")
    LiveData<List<CartItems>> getUserCart(String deviceID, String cartType);


    @Query("SELECT * FROM cart_items WHERE deviceID LIKE :deviceID AND " + "typeOfCart LIKE :typeOfCart")
    LiveData<List<CartItems>> getCartCount(String deviceID, String typeOfCart);


    @Query("SELECT * FROM cart_items WHERE deviceID LIKE :deviceID AND " +
            "typeOfCart LIKE :type")
    LiveData<List<CartItems>> getCartCountBasedOnType(String deviceID, String type);


    @Query("SELECT * FROM cart_items WHERE deviceID LIKE :deviceID AND " +
            "typeOfCart LIKE :type AND " + "product_id LIKE :productID")
    LiveData<List<CartItems>> getProductCartQty(String deviceID, String type, String productID);

    @Query("SELECT * FROM cart_items WHERE product_id LIKE :productID AND " +
            "typeOfCart LIKE :cartType")
    boolean checkCartProduct(String productID, String cartType);

    @Query("UPDATE cart_items SET cartQty= :qty WHERE product_id LIKE :cartID")
    void updateCartQty(String qty, String cartID);

    @Query("SELECT * FROM cart_items WHERE store_id not LIKE :restaurantID AND"
            + "   user_id LIKE :userID AND"
            + " typeOfCart LIKE :cartType")
    boolean checkSameRestaurantOrNot(String userID, String restaurantID, String cartType);

}
