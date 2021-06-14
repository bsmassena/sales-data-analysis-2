package com.bsmlima.salesdataanalysis.service;

import com.bsmlima.salesdataanalysis.dao.ReportDao;
import com.bsmlima.salesdataanalysis.dto.Sale;
import com.bsmlima.salesdataanalysis.dto.SalesData;
import com.bsmlima.salesdataanalysis.parser.FileParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

@Service
public class ReportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FileParser fileParser;
    private final ReportDao reportDao;
    private final String inputSuffix;
    private final String outputSuffix;
    private final String outputTemplate;

    public ReportService(FileParser fileParser, ReportDao reportDao, @Value("${files.input.suffix}") String inputSuffix,
                         @Value("${files.output.suffix}") String outputSuffix, @Value("${files.output.template}") String outputTemplate) {
        this.fileParser = fileParser;
        this.reportDao = reportDao;
        this.inputSuffix = inputSuffix;
        this.outputSuffix = outputSuffix;
        this.outputTemplate = outputTemplate;
    }

    public void processFile(String filename) throws IOException {
        if (!filename.endsWith(inputSuffix)) {
            logger.warn("Skipping file {}: unknown suffix", filename);
            return;
        }

        logger.info("Processing file {}", filename);
        SalesData salesData = fileParser.parse(reportDao.read(filename));
        logger.info("File {} parsed successfully", filename);
        String outputFilename = generateOutputFilename(filename);
        reportDao.save(outputFilename, String.format(outputTemplate,
                getCustomersAmount(salesData),
                getSalesmenAmount(salesData),
                getMostExpensiveSaleId(salesData),
                getWorstSalesman(salesData)));
        logger.info("File {} was successfully saved as {}", filename, outputFilename);

    }

    public int getCustomersAmount(SalesData salesData) {
        return salesData.getCustomers().size();
    }

    public int getSalesmenAmount(SalesData salesData) {
        return salesData.getSalesmen().size();
    }

    public String getMostExpensiveSaleId(SalesData salesData) {
        Comparator<Sale> saleComparator = Comparator.comparingDouble(sale ->
            sale.getItems().stream().map(item -> item.getQuantity() * item.getPrice()).reduce(0.0, Double::sum));

        return salesData.getSales().stream()
                .max(saleComparator)
                .get()
                .getId();
    }

    public String getWorstSalesman(SalesData salesData) {
        return Collections.min(salesData.getSalesmenSalesRevenue().entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    private String generateOutputFilename(String inputFilename) {
        String filenameWithoutSuffix = StringUtils.removeEnd(inputFilename, inputSuffix);
        return filenameWithoutSuffix.concat(outputSuffix);
    }
}
