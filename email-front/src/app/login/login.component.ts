import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Account } from '../account/account';
import { Master } from '../master/master';
import { BackendService } from '../Service/backend.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  account!: Account;

  constructor(private router: Router, private service: BackendService){
  }

  login(username: string, password: string){
    this.account = new Account(username, password);
    this.service.login(this.account).subscribe(response => {
      if(response == "error"){
        alert("Your username or password may be incorrect, please recheck them and try again");
      }else{
        console.log("logged in successfully");
        Master.Username = username;
        Master.Password = password;
        this.service.saveMaster().subscribe();
        this.service.savePass().subscribe();
        this.router.navigate(["/mail/inbox"]);
      }
    })
  }
}
