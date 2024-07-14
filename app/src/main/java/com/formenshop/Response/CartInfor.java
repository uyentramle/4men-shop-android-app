package com.formenshop.Response;

import java.util.List;

public class CartInfor {
        private List<CartItem> items;
        private double totalPrice;

        // Getters and setters

        public static class CartItem {
            private String productName;
            private int quantity;
            private double price;

            public CartItem(String productName, int quantity, double price) {
                this.productName = productName;
                this.quantity = quantity;
                this.price = price;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
            // Getters and setters
        }

        public int getTotalQuantity() {
            int totalQuantity = 0;
            for (CartItem item : items) {
                totalQuantity += item.getQuantity();
            }
            return totalQuantity;
        }

        public double getTotalPrice() {
            double totalPrice = 0;
            for (CartItem item : items) {
                totalPrice += item.getPrice() * item.getQuantity();
            }
            return totalPrice;
        }

}
