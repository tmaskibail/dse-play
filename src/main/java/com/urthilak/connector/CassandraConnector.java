package com.urthilak.connector;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.urthilak.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class CassandraConnector {
    private static Logger LOGGER = LoggerFactory.getLogger(CassandraConnector.class);

    @Value("${cassandra.contact-points}")
    private String contactPoints;

    @Value("${cassandra-keyspace.name}")
    private String keyspaceName;

    @Value("${cassandra.username}")
    private String userName;

    @Value("${cassandra.password}")
    private String password;

    private Session session;

    @PostConstruct
    public void init() {
        if (session == null) {
            Cluster cluster = Cluster.builder().addContactPoint(contactPoints).withCredentials(userName, password).build();
            session = cluster.connect(keyspaceName);
            LOGGER.info("Connected to Cassandra keyspace {} on instance {} with userName {}", keyspaceName, contactPoints, userName);
        }
    }

    public List<Quote> query(String cql) {
        LOGGER.info("Executing cql [{}]", cql);
        ResultSet resultSet = session.execute(cql);

        List<Quote> quotes = new ArrayList<>();
        for (Row row : resultSet) {
            Quote quote = new Quote();
            quote.setId(row.getString("id"));
            quote.setState(row.getString("state"));
            quote.setApplicationId(row.getString("application_id"));
            quote.setEntityId(row.getString("entity_id"));
            quote.setMessage(row.getString("message"));
            quote.setTxDate(row.getTimestamp("tx_date"));
            quotes.add(quote);
        }

        return quotes;
    }

    public void save(String cql) {
        LOGGER.info("Executing [{}]", cql);
        session.execute(cql);
    }

    @PreDestroy
    public void close() {
        if (session != null) {
            session.close();
        }
    }


}
