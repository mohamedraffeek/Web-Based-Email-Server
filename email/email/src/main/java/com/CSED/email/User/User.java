package com.CSED.email.User;

import com.CSED.email.Account.Account;
import com.CSED.email.Contact.Contact;
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
        folders.add(new Folder("Inbox"));
        folders.add(new Folder("Sent"));
        folders.add(new Folder("Trash"));
        folders.add(new Folder("Draft"));
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
        folders.remove(index + 4);
    }
    @Override
    public void renameFolder(int index, String newName){
        Folder temp = folders.remove(index + 4);
        temp.setName(newName);
        folders.add(temp);
    }

    @Override
    public ArrayList<Folder> getFolders(){
        ArrayList<Folder> ret = new ArrayList<>();
        for(Folder folder: folders){
            if(folder.getName().equalsIgnoreCase("Inbox") || folder.getName().equalsIgnoreCase("Draft")
             || folder.getName().equalsIgnoreCase("Sent") || folder.getName().equalsIgnoreCase("Trash")){
                continue;
            }
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
}
