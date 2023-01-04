package com.CSED.email.User;

import com.CSED.email.Account.Account;
import com.CSED.email.Contact.Contact;
import com.CSED.email.Criteria.CriteriaName;
import com.CSED.email.Email.Email;
import com.CSED.email.Folder.Folder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements IUser{
    private Account account;
    private ArrayList<Folder> folders;
    private ArrayList<Contact> contacts;

    public User(Account account){
        this.account = account;
        folders = new ArrayList<>();
        contacts = new ArrayList<>();
    }

    public User(){
        account = new Account();
        folders = new ArrayList<>();
        contacts = new ArrayList<>();
    }

    @Override
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String getUsername(){
        return this.account.get_username();
    }

    @Override
    public String getPassword(){
        return this.account.get_password();
    }

    @Override
    public Boolean isNull(){
        return false;
    }

    @Override
    public Folder getFolder(String name){
        for(Folder folder: folders){
            if(folder.getName().equalsIgnoreCase(name)){
                return folder;
            }
        }
        return null;
    }

    @Override
    public void addFolder(String name){
        folders.add(new Folder(name));
    }

    @Override
    public void deleteFolder(int index){
        if(index < 4){
            System.out.println("Attempt to delete a main folder detected");
            return;
        }
        folders.remove(index + 4);
    }
    @Override
    public void renameFolder(int index, String newName){
        if(index < 4){
            System.out.println("Attempt to rename a main folder detected");
            return;
        }
        try {
            Folder temp = folders.remove(index + 4);
            temp.setName(newName);
            folders.add(temp);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Attempt to rename a main folder detected");
        }
    }

    @Override
    public ArrayList<Folder> getFolders(){
        ArrayList<Folder> ret = new ArrayList<>();
        for(Folder folder: folders){

            ret.add(folder);
        }
        return ret;
    }

    @Override
    public void addContact(String name, String emailAddress){
        contacts.add(new Contact(name, emailAddress));
    }
    @Override
    public void removeContact(int index){
        contacts.remove(index);
    }
    @Override
    public ArrayList<Contact> getContacts(){
        return contacts;
    }
    @Override
    public ArrayList<Contact> searchContacts(CriteriaName criteriaName){
        ArrayList<Contact> ret = criteriaName.meetCriteria(contacts);
        if(ret.size()==0)
            return new ArrayList<>();
        return ret;
    }
}
