package com.CSED.email.Criteria;

import com.CSED.email.Email.Email;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class OrCriteria implements Criteria{
    private Criteria criteria;
    private Criteria otherCriteria;

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        if(criteria==null && otherCriteria!=null)
            return otherCriteria.meetCriteria(emails);
        if(criteria!=null && otherCriteria==null)
            return criteria.meetCriteria(emails);
        if(criteria==null && otherCriteria==null)
            return new ArrayList<Email>();

        ArrayList<Email> set1 = criteria.meetCriteria(emails);
        ArrayList<Email> set2 = otherCriteria.meetCriteria(emails);
        ArrayList<Email> ret = (ArrayList<Email>) set2.clone();
        for(Email email: set1){
            if(!ret.contains(email))
                ret.add(email);
        }
        return ret;
    }
}
