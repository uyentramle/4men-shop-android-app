package com.formenshop.Request;

public class RegisterRequest {
    private String codeemail;
    private String email;
    private String password;


    // Constructor


    public RegisterRequest(String codeemail, String email, String password) {

        this.codeemail = codeemail;
        this.email = email;
        this.password = password;
    }

    public String getCodeemail() {
        return codeemail;
    }

    public void setCodeemail(String codeemail) {
        this.codeemail = codeemail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
