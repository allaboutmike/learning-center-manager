package com.learningcenter.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AdminStatsResponseTest {

    @Test
    void constructor_setsAllFieldsCorrectly() {
        AdminStatsResponse response = new AdminStatsResponse(5L, 10L, 3L, 200L);

        assertEquals(5L, response.getParents());
        assertEquals(10L, response.getStudents());
        assertEquals(3L, response.getTutors());
        assertEquals(200L, response.getCreditsPurchased());
    }

    @Test
    void getParents_returnsCorrectValue() {
        AdminStatsResponse response = new AdminStatsResponse(7L, 0L, 0L, 0L);
        assertEquals(7L, response.getParents());
    }

    @Test
    void getStudents_returnsCorrectValue() {
        AdminStatsResponse response = new AdminStatsResponse(0L, 15L, 0L, 0L);
        assertEquals(15L, response.getStudents());
    }

    @Test
    void getTutors_returnsCorrectValue() {
        AdminStatsResponse response = new AdminStatsResponse(0L, 0L, 4L, 0L);
        assertEquals(4L, response.getTutors());
    }

    @Test
    void getCreditsPurchased_returnsCorrectValue() {
        AdminStatsResponse response = new AdminStatsResponse(0L, 0L, 0L, 500L);
        assertEquals(500L, response.getCreditsPurchased());
    }

    @Test
    void constructor_withZeroValues_returnsZeros() {
        AdminStatsResponse response = new AdminStatsResponse(0L, 0L, 0L, 0L);

        assertEquals(0L, response.getParents());
        assertEquals(0L, response.getStudents());
        assertEquals(0L, response.getTutors());
        assertEquals(0L, response.getCreditsPurchased());
    }
}
