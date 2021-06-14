package com.bsmlima.salesdataanalysis.parser;

import com.bsmlima.salesdataanalysis.dto.Sale;
import com.bsmlima.salesdataanalysis.dto.SaleItem;
import com.bsmlima.salesdataanalysis.dto.SalesData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileParserTest {

    @Autowired
    private FileParser fileParser;
    private final List<String> fileContent = Arrays.asList(
            "001ç1234567891234çPedroç50000",
            "002ç2345675434544345çJose da SilvaçRural",
            "003ç10ç[1-10-100,2-30-2.50]çPedro");

    @Test
    public void parseShouldReturnCorrectCustomers() {
        SalesData salesData = fileParser.parse(fileContent.stream());

        assertEquals(1, salesData.getCustomers().size());

        assertEquals("2345675434544345", salesData.getCustomers().get(0).getCnpj());
        assertEquals("Jose da Silva", salesData.getCustomers().get(0).getName());
        assertEquals("Rural", salesData.getCustomers().get(0).getArea());
    }

    @Test
    public void parseShouldReturnCorrectSalesman() {
        SalesData salesData = fileParser.parse(fileContent.stream());

        assertEquals(1, salesData.getSalesmen().size());

        assertEquals("1234567891234", salesData.getSalesmen().get(0).getCpf());
        assertEquals("Pedro", salesData.getSalesmen().get(0).getName());
        assertEquals(Double.valueOf(50000), salesData.getSalesmen().get(0).getSalary());
    }

    @Test
    public void parseShouldReturnCorrectSales() {
        SalesData salesData = fileParser.parse(fileContent.stream());

        assertEquals(1, salesData.getSales().size());

        assertEquals("10", salesData.getSales().get(0).getId());
        assertEquals("Pedro", salesData.getSales().get(0).getSalesmanName());
    }

    @Test
    public void parseShouldReturnCorrectSaleItems() {
        SalesData salesData = fileParser.parse(fileContent.stream());
        Sale sale = salesData.getSales().get(0);

        assertEquals(2, sale.getItems().size());

        SaleItem item1 = sale.getItems().get(0);

        assertEquals(1, item1.getId());
        assertEquals(10, item1.getQuantity());
        assertEquals(Double.valueOf(100), item1.getPrice());

        SaleItem item2 = sale.getItems().get(1);

        assertEquals(2, item2.getId());
        assertEquals(30, item2.getQuantity());
        assertEquals(Double.valueOf(2.50), item2.getPrice());
    }
}