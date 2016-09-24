package com.drpweb.user;

import com.drpweb.role.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Asus on 7/8/2559.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private double weight;
    private double height;
    private String gender;
    private int duration;
    private String password;
    private Date dob;//    private double bmi;
    private Long diseaseId;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

//    @OneToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="disease_id")
//    private Disease disease;


//
//    @ManyToMany(fetch= FetchType.LAZY)
//    private Set<Disease> diseases = new HashSet<>();




    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }



    public User() {
    }

    public User(String username, String name, String email, String password,  Set<Role> roles) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }







/*    public CalInfo getCalInfo() {
        return calInfo;
    }

    public void setCalInfo(CalInfo calInfo) {
        this.calInfo = calInfo;
    }*/

/*    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }*/
}
