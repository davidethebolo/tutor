package com.bolowrc.tutoringmanager.model;


import java.text.MessageFormat;

public class Student {

    private long id = 0L;
    private String firstName;
    private String lastName;
    private String school;

    public Student(long id, String firstName, String lastName, String school) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.school = school;
    }

    public long getId() {
        return id;
    }

    public String getTextId() {
        return Long.toString(id);
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSchool() {
        return school;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSummary() {
        return MessageFormat.format("{0} {1}, {2}", firstName, lastName, school);
    }
}
