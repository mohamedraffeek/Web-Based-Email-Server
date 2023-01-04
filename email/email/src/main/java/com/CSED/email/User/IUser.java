package com.CSED.email.User;

import com.CSED.email.Account.Account;
import com.CSED.email.Contact.Contact;
import com.CSED.email.Criteria.CriteriaName;
import com.CSED.email.Folder.Folder;

import java.util.ArrayList;

public interface IUser {
    Boolean isNull();
    String getUsername();
    String getPassword();
    Account getAccount();
    Folder getFolder(String name);
    void addFolder(String name);
    void deleteFolder(int index);
    void renameFolder(int index, String newName);
    ArrayList<Folder> getFolders();
    void addContact(String name, String emailAddress);
    void removeContact(int index);
    ArrayList<Contact> getContacts();
    ArrayList<Contact> searchContacts(CriteriaName criteriaName);
}
