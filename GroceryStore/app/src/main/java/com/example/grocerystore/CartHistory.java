package com.example.grocerystore;

public class CartHistory {


    private String MedName;

    public CartHistory(String name, double totalPrice, String username, String email, int quantity) {
    }

    public String getMedName() {
        return MedName;
    }

    public void setMedName(String medName) {
        MedName = medName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatName() {
        return PatName;
    }

    public void setPatName(String patName) {
        PatName = patName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Double getFee() {
        return Fee;
    }

    public void setFee(Double fee) {
        Fee = fee;
    }

    private String email;
    private String PatName;
    private int Quantity;
    private Double Fee;

    public CartHistory(String medName, String email, String patName, int quantity, Double fee) {
        MedName = medName;
        this.email = email;
        PatName = patName;
        Quantity = quantity;
        Fee = fee;
    }
}
