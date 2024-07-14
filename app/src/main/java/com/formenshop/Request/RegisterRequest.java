package com.formenshop.Request;

public class RegisterRequest {
    private String codeemail;
    private String email;
    private String password;


    // Constructor
    public RegisterRequest(String codeemail,String email, String password) {
        this.codeemail = codeemail;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public String getUsername() {
        return codeemail;
    }

    public void setUsername(String username) {
        this.codeemail = codeemail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
