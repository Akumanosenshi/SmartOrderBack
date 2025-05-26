package ynov.smartorder.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ynov.smartorder.api.persistence.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import ynov.smartorder.api.web.apis.OrdersApi;
import ynov.smartorder.api.web.dtos.OrderDto;
import ynov.smartorder.api.web.dtos.StateDto;
import ynov.smartorder.api.web.mappers.OrderDtoMapper;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrdersApi {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderEtyMapper;

    @Override
    public ResponseEntity<List<OrderDto>> orderAllGet() {
        return orderRepository.getAllOrder().isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(orderRepository.getAllOrder().stream()
                        .map(orderEtyMapper::toDto)
                        .collect(Collectors.toList()));

    }


    @Override
    public ResponseEntity<Void> orderPost(OrderDto orderDto) {
        orderDto.setState(StateDto.valueOf("PENDING"));
        orderRepository.saveOrder(orderEtyMapper.toEntity(orderDto));
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<List<OrderDto>> orderUserGet(UUID Id) {
        return orderRepository.getOrders(Id).isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(orderRepository.getOrders(Id).stream()
                        .map(orderEtyMapper::toDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> toCancel(UUID id) {
        orderRepository.toCancel(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> toCompleted(UUID id) {
        orderRepository.toCompleted(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> toInProgress(UUID id) {
        orderRepository.toInProgress(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> toReadyToPickUp(UUID id) {
        orderRepository.toReadyToPickUp(id);
        return ResponseEntity.ok().build();
    }


}
