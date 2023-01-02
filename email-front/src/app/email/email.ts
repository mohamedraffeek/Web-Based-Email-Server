export class Email {
    private from: string;
    private to: Array<string>;
    private subject: string;
    private body: string;
    private date: Date;
    private read: boolean;
    private priority: number;
    private attachment: FormData;

    constructor(from: string, to: Array<string>, subject: string, body: string, read: boolean, priority: number, attachment: FormData){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.read = read;
        this.priority = priority;
        this.attachment = attachment;
        this.date = new Date;
    }

    public static createEmailFromBack(obj: any){
        return new Email(obj.from, obj.to, obj.subject, obj.body, obj.read, obj.priority, obj.attachment);
    }

    getFrom(){
        return this.from;
    }
    getTo(){
        return this.to;
    }
    getSubject(){
        return this.subject;
    }
    getBody(){
        return this.body;
    }
    getDate(){
        return this.date.getDate() + '/' + (this.date.getMonth() + 1 ) + '/' + this.date.getFullYear();
    }
    getPriority(){
        return this.priority;
    }
    getRead(){
        return this.read;
    }
    getAttachment(){
        return this.attachment;
    }
    toggleRead(){
        this.read = !this.read;
    }
}
