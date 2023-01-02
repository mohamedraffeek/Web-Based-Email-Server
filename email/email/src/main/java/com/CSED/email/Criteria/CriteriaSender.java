package com.CSED.email.Criteria;

import com.CSED.email.Email.Email;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class CriteriaSender implements Criteria{
    private String sender;

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> ret = new ArrayList<>();
        for(Email email: emails){
            if(email.getFrom().equals(sender))
                ret.add(email);
        }
        return ret;
    }
}