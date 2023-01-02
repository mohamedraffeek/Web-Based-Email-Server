package com.CSED.email.Email;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class EmailBuilder{
    private String sender;
    private Queue<String> receiver=new LinkedList<>();
    private String subject;
    private String body;
    private Object attachment;
    private Integer priority;
    private boolean read;
    private Date date;

    public EmailBuilder(String sender,Queue<String> receiver){
        this.sender=sender;
        this.receiver.addAll(receiver);
    }
    public EmailBuilder subject(String subject){
        this.subject=subject;
        return this;
    }
    public EmailBuilder body(String body){
        this.body=body;
        return this;
    }
    public EmailBuilder attachment(Object attachment){
        this.attachment=attachment;
        return this;
    }
    public EmailBuilder priority(Integer priority){
        this.priority=Integer.valueOf(priority);
        return this;
    }
    public EmailBuilder read(boolean read){
        this.read = read;
        return this;
    }

    public EmailBuilder date(Date date){
        this.date = date;
        return this;
    }

    public Email build(){
        return new Email(this);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Queue<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(Queue<String> receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}