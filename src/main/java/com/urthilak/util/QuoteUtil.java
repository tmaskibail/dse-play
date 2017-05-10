package com.urthilak.util;


import com.datastax.driver.core.utils.UUIDs;
import com.urthilak.domain.Quote;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class QuoteUtil {

    public static List<Quote> createQuote(long size) {
        return Stream.generate(() ->
                new Quote(UUIDs.timeBased().toString(),
                        RandomGenerator.getRandomState(),
                        RandomGenerator.getRandomStringCaps(6),
                        RandomGenerator.getRandomString(4),
                        new Date(),
                        RandomGenerator.getRandomString(20)))
                .limit(size)
                .collect(toList());
    }
}
