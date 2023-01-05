package com.CSED.email.dataAccessObject;

import com.CSED.email.Account.Account;
import com.CSED.email.User.IUser;

import java.io.IOException;
import java.util.ArrayList;

public interface IData {
    IUser getUserByAccount(Account account);
    IUser getUserByUsername(String Username);
    void addUser(IUser user);
    void setUsers(ArrayList<IUser> users);
    void deleteEmail(String username, String folder, int index);
    void eraseEmail(String username, int index);
    void restoreEmail(String username, String folder, int index);
    void moveToFolder(String username, String folder, int index);
    void saveData();
    void loadData() throws IOException;
}
