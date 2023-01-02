import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Master } from '../master/master';
import { BackendService } from '../Service/backend.service';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.css']
})
export class MailComponent implements OnInit{
  ids: string[] = ["1", "2", "3", "4", "5", "6", "7"];
  username!: string;
  password!: string;

  constructor(private service: BackendService, private router: Router){
  }

  border(id: string){
    for(let i of this.ids){
      var button = document.getElementById(i);
      if(button != null) button.style.border = "none";
    }
    var bu = document.getElementById(id);
    if(bu != null) bu.style.border = "2px solid #82808054";
  }

  logout(){
    Master.Username = "";
    Master.Password = "";
    this.router.navigate(["/login"]);
  }

  ngOnInit(): void {
    this.service.getMaster().subscribe(response => {
      this.username = response.replace(/^"(.*)"$/, '$1');
      Master.Username = this.username;
    })
    this.service.getPass().subscribe(response => {
      this.password = response.replace(/^"(.*)"$/, '$1');
      Master.Password = this.password;
    })
    for(let i of this.ids){
      var button = document.getElementById(i);
      if(button != null) button.style.border = "none";
    }
  }

}
