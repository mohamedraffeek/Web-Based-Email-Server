package com.CSED.email.Criteria;

import com.CSED.email.Email.Email;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class CriteriaReceiver implements Criteria{
    private String receiver;

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> ret = new ArrayList<>();
        for(Email email: emails){
            ArrayList<String> receiversArr=new ArrayList<>(email.getTo());
            for(String emailReceiver :receiversArr) {
                if (emailReceiver.equals(receiver))
                    ret.add(email);
            }
        }
        return ret;
    }
}