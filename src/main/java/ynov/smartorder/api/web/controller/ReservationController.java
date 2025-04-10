package ynov.smartorder.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ynov.smartorder.api.web.apis.ReservationsApi;
import ynov.smartorder.api.domain.ports.ReservationPort;
import ynov.smartorder.api.persistence.repository.MealRepository;
import ynov.smartorder.api.persistence.repository.ReservationRepository;
import ynov.smartorder.api.web.dtos.ReservationDto;
import ynov.smartorder.api.web.mappers.MealDtoMapper;
import ynov.smartorder.api.web.mappers.ReservationDtoMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ReservationController implements ReservationsApi {

    private final ReservationRepository reservationRepository;
    private final ReservationDtoMapper reservationMapper;

    @Override
    public ResponseEntity<Void> deleteReservation(UUID id) {
        reservationRepository.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ReservationDto>> reservationsAllGet() {
        return ResponseEntity.ok(reservationRepository.FindAllReservation().stream().map(reservationMapper::toDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> reservationsPost(ReservationDto reservationDto) {
        reservationRepository.saveReservation(reservationMapper.toEntity(reservationDto));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ReservationDto>> reservationsUserGet(UUID id) {
        reservationRepository.FindReservation(id);
        return ResponseEntity.ok(reservationRepository.FindReservation(id).stream().map(reservationMapper::toDto).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> updateReservation(ReservationDto reservationDto) {
        reservationRepository.updateReservation(reservationMapper.toEntity(reservationDto));
        return ResponseEntity.ok().build();
    }
}
