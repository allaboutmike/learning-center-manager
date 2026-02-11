package com.learningcenter.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "child", schema = "parent_account")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private long child_id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "grade_level")
    private int grade_level;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent_id;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private List<Session> session = new ArrayList<>();

    public long getChild_id() {
        return child_id;
    }

    public void setChild_id(long child_id) {
        this.child_id = child_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade_level() {
        return grade_level;
    }

    public void setGrade_level(int grade_level) {
        this.grade_level = grade_level;
    }

    public Parent getParent_id() {
        return parent_id;
    }

    public void setParent_id(Parent parent_id) {
        this.parent_id = parent_id;
    }
}

