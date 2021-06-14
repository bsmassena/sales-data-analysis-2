package com.bsmlima.salesdataanalysis.config;

import com.bsmlima.salesdataanalysis.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ComponentScan({"com.bsmlima.salesdataanalysis"})
public class AppConfig {

    @Autowired
    private SalesmanDataParser salesmanDataParser;

    @Autowired
    private CustomerDataParser customerDataParser;

    @Autowired
    private SaleDataParser saleDataParser;

    @Bean
    public HashMap<String, DataParser> parsersHashMap() {
        HashMap<String, DataParser> parsersHashMap = new HashMap<>();

        parsersHashMap.put(salesmanDataParser.getCode(), salesmanDataParser);
        parsersHashMap.put(customerDataParser.getCode(), customerDataParser);
        parsersHashMap.put(saleDataParser.getCode(), saleDataParser);

        return parsersHashMap;
    }
}
