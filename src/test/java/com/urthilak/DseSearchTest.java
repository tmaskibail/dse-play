package com.urthilak;

import com.urthilak.domain.Quote;
import com.urthilak.service.DseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.urthilak.util.QuoteUtil.createQuote;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DseSearchTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DseSearchTest.class);

    @Autowired
    private DseService dseService;


    @Test
    public void shouldQueryForRecord() {
        Quote quote = createQuote(1).get(0);
        dseService.save(quote);

        List<Quote> quotes = dseService.searchForRecord("SELECT * FROM play.quote WHERE id="+quote.getId());
        assertNotNull(quotes);
        assertTrue(quotes.size() == 1);
    }

    @Test
    public void shouldRetrieveRecordInstantly() throws InterruptedException {
        Quote quote = createQuote(1).get(0);
        dseService.save(quote);

        Thread.sleep(10000);

        String cql = "SELECT * FROM play.quote WHERE solr_query='{\"q\":\"state:"
                + quote.getState() + "\", \"fq\":\"application_id:"
                + quote.getApplicationId() + "\", \"fq\":\"entity_id:"
                + quote.getEntityId() + "\"}'";
        List<Quote> quotes = dseService.searchForRecord(cql);

        LOGGER.info("Size of fetch [{}]", quotes.size());
        assertNotNull(quotes);
        assertTrue(quotes.size() > 0);
        assertTrue(quotes.size() == 1);
    }

}
