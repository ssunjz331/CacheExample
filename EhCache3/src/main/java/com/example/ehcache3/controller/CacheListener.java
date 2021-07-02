package com.example.ehcache3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;

public class CacheListener implements CacheEntryCreatedListener<Integer, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(CacheEventLogger.class);

    @Override
    public void onCreated(final Iterable<CacheEntryEvent<? extends Integer, ? extends Integer>> cacheEntryEvents) {
        for (CacheEntryEvent<? extends Integer, ? extends Integer> entryEvent : cacheEntryEvents) {
            logger.info("Cached key: {}, with value: {}", entryEvent.getKey(), entryEvent.getValue());
        }
    }
}
