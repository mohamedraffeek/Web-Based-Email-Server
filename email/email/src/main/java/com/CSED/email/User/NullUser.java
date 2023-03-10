package com.CSED.email.User;

import com.CSED.email.Account.Account;
import com.CSED.email.Contact.Contact;
import com.CSED.email.Criteria.CriteriaName;
import com.CSED.email.Folder.Folder;

import java.util.ArrayList;

public class NullUser implements IUser{
    @Override
    public Boolean isNull(){
        return true;
    }

    @Override
    public String getUsername(){
        return null;
    }

    @Override
    public String getPassword(){
        return null;
    }

    @Override
    public Account getAccount(){
        return null;
    }

    @Override
    public Folder getFolder(String name){
        return null;
    }

    @Override
    public void addFolder(String name){
    }
    @Override
    public void deleteFolder(int index){
    }
    @Override
    public void renameFolder(int index, String n){
    }
    @Override
    public ArrayList<Folder> getFolders(){
        return null;
    }
    @Override
    public void addContact(String name, String ad){
    }
    @Override
    public void removeContact(int index){
    }
    @Override
    public ArrayList<Contact> getContacts(){
        return null;
    }
    @Override
    public ArrayList<Contact> searchContacts(CriteriaName criteriaName){
        return null;
    }
}
