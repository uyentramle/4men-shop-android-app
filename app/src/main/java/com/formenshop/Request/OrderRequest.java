package com.formenshop.Request;

import java.util.List;

public class OrderRequest {
    private String name;
    private String phone;
    private String addressDetail;
    private String paymentMethod;
    private List<OrderItem> listOrders;

    // Constructor
    public OrderRequest(String name, String phone, String addressDetail, String paymentMethod, List<OrderItem> listOrders) {
        this.name = name;
        this.phone = phone;
        this.addressDetail = addressDetail;
        this.paymentMethod = paymentMethod;
        this.listOrders = listOrders;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItem> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<OrderItem> listOrders) {
        this.listOrders = listOrders;
    }

    // Inner class for OrderItem
    public static class OrderItem {
        private int productID;
        private int quantity;

        // Constructor
        public OrderItem(int productID, int quantity) {
            this.productID = productID;
            this.quantity = quantity;
        }

        // Getters and Setters
        public int getProductID() {
            return productID;
        }

        public void setProductID(int productID) {
            this.productID = productID;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
