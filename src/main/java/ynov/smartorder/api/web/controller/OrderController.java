package ynov.smartorder.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ynov.smartorder.api.persistence.repository.OrderRepository;
import smartorder.alison_api.web.apis.OrdersApi;
import java.util.List;
import java.util.UUID;

import smartorder.alison_api.web.dtos.OrderDto;
import ynov.smartorder.api.web.mappers.OrderDtoMapper;

@RequiredArgsConstructor
@RestController
public class OrderController implements OrdersApi {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderEtyMapper;

    @Override
    public ResponseEntity<Void> deleteOrder(UUID id) {
        orderRepository.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<OrderDto>> orderAllGet() {
        return orderRepository.getAllOrder().isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(orderRepository.getAllOrder().stream()
                        .map(orderEtyMapper::toDto)
                        .toList());

    }

    @Override
    public ResponseEntity<List<OrderDto>> orderCurrentGet() {
        return orderRepository.getCurrentOrders().isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(orderRepository.getCurrentOrders().stream()
                        .map(orderEtyMapper::toDto)
                        .toList());
    }

    @Override
    public ResponseEntity<Void> orderPost(OrderDto orderDto) {
        orderRepository.saveOrder(orderEtyMapper.toEntity(orderDto));
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<List<OrderDto>> orderUserGet(UUID Id) {
        return orderRepository.getOrders(Id).isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(orderRepository.getOrders(Id).stream()
                        .map(orderEtyMapper::toDto)
                        .toList());
    }
}
