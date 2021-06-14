package com.bsmlima.salesdataanalysis.parser;

import com.bsmlima.salesdataanalysis.dto.SalesData;
import com.bsmlima.salesdataanalysis.dto.Salesman;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesmanDataParser implements DataParser {

    private static final int CPF_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int SALARY_INDEX = 3;

    @Value("${parser.code.salesman}")
    private String code;

    @Override
    public void parse(List<String> data, SalesData salesData) {
        Salesman salesman = new Salesman();

        salesman.setCpf(data.get(CPF_INDEX));
        salesman.setName(data.get(NAME_INDEX));
        salesman.setSalary(Double.parseDouble(data.get(SALARY_INDEX)));

        salesData.addSalesman(salesman);
    }

    @Override
    public String getCode() {
        return code;
    }
}
