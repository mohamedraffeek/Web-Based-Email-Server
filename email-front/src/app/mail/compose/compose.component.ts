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
  selectedFile: any;

  constructor(private service: BackendService){}

  getFile(event: any){
    this.selectedFile = event.target.files[0];
  }

  createEmail(){
    let attachment = new FormData();
    attachment.append("file", this.selectedFile);
    let receivers = this.to.split(", ");
    var priority = (<HTMLInputElement>document.getElementById("priority")).value;
    let email = new Email(Master.Username, receivers, this.subject, this.content, false, parseInt(priority), this.selectedFile);
    this.service.sendEmail(email).subscribe(response => {
      console.log(response);
      alert("Email sent successfully");
    });
  }

  draft(){
    let attachment = new FormData();
    attachment.append("file", this.selectedFile);
    let receivers = this.to.split(", ");
    var priority = (<HTMLInputElement>document.getElementById("priority")).value;
    let email = new Email(Master.Username, receivers, this.subject, this.content, false, parseInt(priority), this.selectedFile);
    this.service.draftEmail(email).subscribe(response => {
      console.log(response);
      alert("Email drafted successfully");
    });
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
