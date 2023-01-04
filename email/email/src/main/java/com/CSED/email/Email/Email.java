package com.CSED.email.Email;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Email implements Comparable<Email> {
    private String from;
    private Queue<String> to = new LinkedList<>();
    private String subject;
    private String body;
    private String[] attachment;
    private Integer priority;
    private boolean read;
    private Date date;

    public Email(EmailBuilder builder){
        this.from = builder.getSender();
        this.to.addAll(builder.getReceiver());
        this.subject = builder.getSubject();
        this.body = builder.getBody();
        this.attachment = builder.getAttachment();
        this.priority = Integer.valueOf(builder.getPriority());
        this.read = builder.isRead();
        this.date = builder.getDate();
    }
    public Email(){

    }
    @Override
    public int compareTo(Email email){
        return this.getPriority().compareTo(email.getPriority());
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String sender) {
        this.from = sender;
    }

    public Queue<String> getTo() {
        return to;
    }

    public void setTo(Queue<String> receiver) {
        this.to = receiver;
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

    public String[] getAttachment() {
        return attachment;
    }

    public void setAttachment(String[] attachment) {
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