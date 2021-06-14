package com.bsmlima.salesdataanalysis.service;

import com.bsmlima.salesdataanalysis.dto.SalesData;
import com.bsmlima.salesdataanalysis.parser.FileParser;
import org.junit.Before;
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
public class ReportServiceTest {

    @Autowired
    private FileParser fileParser;
    @Autowired
    private ReportService reportService;
    private final List<String> fileContent = Arrays.asList(
            "001ç1234567891234çPedroç50000",
            "001ç3245678865434çPauloç40000.99",
            "001ç4245678657744çBrunoç800",
            "002ç2345675434544345çJose da SilvaçRural",
            "002ç2345675433444345çEduardo PereiraçRural",
            "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
            "003ç08ç[1-34-100]çPaulo");


    private SalesData salesData;

    @Before
    public void init() {
        salesData = fileParser.parse(fileContent.stream());
    }

    @Test
    public void getCustomersAmount() {
        assertEquals(2, reportService.getCustomersAmount(salesData));
    }

    @Test
    public void getSalesmenAmount() {
        assertEquals(3, reportService.getSalesmenAmount(salesData));
    }

    @Test
    public void getMostExpensiveSaleId() {
        assertEquals("08", reportService.getMostExpensiveSaleId(salesData));
    }

    @Test
    public void getWorstSalesman() {
        assertEquals("Bruno", reportService.getWorstSalesman(salesData));
    }
}