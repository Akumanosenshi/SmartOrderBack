package ynov.smartorder.api.metrics.services;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ynov.smartorder.api.metrics.utils.AtomicDouble;
import ynov.smartorder.api.persistence.repository.OrderRepository;
import ynov.smartorder.api.persistence.repository.ReservationRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class StatisticMetricsScheduler {

    private final OrderRepository orderRepository;
    private final ReservationRepository reservationRepository;
    private final MeterRegistry meterRegistry;

    // Valeurs stockées pour les métriques
    private final AtomicInteger totalOrders = new AtomicInteger(0);
    private final AtomicInteger totalReservations = new AtomicInteger(0);
    private final AtomicDouble totalRevenue = new AtomicDouble(0.0);
    private final AtomicDouble averageCart = new AtomicDouble(0.0);
    private final AtomicDouble averagePeople = new AtomicDouble(0.0);

    @PostConstruct
    public void registerGauges() {
        Gauge.builder("smartorder_total_orders", totalOrders::get).register(meterRegistry);
        Gauge.builder("smartorder_total_reservations", totalReservations::get).register(meterRegistry);
        Gauge.builder("smartorder_total_revenue", totalRevenue::get).register(meterRegistry);
        Gauge.builder("smartorder_average_cart", averageCart::get).register(meterRegistry);
        Gauge.builder("smartorder_avg_people_per_reservation", averagePeople::get).register(meterRegistry);
    }


    @Scheduled(fixedRate = 5000) // toutes les 5 secondes
    public void updateStatisticsMetrics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);


        totalOrders.set(orderRepository.getTotalOrders(start, now));
        totalReservations.set(reservationRepository.getTotalReservations(start, now));
        totalRevenue.set(orderRepository.getTotalRevenue(start, now));
        averageCart.set(orderRepository.getAverageCart(start, now));
        averagePeople.set((double) reservationRepository.getAveragePeoplePerReservation(start, now));

        System.out.println("[METRICS] Total orders: " + totalOrders.get());
        System.out.println("[METRICS] Total revenue: " + totalRevenue.get());
        System.out.println("[METRICS] Avg cart: " + averageCart.get());
        System.out.println("[METRICS] Reservations: " + totalReservations.get());
        System.out.println("[METRICS] Avg people/reservation: " + averagePeople.get());
    }

}
