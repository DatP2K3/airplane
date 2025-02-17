package com.airplane.schedule.repository;

import com.airplane.schedule.dto.request.FlightSearchRequest;
import com.airplane.schedule.dto.request.TicketSearchRequest;
import com.airplane.schedule.model.Flight;
import com.airplane.schedule.model.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightRepositoryCustomImpl implements FlightRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Flight> search(FlightSearchRequest flightSearchRequest) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select f from Flight f " + createWhereQuery(flightSearchRequest.getKeyword(), values) + createOrderQuery(flightSearchRequest.getSortBy());
        Query query = entityManager.createQuery(sql, Flight.class);
        values.forEach(query::setParameter);
        query.setFirstResult((flightSearchRequest.getPageIndex() - 1) * flightSearchRequest.getPageSize());
        query.setMaxResults(flightSearchRequest.getPageSize());
        return query.getResultList();
    }

    private String createWhereQuery(String keyword, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        if (!keyword.isBlank()) {
            sql.append(
                    "where lower(f.flightNumber) like :keyword");
            values.put("keyword", encodeKeyword(keyword));
        }
        return sql.toString();
    }

    public StringBuilder createOrderQuery(String sortBy) {
        StringBuilder hql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            hql.append(" order by f.").append(sortBy.replace(".", " "));
        }
        return hql;
    }

    public String encodeKeyword(String keyword) {
        if (keyword == null) {
            return "%";
        }
        return "%" + keyword.trim().toLowerCase() + "%";
    }

    @Override
    public Long count(FlightSearchRequest flightSearchRequest) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select count(f) from Flight f " + createWhereQuery(flightSearchRequest.getKeyword(), values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
