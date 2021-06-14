package com.bsmlima.salesdataanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sale {

    private String id;
    private List<SaleItem> items;
    private String salesmanName;
}
