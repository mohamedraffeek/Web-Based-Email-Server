import { Component } from '@angular/core';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.css']
})
export class MailComponent {

  ids: string[] = ["1", "2", "3", "4", "5", "6", "7"];

  border(id: string){
    for(let i of this.ids){
      var button = document.getElementById(i);
      if(button != null) button.style.border = "none";
    }
    var bu = document.getElementById(id);
    if(bu != null) bu.style.border = "2px solid #82808054";
  }

}
