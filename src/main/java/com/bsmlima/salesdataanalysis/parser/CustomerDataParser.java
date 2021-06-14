package com.bsmlima.salesdataanalysis.parser;

import com.bsmlima.salesdataanalysis.dto.Customer;
import com.bsmlima.salesdataanalysis.dto.SalesData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDataParser implements DataParser {

    private static final int CNPJ_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int AREA_INDEX = 3;

    @Value("${parser.code.customer}")
    private String code;

    @Override
    public void parse(List<String> data, SalesData salesData) {
        Customer customer = new Customer();

        customer.setCnpj(data.get(CNPJ_INDEX));
        customer.setName(data.get(NAME_INDEX));
        customer.setArea(data.get(AREA_INDEX));

        salesData.addCustomer(customer);
    }

    @Override
    public String getCode() {
        return code;
    }
}
