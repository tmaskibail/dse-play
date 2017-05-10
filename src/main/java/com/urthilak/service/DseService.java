package com.urthilak.service;

import com.urthilak.connector.CassandraConnector;
import com.urthilak.domain.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class DseService {

    @Autowired
    private CassandraConnector cassandraConnector;

    public void save(Quote quote) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO play.quote (id, tx_date, state, application_id, entity_id, message)")
                .append(" VALUES (")
                .append(quote.getId())
                .append(", '")
                .append(getFormattedDate(quote))
                .append("', '")
                .append(quote.getState())
                .append("', '")
                .append(quote.getApplicationId())
                .append("', '")
                .append(quote.getEntityId())
                .append("', '")
                .append(quote.getMessage())
                .append("')");

        cassandraConnector.save(builder.toString());
    }

    public List<Quote> searchForRecord(String cql) {
        return cassandraConnector.query(cql);
    }

    private String getFormattedDate(Quote quote) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return format.format(quote.getTxDate());
    }
}
