package com.CSED.email.Folder;

import com.CSED.email.Criteria.Criteria;
import com.CSED.email.Email.Email;

import java.util.ArrayList;

public class Folder {
    private String name;
    private ArrayList<Email> content;

    public Folder(String name){
        this.name = name;
        this.content = new ArrayList<>();
    }

    public Folder(){
        content = new ArrayList<>();
    }

    public ArrayList<Email> search(Criteria criteria){
        ArrayList<Email> ret = criteria.meetCriteria(content);
        if(ret.size()==0)
            return new ArrayList<>();
        return ret;
    }

    public void addEmail(Email email){
        content.add(email);
    }

    public Email deleteEmail(int index){
        Email email = content.get(index);
        content.remove(index);
        return email;
    }

    public Email getEmail(int index){
        Email email = content.get(index);
        return email;
    }

    public void readEmail(Email email){
        for(Email email1: content){
            if(email1.getBody().equals(email.getBody())){
                email1.setRead(true);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Email> getContent() {
        return content;
    }

    public void setContent(ArrayList<Email> content) {
        this.content = content;
    }

}
