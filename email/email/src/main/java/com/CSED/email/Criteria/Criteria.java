package com.CSED.email.Criteria;

import com.CSED.email.Email.Email;

import java.util.ArrayList;

public interface Criteria {
    ArrayList<Email> meetCriteria(ArrayList<Email> emails);
}
