package com.example.springbacth.config;

import com.example.springbacth.model.Users;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

  @Bean
  public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
      ItemReader<Users> itemReader, ItemProcessor<Users, Users> itemProcessor,
      ItemWriter<Users> itemWriter) {

    Step step = stepBuilderFactory.get("ETL-STEP")
        .<Users, Users>chunk(100)
        .reader(itemReader)
        .processor(itemProcessor)
        .writer(itemWriter).build();

    Job job = jobBuilderFactory.get("ELT-JOB")
        .incrementer(new RunIdIncrementer())
        .start(step).build();
    return job;
  }

  @Bean
  public FlatFileItemReader<Users> itemReader(@Value("${input}") Resource resource) {
    FlatFileItemReader<Users> flatFileItemReader = new FlatFileItemReader<Users>();
    flatFileItemReader.setResource(resource);
    flatFileItemReader.setName("CSV-READER");
    flatFileItemReader.setLinesToSkip(1);
    flatFileItemReader.setLineMapper(lineMapper());
    return flatFileItemReader;
  }

  @Bean
  public LineMapper<Users> lineMapper() {
    DefaultLineMapper<Users> lineMapper = new DefaultLineMapper<Users>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setNames(new String[]{"id", "name", "dept", "salary"});
    BeanWrapperFieldSetMapper<Users> fieldSetMapper = new BeanWrapperFieldSetMapper<Users>();
    fieldSetMapper.setTargetType(Users.class);
    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);
    return lineMapper;
  }

}

