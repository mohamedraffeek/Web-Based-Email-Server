import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Email } from 'src/app/email/email';
import { Folder } from 'src/app/folder';
import { Master } from 'src/app/master/master';
import { BackendService } from 'src/app/Service/backend.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-folders',
  templateUrl: './folders.component.html',
  styleUrls: ['./folders.component.css']
})
export class FoldersComponent implements OnInit{
  ids: string[] = ["1", "2", "3", "4", "5", "6", "14"];
  
  @ViewChild('myDisplay') myDisp!: ElementRef;
  @ViewChild('myDisplay2') myDisp2!: ElementRef;

  folders: Folder[] = [];
  allFolders: Folder[] = [];
  newName!: string;
  rename!: string;
  emails: Email[] = [];
  allEmails: Email[] = [];
  to!: Array<string>;
  subject!: string;
  from!:string;
  content!: string;
  date!: string;
  attachment: string[];
  pageNumber: number = 1;
  pageNumber1: number = 1;
  checkboxes: any[] = [
    {value: '1', isChecked: false },
    {value: '2', isChecked: false },
    {value: '3', isChecked: false },
    {value: '4', isChecked: false },
    {value: '5', isChecked: false },
    {value: '6', isChecked: false },
    {value: '14', isChecked: false },
    {value: '8', isChecked: false },
    {value: '9', isChecked: false },
    {value: '10', isChecked: false },
    {value: '11', isChecked: false },
    {value: '12', isChecked: false },
    {value: '13', isChecked: false },
    {value: '14', isChecked: false },
  ]

  constructor(private service: BackendService, private router: Router){}

  loadFolders(){
    this.service.getFolders(Master.Username).subscribe((folderList: Array<Folder>) => {
      this.allFolders = [];
      if(folderList != null){
        folderList.forEach(
          (folder: Folder) => {
            if(!Folder.createFolderFromBack(folder).getName().match("Inbox") && !Folder.createFolderFromBack(folder).getName().match("Sent")
            && !Folder.createFolderFromBack(folder).getName().match("Draft") && !Folder.createFolderFromBack(folder).getName().match("Trash"))
            this.allFolders.push(Folder.createFolderFromBack(folder));
          }
        )
        this.folders = this.allFolders.slice((this.pageNumber - 1) * 14, this.allFolders.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allFolders.length);
      }
    })
  }

  deleteFolder(){
    for(let i = 0; i < 14; i++){
      if(this.checkboxes[i].isChecked){
        this.checkboxes[i].isChecked = false;
        console.log(i);
        this.service.deleteFolder((this.allFolders.length - ((this.pageNumber - 1) * 14 + i) - 1), Master.Username).subscribe(() =>{
          this.allFolders = [];
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
      this.allFolders = [];
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
        this.service.renameFolder((this.allFolders.length - ((this.pageNumber - 1) * 14 + i) - 1), this.rename, Master.Username).subscribe(() =>{
          this.allFolders = [];
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
    this.allEmails = [];
    this.service.getEmails(Master.Username, this.folders[folderNumber].getName()).subscribe((mailList: Array<Email>) => {
      if(mailList != null){
        mailList.forEach(
          (email: Email) => {
            this.allEmails.push(Email.createEmailFromBack(email));
          }
        )
        this.allEmails.reverse();
        this.emails = this.allEmails.slice((this.pageNumber1 - 1) * 7, this.allEmails.length - (this.pageNumber1 - 1) * 7 > 7 ? this.pageNumber1 * 7 : this.allEmails.length);
      }
    })
  }

  on(folderNumber: number){
    this.loadEmails(folderNumber);
    this.myDisp.nativeElement.style.display = 'block';
  }

  on1(mailNumber: number){
    this.myDisp.nativeElement.style.display = 'none';
    this.myDisp2.nativeElement.style.display = 'block';
    this.to = this.emails[mailNumber].getTo();
    this.from = this.emails[mailNumber].getFrom();
    this.subject = this.emails[mailNumber].getSubject();
    this.content = this.emails[mailNumber].getBody();
    this.date = this.emails[mailNumber].getDate();
    this.attachment = this.emails[mailNumber].getAttachment();
    console.log(this.attachment);
    if(!this.emails[mailNumber].getRead()){
      this.emails[mailNumber].toggleRead();
      this.service.readEmail(this.emails[mailNumber], "Inbox").subscribe();
    }
  }

  off(){
    this.myDisp.nativeElement.style.display = "none";
  }

  next(){
    this.pageNumber += 1;
    this.folders = this.allFolders.slice((this.pageNumber - 1) * 14, this.allFolders.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allFolders.length);
  }

  prev(){
    this.pageNumber -= 1;
    this.folders = this.allFolders.slice((this.pageNumber - 1) * 14, this.allFolders.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allFolders.length);
  }

  next1(){
    this.pageNumber1 += 1;
    this.emails = this.allEmails.slice((this.pageNumber1 - 1) * 7, this.allEmails.length - (this.pageNumber1 - 1) * 7 > 7 ? this.pageNumber1 * 7 : this.allEmails.length);
  }

  prev1(){
    this.pageNumber1 -= 1;
    this.emails = this.allEmails.slice((this.pageNumber1 - 1) * 7, this.allEmails.length - (this.pageNumber1 - 1) * 7 > 7 ? this.pageNumber1 * 7 : this.allEmails.length);
  }

  back(){
    this.myDisp.nativeElement.style.display = "block";
    this.myDisp2.nativeElement.style.display = "none";
  }

  onDownloadFiles(filename: string): void{
    this.service.download(filename).subscribe(
      event => {
        console.log(event);
        this.reportProgress(event, filename);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    )
  }

  private reportProgress(event: HttpEvent<string[] | Blob>, name: string): void {
    switch(event.type){
      case HttpEventType.UploadProgress:
        break;
      case HttpEventType.DownloadProgress:
        break;
      case HttpEventType.ResponseHeader:
        console.log("Header returned", event);
        break;
      case HttpEventType.Response:
        if(event.body instanceof Array){
          for(const filename of event.body){
            this.attachment.unshift(filename);
          }
        }else{
          saveAs(new File([event.body], name,
            {type: `${event.headers.get('Content-Type')};charset=utf-8`}));
        }
        break;
      default:
        console.log(event);
    }
  }

  reply(){
    this.router.navigate(["/mail/compose"]);
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
