package com.CSED.email.Criteria;

import com.CSED.email.Email.Email;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class CriteriaBody implements Criteria{
    private String body;

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> ret = new ArrayList<>();
        for(Email email: emails){
            if(email.getBody().contains(body))
                ret.add(email);
        }
        return ret;
    }
}
