package com.CSED.email.User;

import com.CSED.email.Account.Account;
import com.CSED.email.Folder.Folder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements IUser{
    private Account account;
    private ArrayList<Folder> folders;

    public User(Account account){
        this.account = account;
        folders = new ArrayList<>();
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
}
