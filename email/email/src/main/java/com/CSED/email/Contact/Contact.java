package com.CSED.email.Contact;

import com.CSED.email.Criteria.Criteria;
import com.CSED.email.Criteria.CriteriaName;
import com.CSED.email.Email.Email;

import java.util.ArrayList;

public class Contact {
    private String name;
    private String emailAddress;
    public Contact(String name, String emailAddress){
        this.name = name;
        this.emailAddress = emailAddress;
    }
    public Contact(){

    }
    public String getName(){return this.name;}
    public String getEmailAddress(){return this.emailAddress;}
}
