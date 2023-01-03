import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Email } from 'src/app/email/email';
import { Folder } from 'src/app/folder';
import { Master } from 'src/app/master/master';
import { BackendService } from 'src/app/Service/backend.service';

@Component({
  selector: 'app-folders',
  templateUrl: './folders.component.html',
  styleUrls: ['./folders.component.css']
})
export class FoldersComponent implements OnInit{
  ids: string[] = ["1", "2", "3", "4", "5", "6", "7"];
  
  @ViewChild('myDisplay') myDisp!: ElementRef;

  folders: Folder[] = [];
  newName!: string;
  rename!: string;
  emails: Email[] = [];
  to!: Array<string>;
  subject!: string;
  from!:string;
  content!: string;
  date!: string;
  attachment!: Object;
  checkboxes: any[] = [
    {value: '1', isChecked: false },
    {value: '2', isChecked: false },
    {value: '3', isChecked: false },
    {value: '4', isChecked: false },
    {value: '5', isChecked: false },
    {value: '6', isChecked: false },
    {value: '7', isChecked: false },
    {value: '8', isChecked: false },
    {value: '9', isChecked: false },
    {value: '10', isChecked: false },
    {value: '11', isChecked: false },
    {value: '12', isChecked: false },
    {value: '13', isChecked: false },
    {value: '14', isChecked: false }
  ]

  constructor(private service: BackendService, private router: Router){}

  loadFolders(){
    this.service.getFolders(Master.Username).subscribe((folderList: Array<Folder>) => {
      this.folders = [];
      if(folderList != null){
        folderList.forEach(
          (folder: Folder) => {
            this.folders.push(Folder.createFolderFromBack(folder));
          }
        )
      }
    })
  }

  deleteFolder(){
    for(let i = 0; i < 14; i++){
      if(this.checkboxes[i].isChecked){
        this.checkboxes[i].isChecked = false;
        console.log(i);
        this.service.deleteFolder(i, Master.Username).subscribe(() =>{
          this.folders = [];
          this.loadFolders();
        }
        );
      }
    }
  }

  addFolder(){
    this.service.addFolder(this.newName, Master.Username).subscribe((response) =>{
      console.log(response);
      this.folders = [];
      this.loadFolders();
    }
    );
  }

  renameFolder(){
    for(let i = 0; i < 14; i++){
      if(this.checkboxes[i].isChecked){
        this.checkboxes[i].isChecked = false;
        console.log(i);
        this.service.renameFolder(i, this.rename, Master.Username).subscribe(() =>{
          this.folders = [];
          this.loadFolders();
        }
        );
      }
    }
  }

  refresh(){
    this.loadFolders();
  }

  loadEmails(folderNumber: number){
    this.emails = [];
    this.service.getEmails(Master.Username, this.folders[folderNumber].getName()).subscribe((mailList: Array<Email>) => {
      if(mailList != null){
        mailList.forEach(
          (email: Email) => {
            this.emails.push(Email.createEmailFromBack(email));
          }
        )
      }
    })
  }

  on(mailNumber: number){
    this.loadEmails(mailNumber);
    this.myDisp.nativeElement.style.display = 'block';
    this.to = this.emails[mailNumber].getTo();
    this.from = this.emails[mailNumber].getFrom();
    this.subject = this.emails[mailNumber].getSubject();
    this.content = this.emails[mailNumber].getBody();
    this.date = this.emails[mailNumber].getDate();
    this.attachment = this.emails[mailNumber].getAttachment();
    if(!this.emails[mailNumber].getRead()){
      this.emails[mailNumber].toggleRead();
      this.service.readEmail(this.emails[mailNumber], "Inbox").subscribe();
    }
  }

  off(){
    this.myDisp.nativeElement.style.display = "none";
  }

  ngOnInit(): void {
    for(let i of this.ids){
      var button = document.getElementById(i);
      if(button != null) button.style.border = "none";
    }
    var button = document.getElementById("6");
    if(button != null) button.style.border = "2px solid #82808054";
    this.loadFolders();
  }

}
