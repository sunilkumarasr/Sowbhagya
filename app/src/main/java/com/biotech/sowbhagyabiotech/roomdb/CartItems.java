package com.biotech.sowbhagyabiotech.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItems {

    @PrimaryKey(autoGenerate = true)
    public int cartID;

    @ColumnInfo(name = "user_id")
    public String user_id;

    @ColumnInfo(name = "deviceID")
    public String deviceID;

    @ColumnInfo(name = "typeOfCart")
    public String typeOfCart;

    @ColumnInfo(name = "stock")
    public String stock;


    @ColumnInfo(name = "itemName")
    public String itemName;


    @ColumnInfo(name = "itemImage")
    public String itemImage;


    @ColumnInfo(name = "cartQty")
    public String cartQty;


    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "offer_price")
    public String offer_price;

//    @ColumnInfo(name = "final_price")
//    public String final_price;


    @ColumnInfo(name = "product_id")
    public String product_id;


    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public String createdDate;


    @ColumnInfo(name = "store_id")
    public String store_id;

    @ColumnInfo(name = "weight")
    public String weight;

    @ColumnInfo(name = "gst")
    public String gst;
    @ColumnInfo(name = "gst_mount")
    public String gstAmount;

    public CartItems(int cartID, String user_id, String deviceID, String typeOfCart, String itemName, String itemImage, String cartQty, String price, String offer_price, String product_id, String stock, String gst, String gstAmount) {
        this.cartID = cartID;
        this.user_id = user_id;
        this.deviceID = deviceID;
        this.typeOfCart = typeOfCart;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.cartQty = cartQty;
        this.price = price;
        this.product_id = product_id;
        this.offer_price = offer_price;
        this.stock = stock;
        this.gst = gst;
        this.gstAmount = gstAmount;

    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getTypeOfCart() {
        return typeOfCart;
    }

    public void setTypeOfCart(String typeOfCart) {
        this.typeOfCart = typeOfCart;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getCartQty() {
        return cartQty;
    }

    public void setCartQty(String cartQty) {
        this.cartQty = cartQty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(String gstAmount) {
        this.gstAmount = gstAmount;
    }
}

