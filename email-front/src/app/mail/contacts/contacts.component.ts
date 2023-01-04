import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from 'src/app/contact';
import { Email } from 'src/app/email/email';
import { Folder } from 'src/app/folder';
import { Master } from 'src/app/master/master';
import { BackendService } from 'src/app/Service/backend.service';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit{
  ids: string[] = ["1", "2", "3", "4", "5", "6", "7"];

  name!: string;
  emailAddress!: string;
  searchString: string = '';
  allContacts: Contact[] = [];
  contacts: Contact[] = [];
  pageNumber: number = 1;
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

  loadContacts(){
    this.service.getContacts(Master.Username).subscribe((contactList: Array<Contact>) => {
      this.allContacts = [];
      if(contactList != null){
        contactList.forEach(
          (contact: Contact) => {
            this.allContacts.push(Contact.createContactFromBack(contact));
          }
        )
        this.contacts = this.allContacts.slice((this.pageNumber - 1) * 14, this.allContacts.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allContacts.length);
      }
    })
  }

  deleteContact(){
    for(let i = 0; i < 14; i++){
      if(this.checkboxes[i].isChecked){
        this.checkboxes[i].isChecked = false;
        console.log(i);
        this.service.deleteContact(i, Master.Username).subscribe(() =>{
          this.contacts = [];
          this.loadContacts();
        }
        );
      }
    }
  }

  addContact(){
    this.service.addContact(this.name, this.emailAddress, Master.Username).subscribe((response) =>{
      console.log(response);
      this.contacts = [];
      this.loadContacts();
    }
    );
  }

  search(){
    if(this.searchString.length==0)
      this.loadContacts();
    this.service.searchContacts(Master.Username, this.searchString).subscribe((contactList: Array<Contact>) => {
        this.allContacts = [];
        if(contactList.length == 0){
          this.contacts = [];
          return;
        }
        if(contactList != null){
          contactList.forEach(
            (contact: Contact) => {
              this.allContacts.push(Contact.createContactFromBack(contact));
            }
          )
          this.contacts = this.allContacts.slice((this.pageNumber - 1) * 14, this.allContacts.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allContacts.length);
        }
      }
    )
  }

  refresh(){
    this.loadContacts();
  }

  next(){
    this.pageNumber += 1;
    this.contacts = this.allContacts.slice((this.pageNumber - 1) * 14, this.allContacts.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allContacts.length);
  }

  prev(){
    this.pageNumber -= 1;
    this.contacts = this.allContacts.slice((this.pageNumber - 1) * 14, this.allContacts.length - (this.pageNumber - 1) * 14 > 14 ? this.pageNumber * 14 : this.allContacts.length);
  }

  ngOnInit(): void {
    for(let i of this.ids){
      var button = document.getElementById(i);
      if(button != null) button.style.border = "none";
    }
    var button = document.getElementById("7");
    if(button != null) button.style.border = "2px solid #82808054";
    this.loadContacts();
  }

}
