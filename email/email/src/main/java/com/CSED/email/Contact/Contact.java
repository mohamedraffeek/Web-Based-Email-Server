package com.CSED.email.Contact;

public class Contact {
    private String name;
    private String emailAddress;
    public Contact(String name, String emailAddress){
        this.name = name;
        this.emailAddress = emailAddress;
    }
    public String getName(){return this.name;}
    public String getEmailAddress(){return this.emailAddress;}
}
