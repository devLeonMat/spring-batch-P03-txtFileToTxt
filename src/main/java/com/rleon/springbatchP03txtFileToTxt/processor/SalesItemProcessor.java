package com.rleon.springbatchP03txtFileToTxt.processor;

import com.google.gson.Gson;
import com.rleon.springbatchP03txtFileToTxt.model.dto.Sales;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.SQLOutput;

@Slf4j
public class SalesItemProcessor implements ItemProcessor<Sales, Sales> {

    private Gson gson;

    @Override
    public Sales process(Sales sales) throws Exception {
        System.out.println(sales);
        return sales;
    }
}
