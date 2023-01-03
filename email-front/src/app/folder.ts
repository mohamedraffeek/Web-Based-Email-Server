import { Email } from "./email/email";

export class Folder {
    private name: string;
    private content: Email[];

    constructor(name: string, content: Email[]){
        this.name = name;
        this.content = [];
    }

    public static createFolderFromBack(obj: any){
        return new Folder(obj.name, obj.content);
    }
    getName(){
        return this.name;
    }
}
