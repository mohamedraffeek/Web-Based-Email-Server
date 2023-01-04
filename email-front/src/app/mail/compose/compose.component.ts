import { HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Email } from 'src/app/email/email';
import { Master } from 'src/app/master/master';
import { BackendService } from 'src/app/Service/backend.service';

@Component({
  selector: 'app-compose',
  templateUrl: './compose.component.html',
  styleUrls: ['./compose.component.css']
})
export class ComposeComponent implements OnInit{
  ids: string[] = ["1", "2", "3", "4", "5", "6", "7"];

  to!: string;
  subject!: string;
  content!: string;
  filenames: string[] = [];

  constructor(private service: BackendService){}

  createEmail(){
    let receivers = this.to.split(", ");
    var priority = (<HTMLInputElement>document.getElementById("priority")).value;
    let email = new Email(Master.Username, receivers, this.subject, this.content, false, parseInt(priority), this.filenames);
    this.service.sendEmail(email).subscribe(response => {
      console.log(response);
      alert("Email sent successfully");
    });
  }

  draft(){
    let receivers = this.to.split(", ");
    var priority = (<HTMLInputElement>document.getElementById("priority")).value;
    let email = new Email(Master.Username, receivers, this.subject, this.content, false, parseInt(priority), this.filenames);
    this.service.draftEmail(email).subscribe(response => {
      console.log(response);
      alert("Email drafted successfully");
    });
  }

  onUploadFiles(files: File[]): void{
    this.filenames = [];
    const formData = new FormData();
    for(const file of files){
      formData.append('files', file, file.name);
      this.filenames.push(file.name);
    }
    console.log(this.filenames);
    this.service.upload(formData).subscribe(
      event => {
        console.log(event);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    )
  }

  ngOnInit(): void {
    for(let i of this.ids){
      var button = document.getElementById(i);
      if(button != null) button.style.border = "none";
    }
    var button = document.getElementById("1");
    if(button != null) button.style.border = "2px solid #82808054";
  }

}
