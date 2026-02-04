package org.example.moviemate.service.intf;


import org.example.moviemate.model.dto.SystemStatsDTO;
import org.example.moviemate.model.dto.UserStatsDTO;

public interface StatsService {

    UserStatsDTO getUserStats(Long userId);

    SystemStatsDTO getSystemStats();

}
