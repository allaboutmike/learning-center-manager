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
    private long parentId;

    @Column(nullable = false, name="name")
    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> child = new ArrayList<>();

    public Parent( String name ){

        this.name = name;
    }

    public Parent() {

    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
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

    @Override
    public String toString() {
        return "ParentEntity{" +
                "id=" + parentId +
                ", name='" + name + '\'' +
                ", children='" + child + '\'' +
                '}';
    }
}
