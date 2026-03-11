package com.bookingsystem.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryDataStore {
    private final ConcurrentHashMap<String, String> dataMap = new ConcurrentHashMap<>();
    
    public void put(String key, String value) {
        dataMap.put(key, value);
    }
    
    public String get(String key) {
        return dataMap.get(key);
    }
    
    public Map<String, String> getAll() {
        return new HashMap<>(dataMap);
    }
    
    public void remove(String key) {
        dataMap.remove(key);
    }
    
    public void clear() {
        dataMap.clear();
    }
}