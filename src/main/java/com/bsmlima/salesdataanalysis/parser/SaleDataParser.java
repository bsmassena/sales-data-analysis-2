package com.bsmlima.salesdataanalysis.parser;

import com.bsmlima.salesdataanalysis.dto.Sale;
import com.bsmlima.salesdataanalysis.dto.SaleItem;
import com.bsmlima.salesdataanalysis.dto.SalesData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleDataParser implements DataParser {

    private static final int SALE_ID_INDEX = 1;
    private static final int SALE_ITEMS_INDEX = 2;
    private static final int SALE_SALESMAN_NAME_INDEX = 3;

    private static final int ITEM_ID_INDEX = 0;
    private static final int ITEM_QUANTITY_INDEX = 1;
    private static final int ITEM_PRICE_INDEX = 2;

    @Value("${parser.code.sale}")
    private String code;
    @Value("${parser.separator.item}")
    private String itemSeparator;
    @Value("${parser.separator.field}")
    private String itemFieldSeparator;


    @Override
    public void parse(List<String> data, SalesData salesData) {
        Sale sale = new Sale();

        sale.setId(data.get(SALE_ID_INDEX));
        sale.setItems(parseItems(data.get(SALE_ITEMS_INDEX)));
        sale.setSalesmanName(data.get(SALE_SALESMAN_NAME_INDEX));

        salesData.addSale(sale);
    }

    @Override
    public String getCode() {
        return code;
    }

    private List<SaleItem> parseItems(String items) {
        items = removeBrackets(items);

        return Arrays.stream(items.split(itemSeparator)).map(item -> {
            List<String> itemData = Arrays.asList(item.split(itemFieldSeparator));

            return new SaleItem( Integer.parseInt(itemData.get(ITEM_ID_INDEX)),
                                 Integer.parseInt(itemData.get(ITEM_QUANTITY_INDEX)),
                                 Double.parseDouble(itemData.get(ITEM_PRICE_INDEX)));
        }).collect(Collectors.toList());
    }

    private String removeBrackets(String items) {
        return items.substring(1, items.length()-1);
    }
}
