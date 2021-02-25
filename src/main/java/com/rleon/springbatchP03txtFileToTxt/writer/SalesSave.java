package com.rleon.springbatchP03txtFileToTxt.writer;

import com.rleon.springbatchP03txtFileToTxt.model.dto.Sales;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class SalesSave implements ItemWriter<Sales> {

    @Override
    public void write(List<? extends Sales> salesList) throws Exception {
        System.out.println("===============ITERACION 1===================");
        for (Sales sa : salesList) {
//            log.info(sa.getName());
            System.out.println("Nombre: " + sa.getName());
        }
        System.out.println("=========");
    }
}
