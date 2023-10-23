package com.example.mygymmanager.models;

public class Member {
    private String id, name, mobile, email, gender, parent1, parent2, sin, age, gymTime, monthlyFee;

    public Member(String id, String name, String mobile, String email, String gender, String parent1, String parent2, String gymTime, String sin, String age, String monthlyFee) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.gender = gender;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.sin = sin;
        this.age = age;
        this.gymTime = gymTime;
        this.monthlyFee = monthlyFee;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getParent1() {
        return parent1;
    }

    public String getParent2() {
        return parent2;
    }

    public String getSin() {
        return sin;
    }

    public String getAge() {
        return age;
    }

    public String getGymTime() {
        return gymTime;
    }

    public String getMonthlyFee() {
        return monthlyFee;
    }
}