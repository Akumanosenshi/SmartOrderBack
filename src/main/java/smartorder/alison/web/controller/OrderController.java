package smartorder.alison.web.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import smartorder.alison.web.apis.OrdersApi;
import smartorder.alison.web.dtos.OrderDto;
import smartorder.alison.persistence.repository.OrderRepository;
import smartorder.alison.web.mappers.OrderDtoMapper;

import java.util.List;

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
                        .toList());
    }

    @Override
    public ResponseEntity<Void> orderPost(OrderDto orderDto) {
        orderRepository.saveOrder(orderEtyMapper.toEntity(orderDto));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<OrderDto>> orderUserGet(String userEmail) {

        return null;
    }
}
