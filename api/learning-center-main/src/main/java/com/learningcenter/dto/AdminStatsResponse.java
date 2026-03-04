package com.learningcenter.dto;

public class AdminStatsResponse {

    private final long parents;
    private final long students;
    private final long tutors;
    private final long creditsPurchased;

    public AdminStatsResponse(long parents, long students, long tutors, long creditsPurchased) {
        this.parents = parents;
        this.students = students;
        this.tutors = tutors;
        this.creditsPurchased = creditsPurchased;
    }

    public long getParents() { return parents; }
    public long getStudents() { return students; }
    public long getTutors() { return tutors; }
    public long getCreditsPurchased() { return creditsPurchased; }
}
