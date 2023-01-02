import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { MailComponent } from './mail/mail.component';
import { InboxComponent } from './mail/inbox/inbox.component';
import { TrashComponent } from './mail/trash/trash.component';
import { ComposeComponent } from './mail/compose/compose.component';
import { ContactsComponent } from './mail/contacts/contacts.component';
import { SentComponent } from './mail/sent/sent.component';
import { DraftComponent } from './mail/draft/draft.component';
import { FoldersComponent } from './mail/folders/folders.component';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    MailComponent,
    InboxComponent,
    TrashComponent,
    ComposeComponent,
    ContactsComponent,
    SentComponent,
    DraftComponent,
    FoldersComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
