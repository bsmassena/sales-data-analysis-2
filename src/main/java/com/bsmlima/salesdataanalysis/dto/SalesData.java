package com.bsmlima.salesdataanalysis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class SalesData {

    private final List<Salesman> salesmen = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Sale> sales = new ArrayList<>();
    private final HashMap<String, Double> salesmenSalesRevenue = new HashMap<>();

    public void addSalesman(Salesman salesman) {
        this.salesmen.add(salesman);
        this.salesmenSalesRevenue.putIfAbsent(salesman.getName(), 0.0);
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public void addSale(Sale sale) {
        this.sales.add(sale);
        this.salesmenSalesRevenue.putIfAbsent(sale.getSalesmanName(), 0.0);

        Double newValue = salesmenSalesRevenue.get(sale.getSalesmanName()) + sale.getItems().stream()
                .map(item -> item.getQuantity() * item.getPrice())
                .reduce(0.0, Double::sum);

        this.salesmenSalesRevenue.put(sale.getSalesmanName(), newValue);
    }
}
