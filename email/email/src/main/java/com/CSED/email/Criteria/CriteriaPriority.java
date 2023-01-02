package com.CSED.email.Criteria;

import com.CSED.email.Email.Email;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class CriteriaPriority implements Criteria{
    private Integer priority;

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> ret = new ArrayList<>();
        for(Email email: emails){
            if(email.getPriority().equals(priority))
                ret.add(email);
        }
        return ret;
    }
}