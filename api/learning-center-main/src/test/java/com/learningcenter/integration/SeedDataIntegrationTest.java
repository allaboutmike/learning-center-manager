package com.learningcenter.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SeedDataIntegrationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Test
    void subject_math_exists() {
        Boolean exists = jdbcTemplate.queryForObject("""
                    SELECT EXISTS (
                        SELECT 1
                        FROM tutor_profile.subject
                        WHERE subject_name = 'Math'
                    )
                """, Boolean.class);

        assertEquals(true, exists);
    }

}

