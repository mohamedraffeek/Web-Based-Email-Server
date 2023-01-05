import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders } from '@angular/common/http'
import { Account } from '../account/account';
import { Observable } from 'rxjs';
import { Master } from '../master/master';
import { Email } from '../email/email';

const httpOptions: Object = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    Authorization: 'my-auth-token'
  }), responseType: 'text'
};

const httpOptions2: Object = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    Authorization: 'my-auth-token'
  })
};

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http: HttpClient) { }

  createAccount(account: Account): Observable<any>{
    return this.http.post<any>("http://localhost:8080/serve/signup", JSON.stringify(account), httpOptions);
  }

  login(account: Account): Observable<any>{
    return this.http.post<any>("http://localhost:8080/serve/login", JSON.stringify(account), httpOptions);
  }

  saveMaster(): Observable<any>{
    return this.http.post<any>("http://localhost:8080/serve/saveMaster", JSON.stringify(Master.Username), httpOptions);
  }

  getMaster(): Observable<any>{
    return this.http.get<any>("http://localhost:8080/serve/getMaster", httpOptions);
  }

  savePass(): Observable<any>{
    return this.http.post<any>("http://localhost:8080/serve/savePass", JSON.stringify(Master.Password), httpOptions);
  }

  getPass(): Observable<any>{
    return this.http.get<any>("http://localhost:8080/serve/getPass", httpOptions);
  }

  sendEmail(email: Email): Observable<any>{
    return this.http.post<any>("http://localhost:8080/serve/sendEmail", JSON.stringify(email), httpOptions);
  }

  draftEmail(email: Email): Observable<any>{
    return this.http.post<any>("http://localhost:8080/serve/draftEmail", JSON.stringify(email), httpOptions);
  }

  getEmails(username: string, folder: string): Observable<any>{
    return this.http.get<any>(`http://localhost:8080/serve/getEmails/${username}/${folder}`, httpOptions2);
  }

  readEmail(email: Email, folder: string): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/readEmail/${folder}`, JSON.stringify(email), httpOptions);
  }

  deleteEmail(index: number): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/deleteEmail/${Master.Username}/${index}`, JSON, httpOptions);
  }

  deleteEmailS(index: number): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/deleteEmailS/${Master.Username}/${index}`, JSON, httpOptions);
  }

  deleteEmailD(index: number): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/deleteEmailD/${Master.Username}/${index}`, JSON, httpOptions);
  }

  eraseEmailFromData(index: number): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/eraseEmailFromData/${Master.Username}/${index}`, JSON, httpOptions);
  }

  restoreEmail(index: number): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/restoreEmail/${Master.Username}/${index}`, JSON, httpOptions);
  }

  filterEmails(username: string, folder: string, filter: Array<string>, searchString: string): Observable<any>{
    return this.http.get<any>(`http://localhost:8080/serve/filterEmails/${username}/${folder}/${filter}/${searchString}`, httpOptions2);
  }

  sortEmails(username: string, folder: string): Observable<Array<Email>>{
    return this.http.get<Array<Email>>(`http://localhost:8080/serve/sortEmails/${username}/${folder}`, httpOptions2);
  }

  getFolders(username: string): Observable<any>{
    return this.http.get<any>(`http://localhost:8080/serve/getFolders/${username}`, httpOptions2);
  }

  addFolder(folder: string, username: string): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/addFolder/${folder}/${username}`, JSON, httpOptions);
  }

  deleteFolder(index: number, username: string): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/deleteFolder/${index}/${username}`, JSON, httpOptions);
  }

  renameFolder(index: number, newName: string, username: string): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/renameFolder/${index}/${newName}/${username}`, JSON, httpOptions);
  }

  addToFolder(index: number, folder: string): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/addToFolder/${Master.Username}/${index}/${folder}`, JSON, httpOptions);
  }

  addContact(name: string, emailAddress: string, username: string): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/addContact/${name}/${emailAddress}/${username}`, JSON, httpOptions);
  }

  deleteContact(index: number, username: string): Observable<any>{
    return this.http.post<any>(`http://localhost:8080/serve/deleteContact/${index}/${username}`, JSON, httpOptions);
  }

  getContacts(username: string): Observable<any>{
    return this.http.get<any>(`http://localhost:8080/serve/getContacts/${username}`, httpOptions2);
  }

  searchContacts(username: string, searchString: string): Observable<any>{
    return this.http.get(`http://localhost:8080/serve/searchContacts/${username}/${searchString}`, httpOptions2);
  }

  upload(formData: FormData): Observable<HttpEvent<string[]>>{
    return this.http.post<string[]>(`http://localhost:8080/serve/upload`, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }

  download(filename: string): Observable<HttpEvent<Blob>>{
    return this.http.get(`http://localhost:8080/serve/download/${filename}`, {
      reportProgress: true,
      observe: 'events',
      responseType: 'blob'
    });
  }

}
