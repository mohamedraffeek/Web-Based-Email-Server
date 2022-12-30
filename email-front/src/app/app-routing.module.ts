import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ComposeComponent } from './mail/compose/compose.component';
import { ContactsComponent } from './mail/contacts/contacts.component';
import { DraftComponent } from './mail/draft/draft.component';
import { FoldersComponent } from './mail/folders/folders.component';
import { InboxComponent } from './mail/inbox/inbox.component';
import { MailComponent } from './mail/mail.component';
import { SentComponent } from './mail/sent/sent.component';
import { TrashComponent } from './mail/trash/trash.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
  {path: "signup", component: SignupComponent},
  {path: "login", component: LoginComponent},
  {path: "mail", component: MailComponent,
  children:[
    {path: "inbox", component: InboxComponent},
    {path: "compose", component: ComposeComponent},
    {path: "trash", component: TrashComponent},
    {path: "contacts", component: ContactsComponent},
    {path: "sent", component: SentComponent},
    {path: "draft", component: DraftComponent},
    {path: "folders", component: FoldersComponent},
  ]},
  {path: "", redirectTo: "login", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
