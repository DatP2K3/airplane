package com.airplane.schedule.controller;

import com.airplane.schedule.dto.ApiResponse;
import com.airplane.schedule.dto.response.StatisticResponseDTO;
import com.airplane.schedule.service.Impl.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PreAuthorize("hasPermission('statistic', 'admin')")
    @GetMapping("/statistic")
    public ApiResponse<StatisticResponseDTO> getStatistic() {
        StatisticResponseDTO statisticResponseDTO = statisticService.getStatistic();
        return ApiResponse.<StatisticResponseDTO>builder()
                .data(statisticResponseDTO)
                .success(true)
                .code(200)
                .message("Get statistic successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
