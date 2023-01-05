package com.CSED.email;

import com.CSED.email.Account.Account;
import com.CSED.email.Contact.Contact;
import com.CSED.email.Criteria.*;
import com.CSED.email.Email.Email;
import com.CSED.email.Folder.Folder;
import com.CSED.email.Master.Master;
import com.CSED.email.User.IUser;
import com.CSED.email.User.NullUser;
import com.CSED.email.User.User;
import com.CSED.email.dataAccessObject.Data;
import org.json.JSONException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import org.springframework.util.StringUtils;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/serve")
public class Controller {
    private final Data data = Data.getInstance();
    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody String receivedJSON) throws JSONException {
        JSONObject JO = new JSONObject(receivedJSON);
        Gson G = new Gson();
        Account account = G.fromJson(receivedJSON, Account.class);
        if(!data.getUserByUsername(account.get_username()).isNull())
            return "error";
        data.addUser(new User(account));
        addFolder("Inbox", account.get_username());
        addFolder("Sent", account.get_username());
        addFolder("Draft", account.get_username());
        addFolder("Trash", account.get_username());
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
                    if(sender.getFolder("Sent") == null){
                        sender.addFolder("Sent");
                        sender.addFolder("Inbox");
                        sender.addFolder("Trash");
                        sender.addFolder("Draft");
                    }
                    Folder sentFolder = sender.getFolder("Sent");
                    Folder inboxFolder = receiver.getFolder("Inbox");
                    sentFolder.addEmail(email);
                    inboxFolder.addEmail(email);
                }
            }
        }
        data.saveData();
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

    @PostMapping("/deleteEmailD/{username}/{index}")
    public void deleteEmailD(@PathVariable("index") int index, @PathVariable("username") String username){
        try {
            data.deleteEmail(username,"Draft",index);
        }catch(Exception e){
            System.out.println("Something Wrong in deletion");
        }
        data.saveData();
    }

    @PostMapping("/eraseEmailFromData/{username}/{index}")
    public void eraseEmailFromData(@PathVariable("index") int index, @PathVariable("username") String username){
        try {
            data.eraseEmail(username, index);
        }catch(Exception e){
            System.out.println("Something Wrong in erasing");
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
        data.saveData();
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

    @GetMapping("/searchContacts/{username}/{searchString}")
    public ArrayList<Contact> filterContacts(@PathVariable("username") String username, @PathVariable("searchString") String searchString){
        IUser user = data.getUserByUsername(username);
        if(user.isNull() || user.getContacts() == null)
            return null;
        CriteriaName criteria = new CriteriaName(searchString);
        System.out.println(user.searchContacts(criteria));
        return user.searchContacts(criteria);
    }

    @PostMapping("/addFolder/{folder}/{username}")
    public String addFolder(@PathVariable("folder") String folder, @PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(!user.isNull()){
            user.addFolder(folder);
        }
        data.saveData();
        return "Folder added successfully";
    }

    @PostMapping("/deleteFolder/{index}/{username}")
    public String deleteFolder(@PathVariable("index") int index, @PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(!user.isNull()){
            user.deleteFolder(index);
        }
        data.saveData();
        return "Folder deleted successfully";
    }

    @PostMapping("/renameFolder/{index}/{newName}/{username}")
    public String renameFolder(@PathVariable("index") int index, @PathVariable("newName") String newName, @PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(!user.isNull()){
            user.renameFolder(index, newName);
        }
        data.saveData();
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

    @PostMapping("/addContact/{name}/{emailAddress}/{username}")
    public String addContact(@PathVariable("name") String name, @PathVariable("emailAddress") String emailAddress, @PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(!user.isNull()){
            user.addContact(name, emailAddress);
        }
        data.saveData();
        return "Contact added successfully";
    }

    @PostMapping("/deleteContact/{index}/{username}")
    public String deleteContact(@PathVariable("index") int index, @PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(!user.isNull()){
            user.removeContact(index);
        }
        data.saveData();
        return "Contact deleted successfully";
    }

    @GetMapping("/getContacts/{username}")
    public ArrayList<Contact> getContacts(@PathVariable("username") String username){
        IUser user = data.getUserByUsername(username);
        if(user.isNull())
            return null;
        return user.getContacts();
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles) throws IOException {
        List<String> filenames = new ArrayList<>();
        for(MultipartFile file: multipartFiles){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if(!Files.exists(filePath)){
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }
}
