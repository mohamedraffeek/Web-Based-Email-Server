package com.CSED.email.Account;

public class Account {
    private String _username;
    private String _password;

    @Override
    public boolean equals(Object account){
        Account combination = (Account) account;
        return _username.equals(combination.get_username()) && _password.equals((combination.get_password()));
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }
}
