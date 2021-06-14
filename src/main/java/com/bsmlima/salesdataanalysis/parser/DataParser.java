package com.bsmlima.salesdataanalysis.parser;

import com.bsmlima.salesdataanalysis.dto.SalesData;

import java.util.List;

public interface DataParser {
    void parse(List<String> data, SalesData salesData);
    String getCode();
}
