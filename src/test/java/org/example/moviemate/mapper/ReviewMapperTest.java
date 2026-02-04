package org.example.moviemate.mapper;

import org.example.moviemate.dao.entity.ReviewEntity;
import org.example.moviemate.model.dto.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ReviewMapperTest {

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    void toReviewEntity_ShouldMapIdsToEntities() {
        ReviewDTO dto = new ReviewDTO();
        dto.setUserId(1L);
        dto.setMovieId(5L);
        dto.setComment("Əla film!");

        ReviewEntity entity = reviewMapper.toReviewEntity(dto);

        assertNotNull(entity.getUser());
        assertEquals(1L, entity.getUser().getId());
        assertNotNull(entity.getMovie());
        assertEquals(5L, entity.getMovie().getId());
    }
}