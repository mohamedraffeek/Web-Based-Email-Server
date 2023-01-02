package com.CSED.email.User;

import com.CSED.email.Account.Account;
import com.CSED.email.Folder.Folder;

public interface IUser {
    Boolean isNull();
    String getUsername();
    String getPassword();
    Account getAccount();
    Folder getFolder(String name);
}
