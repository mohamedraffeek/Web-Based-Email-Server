package com.CSED.email.Criteria;

import com.CSED.email.Contact.Contact;
import com.CSED.email.Email.Email;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class CriteriaName{
    private String name;

    public ArrayList<Contact> meetCriteria(ArrayList<Contact> contacts) {
        ArrayList<Contact> ret = new ArrayList<>();
        for(Contact contact: contacts){
            if(contact.getName().equalsIgnoreCase(name))
                ret.add(contact);
        }
        return ret;
    }
}