package com.airplane.schedule.repository;

import com.airplane.schedule.dto.request.TicketSearchRequest;
import com.airplane.schedule.model.Ticket;
import com.airplane.schedule.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketRepositoryCustomImpl implements TicketRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ticket> search(TicketSearchRequest ticketSearchRequest) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select t from Ticket t " + createWhereQuery(ticketSearchRequest.getKeyword(), values) + createOrderQuery(ticketSearchRequest.getSortBy());
        Query query = entityManager.createQuery(sql, Ticket.class);
        values.forEach(query::setParameter);
        query.setFirstResult((ticketSearchRequest.getPageIndex() - 1) * ticketSearchRequest.getPageSize());
        query.setMaxResults(ticketSearchRequest.getPageSize());
        return query.getResultList();
    }

    private String createWhereQuery(String keyword, Map<String, Object> values) {
        StringBuilder sql = new StringBuilder();
        if (!keyword.isBlank()) {
            sql.append(
                    "where ( lower(t.ticketNumber) like :keyword"
                            + " or lower(t.ticketNumber) like :keyword)");
            values.put("keyword", encodeKeyword(keyword));
        }
        return sql.toString();
    }

    public StringBuilder createOrderQuery(String sortBy) {
        StringBuilder hql = new StringBuilder(" ");
        if (StringUtils.hasLength(sortBy)) {
            hql.append(" order by t.").append(sortBy.replace(".", " "));
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
    public Long count(TicketSearchRequest ticketSearchRequest) {
        Map<String, Object> values = new HashMap<>();
        String sql = "select count(u) from User u " + createWhereQuery(ticketSearchRequest.getKeyword(), values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }
}
