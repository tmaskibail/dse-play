package com.urthilak;


import com.urthilak.domain.Quote;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DseSparkTest {

    @Test
    public void shouldReturnRecord() {

//        SparkConf sparkConf = new SparkConf()
//                .setAppName("FORS")
//                .set("spark.cassandra.connection.host", "localhost");
//        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
//
//        JavaRDD<CassandraRow> cassandraRowsRDD =  javaFunctions(sparkContext).cassandraTable("play", "quote");

        SparkSession session = SparkSession
                .builder()
                .appName("FORS")
                .config("spark.master", "local")
                .config("spark.cassandra.connection.host", "localhost")
                .getOrCreate();

        session.sql("CREATE TEMPORARY TABLE quote_temp " +
                "    USING org.apache.spark.sql.cassandra " +
                "    OPTIONS ( table \"quote\", keyspace \"play\")");

        Dataset<Quote> results = session.sql("SELECT id, state, tx_date, application_id, entity_id, message FROM quote_temp")
                .map((MapFunction<Row, Quote>) row -> {
                    Quote quote = new Quote();
                    quote.setId((row.getString(0)));
                    quote.setState(row.getString(1));
                    quote.setTxDate(row.getTimestamp(2));
                    quote.setApplicationId(row.getString(3));
                    quote.setEntityId(row.getString(4));
                    quote.setMessage(row.getString(5));
                    return quote;
                }, Encoders.bean(Quote.class));
        results.show(10);

//
//        Dataset<String> namesDS = results.map((MapFunction<Row, String>) row -> "id: " + row.getString(0), Encoders.STRING());
//
//        namesDS.show();

        session.close();
    }


}
