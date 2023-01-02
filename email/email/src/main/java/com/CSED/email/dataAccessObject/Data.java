package com.CSED.email.dataAccessObject;
import com.fasterxml.jackson.databind.ObjectMapper;



import com.CSED.email.Account.Account;
import com.CSED.email.Email.Email;
import com.CSED.email.Folder.Folder;
import com.CSED.email.User.IUser;
import com.CSED.email.User.NullUser;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;


@Getter @Setter
public class Data implements IData{
    ArrayList<IUser> registeredUsers = new ArrayList<>();
    private static Data instance = new Data();

    public static Data getInstance(){
        return instance;
    }

    @Override
    public IUser getUserByAccount(Account account) {
        for(IUser user : registeredUsers){
            Account account1 = user.getAccount();
            if(account1.equals(account)){
                return user;
            }
        }
        return new NullUser();
    }

    @Override
    public IUser getUserByUsername(String username) {
        for(IUser user: registeredUsers){
            if(user.getUsername().equals(username))
                return user;
        }
        return new NullUser();
    }

    @Override
    public void addUser(IUser user){
        registeredUsers.add(user);
    }

    @Override
    public void setUsers(ArrayList<IUser> users) {
        this.registeredUsers = users;
    }

    @Override
    public void deleteEmail(String username,String folderName,int index){
        IUser user = this.getUserByUsername(username);
        Folder folder = user.getFolder(folderName);
        Email email = folder.deleteEmail(index);
        Folder trash = user.getFolder("Trash");
        trash.addEmail(email);
    }

    @Override
    public void restoreEmail(String username,String folderName,int index){
        IUser user = this.getUserByUsername(username);
        Folder folder = user.getFolder(folderName);
        Email email = folder.deleteEmail(index);
        Folder inbox = user.getFolder("Inbox");
        inbox.addEmail(email);
    }

    @Override
    public void saveData(){
        File file=new File("Data/users.json");
        try {
            ObjectMapper mapper=new ObjectMapper();
            String jsonStr=mapper.writeValueAsString(registeredUsers);
            System.out.println("\n"+jsonStr);
            FileWriter writer = new FileWriter(file);
            writer.write(jsonStr);
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
