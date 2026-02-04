package org.example.moviemate.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.moviemate.model.enums.Genre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate releaseDate;

    @Column(name = "poster_url")
    private String posterUrl;

    private String description;

    private double averageRating;

    private String director;

    private String actors;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<ReviewEntity> reviews;

    @ManyToMany(mappedBy = "movies")
    @JsonIgnore
    private List<WatchlistEntity> watchlists;

    @ManyToMany(mappedBy = "movies")
    @JsonIgnore
    private List<DiaryEntity> diaries;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}