import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Email } from 'src/app/email/email';
import { Master } from 'src/app/master/master';
import { BackendService } from 'src/app/Service/backend.service';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css']
})
export class InboxComponent implements OnInit{
  ids: string[] = ["1", "2", "3", "4", "5", "6", "7"];
  
  @ViewChild('myDisplay') myDisp!: ElementRef;

  to!: Array<string>;
  subject!: string;
  from!:string;
  content!: string;
  date!: string;
  attachment: string[];
  email!: Email;
  emails: Email[] = [];
  allEmails: Email[] = [];
  folderName!: string;

  prioritySort: boolean = true;
  searchString: string = '';
  filterTo: boolean = false;
  filterFrom: boolean = false;
  filterBody: boolean = false;
  filterSubject: boolean = false;
  filterPrio: boolean = false;

  pageNumber: number = 1;
  selectAll: boolean = false;
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

  loadEmails(){
    this.service.getEmails(Master.Username, "Inbox").subscribe((mailList: Array<Email>) => {
      this.allEmails = [];
      if(mailList != null){
        mailList.forEach(
          (email: Email) => {
            this.allEmails.push(Email.createEmailFromBack(email));
          }
        )
        this.allEmails.reverse();
        this.emails = this.allEmails.slice((this.pageNumber - 1) * 14, this.allEmails.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allEmails.length);
      }
    })
  }

  deleteEmails(){
    for(let i = 0; i < 14; i++){
      if(this.checkboxes[i].isChecked){
        this.checkboxes[i].isChecked = false;
        console.log(i);
        this.service.deleteEmail((this.allEmails.length - ((this.pageNumber - 1) * 14 + i) - 1)).subscribe(() =>{
          this.allEmails = [];
          this.emails = [];
          this.loadEmails();
        }
        );
      }
    }
  }

  addToFolder(){
    for(let i = 0; i < 14; i++){
      if(this.checkboxes[i].isChecked){
        this.checkboxes[i].isChecked = false;
        console.log(i);
        this.service.addToFolder((this.allEmails.length - ((this.pageNumber - 1) * 14 + i) - 1), this.folderName).subscribe(() =>{
          alert("Email Added to folder " + this.folderName + " successfully");
        }
        );
      }
    }
    
  }

  search(){
    if(!this.filterBody && !this.filterFrom && !this.filterSubject && !this.filterTo && this.searchString.length==0)
      this.loadEmails();
    var filters=Array<string>();
    if(this.filterTo){
      filters.push("to");
    }
    if(this.filterFrom){
      filters.push("from");
    }
    if(this.filterBody){
      filters.push("Body");
    }
    if(this.filterSubject){
      filters.push("Subject");
    }
    if(this.filterPrio){
      filters.push("Priority");
    }
    this.service.filterEmails(Master.Username, "Inbox", filters, this.searchString).subscribe((mailList: Array<Email>) => {
        this.allEmails = [];
        if(mailList.length == 0){
          this.emails = [];
          return;
        }
        if(mailList != null){
          mailList.forEach(
            (email: Email) => {
              this.allEmails.push(Email.createEmailFromBack(email));
            }
          )
          this.allEmails.reverse();
          this.emails = this.allEmails.slice((this.pageNumber - 1) * 14, this.allEmails.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allEmails.length);
        }
      }
    )
  }

  sortEmails(){
    this.prioritySort = !this.prioritySort;
    if(this.prioritySort){
      this.loadEmails();
      return;
    }
    this.service.sortEmails(Master.Username, "Inbox").subscribe((mailList: Array<Email>) => {
      this.allEmails = [];
      if(mailList != null){
        mailList.forEach(
          (email: Email) => {
            console.log(email);
            this.allEmails.push(Email.createEmailFromBack(email));
          }
        )
        this.allEmails.reverse();
        this.emails = this.allEmails.slice((this.pageNumber - 1) * 14, this.allEmails.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allEmails.length);
      }
    })

  }

  refresh(){
    this.loadEmails();
  }

  toggleCheckboxes(){
    for(let i = 0; i < 14; i++){
      this.checkboxes[i].isChecked = this.selectAll;
    }
  }

  on(mailNumber: number){
    this.myDisp.nativeElement.style.display = 'block';
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

  reply(){
    this.router.navigate(["/mail/compose"]);
  }

  next(){
    this.pageNumber += 1;
    this.emails = this.allEmails.slice((this.pageNumber - 1) * 14, this.allEmails.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allEmails.length);
  }

  prev(){
    this.pageNumber -= 1;
    this.emails = this.allEmails.slice((this.pageNumber - 1) * 14, this.allEmails.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allEmails.length);
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


  ngOnInit(): void {
    for(let i of this.ids){
      var button = document.getElementById(i);
      if(button != null) button.style.border = "none";
    }
    var button = document.getElementById("2");
    if(button != null) button.style.border = "2px solid #82808054";
    this.loadEmails();
  }

}
