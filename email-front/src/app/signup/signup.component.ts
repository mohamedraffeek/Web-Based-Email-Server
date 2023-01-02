import { Component } from '@angular/core';
import { Master } from '../master/master';
import { Account } from '../account/account';
import { Router } from '@angular/router';
import { BackendService } from '../Service/backend.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  account!: Account;

  constructor(private router: Router, private service: BackendService){
  }

  register(username: string, password: string){
    this.account = new Account(username, password);
    this.service.createAccount(this.account).subscribe(response => {
      if(response == "error"){
        alert("Username already exists.");
      }else{
        console.log("User added successfully");
        Master.Username = username;
        Master.Password = password;
        this.service.saveMaster().subscribe();
        this.service.savePass().subscribe();
        this.router.navigate(["/mail/inbox"]);
      }
    })
  }

}
