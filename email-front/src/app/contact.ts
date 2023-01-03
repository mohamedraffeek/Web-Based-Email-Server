export class Contact {
    private name: string;
    private emailAddress: string;
    constructor(name: string, emailAddress: string){
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public static createContactFromBack(obj: any){
        return new Contact(obj.name, obj.emailAddress);
    }

    getName(){return this.name;}
    getEmailAddress(){return this.emailAddress;}
}
