package com.rleon.springbatchP03txtFileToTxt.config;

import com.google.gson.Gson;
import com.rleon.springbatchP03txtFileToTxt.model.dto.Sales;
import com.rleon.springbatchP03txtFileToTxt.processor.SalesItemProcessor;
import com.rleon.springbatchP03txtFileToTxt.writer.SalesSave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@Slf4j
@EnableBatchProcessing
public class BatchConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private Gson gson;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Value("${file.input}")
    private String fileInput;

    public FlatFileItemReader<Sales> reader() {

        return new FlatFileItemReaderBuilder<Sales>().name("salesItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(new String[]{"name"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Sales.class);
                }})
                .build();
    }

    public SalesItemProcessor processor() {
        return new SalesItemProcessor();
    }

    @Bean
    public Job demo3Job() throws Exception {
        return this.jobBuilderFactory.get("demo3")
                .start(step1Demo3())
                .build();
    }

    @Bean
    public Step step1Demo3() throws Exception {
        return this.stepBuilderFactory.get("step1")
                .<Sales, Sales>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(salesSave())
                .build();
    }


    public SalesSave salesSave() {
        return new SalesSave();
    }

}
