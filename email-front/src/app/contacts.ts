import { Master } from "./master/master";

export class Contact {
    private name: string;
    private addressList: Array<string>;
    private username: string;
    constructor(name:string,info:Array<string>){
        this.name = name;
        this.addressList = info;
        this.username = Master.Username;
    }
    getName(){
        return this.name;
    }
    getAddressList(){
        return this.addressList;
    }
    static createContactFromObject(obj: any){
        return new Contact(obj.name,obj.addressList);
    }
}
