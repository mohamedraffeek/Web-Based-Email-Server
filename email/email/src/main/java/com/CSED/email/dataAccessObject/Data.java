package com.CSED.email.dataAccessObject;
import com.CSED.email.User.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



import com.CSED.email.Account.Account;
import com.CSED.email.Email.Email;
import com.CSED.email.Folder.Folder;
import com.CSED.email.User.IUser;
import com.CSED.email.User.NullUser;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.parser.JSONParser;

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
    public void eraseEmail(String username, int index){
        IUser user = this.getUserByUsername(username);
        Folder folder = user.getFolder("Trash");
        Email email = folder.deleteEmail(index);
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
    public void moveToFolder(String username,String folderName,int index){
        IUser user = this.getUserByUsername(username);
        Folder folder = user.getFolder("Inbox");
        Email email = folder.getEmail(index);
        Folder to = user.getFolder(folderName);
        to.addEmail(email);
    }

    @Override
    public void saveData(){
        File file = new File("Data/users.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = mapper.writeValueAsString(registeredUsers);
            System.out.println("\n" + jsonStr);
            mapper.writeValue(file, registeredUsers);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void loadData() throws IOException {
        File file = new File("Data/users.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.setUsers(new ArrayList<IUser>(
                    mapper.readValue(file,
                            new TypeReference<ArrayList<User>>() {
                            })));
        }catch (MismatchedInputException e){
            System.out.println("Error");
        }
        System.out.println(registeredUsers);
    }
}
