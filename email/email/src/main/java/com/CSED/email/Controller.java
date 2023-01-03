package com.CSED.email;

import com.CSED.email.Account.Account;
import com.CSED.email.Criteria.*;
import com.CSED.email.Email.Email;
import com.CSED.email.Folder.Folder;
import com.CSED.email.Master.Master;
import com.CSED.email.User.IUser;
import com.CSED.email.User.NullUser;
import com.CSED.email.User.User;
import com.CSED.email.dataAccessObject.Data;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.PriorityQueue;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/serve")
public class Controller {
    private final Data data = Data.getInstance();

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody String receivedJSON) throws JSONException {
        JSONObject JO = new JSONObject(receivedJSON);
        Gson G = new Gson();
        Account account = G.fromJson(receivedJSON, Account.class);
        if(!data.getUserByUsername(account.get_username()).isNull())
            return "error";
        data.addUser(new User(account));
        data.saveData();
        return "added";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String receivedJSON) throws JSONException {
        JSONObject JO = new JSONObject(receivedJSON);
        Gson G = new Gson();
        Account account = G.fromJson(receivedJSON, Account.class);
        IUser potentialUser = data.getUserByUsername(account.get_username());
        if(!potentialUser.isNull() && account.get_password().equals(potentialUser.getPassword()))
            return "found";
        return "error";
    }

    @PostMapping("/saveMaster")
    @ResponseBody
    public void saveMaster(@RequestBody String receivedJSON) throws JSONException {
        Master.Username = receivedJSON;
    }

    @GetMapping("/getMaster")
    public String getMaster(){
        return Master.Username;
    }

    @PostMapping("/savePass")
    @ResponseBody
    public void savePass(@RequestBody String receivedJSON) throws JSONException {
        Master.Password = receivedJSON;
    }

    @GetMapping("/getPass")
    public String getPass(){
        return Master.Password;
    }

    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(@RequestBody String receivedJSON) throws JSONException {
        JSONObject JO = new JSONObject(receivedJSON);
        Gson G = new Gson();
        Email email = G.fromJson(receivedJSON, Email.class);
        IUser sender = data.getUserByUsername(email.getFrom());
        if(!sender.isNull()){
            ArrayList<String> receivers = new ArrayList<>(email.getTo());
            IUser receiver = new NullUser();
            for(int i = 0; i < receivers.size(); ++i){
                receiver = data.getUserByUsername(receivers.get(i));
                if(!sender.isNull() && !receiver.isNull()) {
                    Folder sentFolder = sender.getFolder("Sent");
                    Folder inboxFolder = receiver.getFolder("Inbox");
                    sentFolder.addEmail(email);
                    inboxFolder.addEmail(email);
                }
            }
        }
        return "email sent successfully";
    }

    @PostMapping("/draftEmail")
    @ResponseBody
    public String draftEmail(@RequestBody String receivedJSON) throws JSONException {
        JSONObject JO = new JSONObject(receivedJSON);
        Gson G = new Gson();
        Email email = G.fromJson(receivedJSON, Email.class);
        IUser sender = data.getUserByUsername(email.getFrom());
        if(!sender.isNull()){
            Folder draftFolder = sender.getFolder("Draft");
            draftFolder.addEmail(email);
        }
        data.saveData();
        return "email drafted successfully";
    }

    @PostMapping("/deleteEmail/{username}/{index}")
    public void deleteEmail(@PathVariable("index") int index, @PathVariable("username") String username){
        try {
            data.deleteEmail(username,"Inbox",index);
        }catch(Exception e){
            System.out.println("Something Wrong in deletion");
        }
        data.saveData();
    }

    @PostMapping("/deleteEmailS/{username}/{index}")
    public void deleteEmailS(@PathVariable("index") int index, @PathVariable("username") String username){
        try {
            data.deleteEmail(username,"Sent",index);
        }catch(Exception e){
            System.out.println("Something Wrong in deletion");
        }
        data.saveData();
    }

    @PostMapping("/restoreEmail/{username}/{index}")
    public void restoreEmail(@PathVariable("index") int index, @PathVariable("username") String username){
        try {
            data.restoreEmail(username,"Trash",index);
        }catch(Exception e){
            System.out.println("Something Wrong in restoring");
        }
        data.saveData();
    }

    @PostMapping("/readEmail/{folder}")
    @ResponseBody
    public String readEmail(@RequestBody String receivedJSON, @PathVariable("folder") String folder) throws JSONException {
        JSONObject JO = new JSONObject(receivedJSON);
        Gson G = new Gson();
        Email email = G.fromJson(receivedJSON, Email.class);
        IUser sender = data.getUserByUsername(email.getFrom());
        if(!sender.isNull()){
            ArrayList<String> receivers = new ArrayList<>(email.getTo());
            IUser receiver = new NullUser();
            for(int i = 0; i < receivers.size(); ++i){
                receiver = data.getUserByUsername(receivers.get(i));
                if(!sender.isNull() && !receiver.isNull()) {
                    Folder inboxFolder = receiver.getFolder(folder);
                    inboxFolder.readEmail(email);
                }
            }
        }
        return "email read successfully";
    }

    @GetMapping("/getEmails/{username}/{folder}")
    public ArrayList<Email> getEmails(@PathVariable("username") String username, @PathVariable("folder") String folder){
        IUser user = data.getUserByUsername(username);
        if(user.isNull() || user.getFolder(folder) == null)
            return null;
        return user.getFolder(folder).getContent();
    }

    @GetMapping("/sortEmails/{username}/{folder}")
    public ArrayList<Email> sortEmails(@PathVariable("username") String username, @PathVariable("folder") String folder){
        IUser user = data.getUserByUsername(username);
        if(user.isNull() || user.getFolder(folder) == null)
            return null;
        PriorityQueue<Email> pq = new PriorityQueue<>();
        for(int i = 0; i < user.getFolder(folder).getContent().size(); ++i){
            pq.add(user.getFolder(folder).getContent().get(i));
        }
        ArrayList<Email> ret = new ArrayList<>();
        for(int i = 0; i < user.getFolder(folder).getContent().size(); ++i){
            ret.add(pq.poll());
        }
        return ret;
    }

    @GetMapping("/filterEmails/{username}/{folder}/{filter}/{searchString}")
    public ArrayList<Email> filterEmails(@PathVariable("username") String username, @PathVariable("folder") String folder
                                        ,@PathVariable("filter") ArrayList<String> filter, @PathVariable("searchString") String searchString){
        IUser user = data.getUserByUsername(username);
        if(user.isNull() || user.getFolder(folder) == null)
            return null;
        Criteria criteria1 = null;
        Criteria criteria2 = null;
        Criteria criteria3 = null;
        Criteria criteria4 = null;
        Criteria criteria5 = null;
        if(filter.contains("from")){
            criteria1 = new CriteriaSender(searchString);
        }
        if(filter.contains("to")){
            criteria2 = new CriteriaReceiver(searchString);
        }
        Criteria or1 = new OrCriteria(criteria1, criteria2);
        if(filter.contains("Body")){
            criteria3 = new CriteriaBody(searchString);
        }
        if(filter.contains("Subject")){
            criteria4 = new CriteriaSubject(searchString);
        }
        if(filter.contains("Priority")){
            criteria5 = new CriteriaPriority(Integer.parseInt(searchString));
        }
        Criteria or2 = new OrCriteria(criteria3, criteria4);
        Criteria or3 = new OrCriteria(or2, or1);
        Criteria or4 = new OrCriteria(or3, criteria5);
        return user.getFolder(folder).search(or4);
    }

    @PostMapping("/addFolder/{folder}/{username}")
    public String addFolder(@PathVariable("folder") String folder, @PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(!user.isNull()){
            user.addFolder(folder);
        }
        return "Folder added successfully";
    }

    @PostMapping("/deleteFolder/{index}/{username}")
    public String deleteFolder(@PathVariable("index") int index, @PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(!user.isNull()){
            user.deleteFolder(index);
        }
        return "Folder deleted successfully";
    }

    @PostMapping("/renameFolder/{index}/{newName}/{username}")
    public String renameFolder(@PathVariable("index") int index, @PathVariable("newName") String newName, @PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(!user.isNull()){
            user.renameFolder(index, newName);
        }
        return "Folder renamed successfully";
    }

    @GetMapping("/getFolders/{username}")
    public ArrayList<Folder> getFolders(@PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(user.isNull())
            return null;
        return user.getFolders();
    }

    @PostMapping("/addToFolder/{username}/{index}/{folder}")
    public void addToFolder(@PathVariable("index") int index, @PathVariable("username") String username, @PathVariable("folder") String folder){
        try {
            data.moveToFolder(username,folder,index);
        }catch(Exception e){
            System.out.println("Something Wrong in moving");
        }
        data.saveData();
    }
}
