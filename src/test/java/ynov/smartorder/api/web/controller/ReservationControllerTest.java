package ynov.smartorder.api.web.controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.domain.models.Meal;
import ynov.smartorder.api.domain.models.Order;
import ynov.smartorder.api.domain.models.Reservation;
import ynov.smartorder.api.domain.models.User;
import ynov.smartorder.api.domain.ports.UserPort;
import ynov.smartorder.api.persistence.repository.MealRepository;
import ynov.smartorder.api.persistence.repository.OrderRepository;
import ynov.smartorder.api.persistence.repository.ReservationRepository;
import ynov.smartorder.api.web.dtos.*;
import ynov.smartorder.api.web.mappers.MealDtoMapper;
import ynov.smartorder.api.web.mappers.OrderDtoMapper;
import ynov.smartorder.api.web.mappers.ReservationDtoMapper;
import ynov.smartorder.api.web.mappers.UserDtoMapper;
import ynov.smartorder.api.web.services.BruteForceProtectionService;
import ynov.smartorder.api.web.services.JwtService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest implements WithRandom {

    @Mock private ReservationRepository reservationRepository;
    @Mock private ReservationDtoMapper reservationMapper;
    @InjectMocks private ReservationController reservationController;

    @Test
    void shouldDeleteReservation() {
        // given
        UUID id = randomUUID();

        // when
        ResponseEntity<Void> response = reservationController.deleteReservation(id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reservationRepository).deleteReservation(id);
    }

    @Test
    void shouldReturnAllReservations() {
        // given
        List<Reservation> reservations = List.of(random(Reservation.class));
        List<ReservationDto> reservationDtos = List.of(random(ReservationDto.class));

        when(reservationRepository.FindAllReservation()).thenReturn(reservations);
        when(reservationMapper.toDto(any(Reservation.class))).thenReturn(reservationDtos.get(0));

        // when
        ResponseEntity<List<ReservationDto>> response = reservationController.reservationsAllGet();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservationDtos, response.getBody());
    }

    @Test
    void shouldSaveReservation() {
        // given
        ReservationDto dto = random(ReservationDto.class);
        Reservation entity = random(Reservation.class);
        when(reservationMapper.toEntity(dto)).thenReturn(entity);

        // when
        ResponseEntity<Void> response = reservationController.reservationsPost(dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reservationRepository).saveReservation(entity);
    }

    @Test
    void shouldReturnReservationsByUserId() {
        // given
        UUID id = randomUUID();
        List<Reservation> reservations = List.of(random(Reservation.class));
        List<ReservationDto> reservationDtos = List.of(random(ReservationDto.class));

        when(reservationRepository.FindReservation(id)).thenReturn(reservations);
        when(reservationMapper.toDto(any(Reservation.class))).thenReturn(reservationDtos.get(0));

        // when
        ResponseEntity<List<ReservationDto>> response = reservationController.reservationsUserGet(id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservationDtos.size(), response.getBody().size());
        assertEquals(reservationDtos.get(0), response.getBody().get(0));
    }
}

