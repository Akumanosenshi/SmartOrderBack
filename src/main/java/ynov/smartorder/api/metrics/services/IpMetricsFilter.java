package ynov.smartorder.api.metrics.services;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class IpMetricsFilter extends OncePerRequestFilter {

    private final MeterRegistry registry;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {

        String ip = request.getRemoteAddr();
        registry.counter("smartorder_requests_by_ip", "ip", ip).increment();


        filterChain.doFilter(request, response);
    }
}
