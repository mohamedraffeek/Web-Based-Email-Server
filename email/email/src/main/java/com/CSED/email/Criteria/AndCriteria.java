package com.CSED.email.Criteria;

import com.CSED.email.Email.Email;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class AndCriteria implements Criteria{
    private Criteria criteria;
    private Criteria otherCriteria;

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> set1 = criteria.meetCriteria(emails);
        ArrayList<Email> set2 = otherCriteria.meetCriteria(emails);
        ArrayList<Email> ret = new ArrayList<>();
        for(Email email: set1){
            if(set2.contains(email))
                ret.add(email);
        }
        return ret;
    }
}
