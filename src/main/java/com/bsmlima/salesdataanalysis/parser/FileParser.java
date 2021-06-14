package com.bsmlima.salesdataanalysis.parser;

import com.bsmlima.salesdataanalysis.dto.SalesData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileParser {

    private final HashMap<String, DataParser> parsersHashMap;
    private final String dataSeparator;

    public FileParser(HashMap<String, DataParser> parsersHashMap, @Value("${parser.separator.data}") String dataSeparator) {
        this.parsersHashMap = parsersHashMap;
        this.dataSeparator = dataSeparator;
    }

    public SalesData parse(Stream<String> fileLines) {
        SalesData salesData = new SalesData();

        fileLines.forEach(line -> {
            List<String> data = Arrays.asList(line.split(dataSeparator));
            String dataType = data.get(0);
            parsersHashMap.get(dataType).parse(data, salesData);
        });

        return salesData;
    }
}
