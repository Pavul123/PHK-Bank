package com.bank.model;

public class Customer {
    private int customerId;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String password;
    private String securityQuestion;
    private String answer;
    private String status;

    public Customer() {}

    public Customer(int customerId, String name, String phone, String email,
                    String address, String status) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
    }

    // Getters and Setters
    public int getCustomerId()                    { return customerId; }
    public void setCustomerId(int customerId)     { this.customerId = customerId; }

    public String getName()                       { return name; }
    public void setName(String name)              { this.name = name; }

    public String getPhone()                      { return phone; }
    public void setPhone(String phone)            { this.phone = phone; }

    public String getEmail()                      { return email; }
    public void setEmail(String email)            { this.email = email; }

    public String getAddress()                    { return address; }
    public void setAddress(String address)        { this.address = address; }

    public String getPassword()                   { return password; }
    public void setPassword(String password)      { this.password = password; }

    public String getSecurityQuestion()           { return securityQuestion; }
    public void setSecurityQuestion(String q)     { this.securityQuestion = q; }

    public String getAnswer()                     { return answer; }
    public void setAnswer(String answer)          { this.answer = answer; }

    public String getStatus()                     { return status; }
    public void setStatus(String status)          { this.status = status; }

    @Override
    public String toString() {
        return "Customer{id=" + customerId + ", name=" + name + ", status=" + status + "}";
    }
}
