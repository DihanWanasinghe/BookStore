/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.models;

/**
 *
 * @author Hans
 */
public class CustomerDTO {
    
    private String customerId;
    private String name;
    private String email;
    private String password;
    
    public CustomerDTO()
    {}
    public CustomerDTO(String customerId, String name, String email, String password) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerID) {
        this.customerId = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "CustomerDTO{" + "customerID=" + customerId + ", name=" + name + ", email=" + email + ", password=" + password + '}';
    }
    
    
    
    
}
