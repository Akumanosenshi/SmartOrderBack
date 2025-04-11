package ynov.smartorder.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ynov.smartorder.api.persistence.repository.OrderRepository;
import ynov.smartorder.api.web.apis.StatisticsApi;
import ynov.smartorder.api.web.dtos.StatisticsDto;

import java.time.OffsetDateTime;

@RequiredArgsConstructor
@RestController
public class StatisticController implements StatisticsApi {
    @Override
    public ResponseEntity<StatisticsDto> statisticsGet(OffsetDateTime startDate, OffsetDateTime endDate) {
    return ResponseEntity.ok(null);
    }
}
