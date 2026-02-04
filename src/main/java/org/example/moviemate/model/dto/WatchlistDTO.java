package org.example.moviemate.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class WatchlistDTO {

    private Long userId;
    private List<String> movies;

}

