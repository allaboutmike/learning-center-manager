package com.learningcenter.entities;


import jakarta.persistence.*;


@Entity
@Table(name = "child")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private long child_id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "grade_level")
    private int grade_level;

    @Column(nullable = false, name = "parent_id")
    private long parent_id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;


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

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}

