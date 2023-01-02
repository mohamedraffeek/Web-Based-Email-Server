package com.CSED.email.Criteria;

import com.CSED.email.Email.Email;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class CriteriaSubject implements Criteria{
    private String subject;

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> ret = new ArrayList<>();
        for(Email email: emails){
            if(email.getSubject().contains(subject))
                ret.add(email);
        }
        return ret;
    }
}