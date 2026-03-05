package com.learningcenter.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parent", schema = "parent_account")
public class Parent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name="parent_id")
    private Long parentId;

    @Column(nullable = false, name="name")
    private String name;

    @Column(nullable=false, name="credits")
    private Integer credits;

    @Column(nullable=false, name="email", unique=true)
    private String email;

    @Column(name="phone")
    private String phone;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> child = new ArrayList<>();

    public Parent() {

    }

    public Parent(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.credits = 0;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChild() {
        return child;
    }

    public void setChild(List<Child> child) {
        this.child = child;
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ParentEntity{" +
                "id=" + parentId +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", email='" + email + '\'' +
                ", children='" + child + '\'' +
                '}';
    }
}
