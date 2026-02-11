package com.learningcenter.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.sql.Timestamp;

@Entity
@Table(name = "timeslot", schema = "session")
public class Timeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeslot_id")
    private long timeslot_id;

    @Column(nullable = false, name="time")
    private Timestamp time;

    public long getTimeslot_id() {
        return timeslot_id;
    }

    public void setTimeslot_id(long timeslot_id) {
        this.timeslot_id = timeslot_id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
