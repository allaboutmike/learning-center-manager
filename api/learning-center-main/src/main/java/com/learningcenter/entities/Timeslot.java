package com.learningcenter.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "time_slot", schema = "session")
public class Timeslot {

    public Timeslot(Timestamp time) {
        this.time = time;
    }

    public Timeslot() {
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
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
