package ru.rvsoft.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.rvsoft.springbatch.model.Person;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

//    @Autowired
//    public JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    public StepBuilderFactory stepBuilderFactory;
//
//    @Bean
//    public FlatFileItemReader<Person> reader() { //+
//        FlatFileItemReader<Person> personItemReader = new FlatFileItemReaderBuilder<Person>()
//                .name("personItemReader")
//                .resource(new ClassPathResource("sample-data.csv"))
//                .delimited()
//                .names("firstName", "lastName")
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
//                    setTargetType(Person.class);
//                }})
//                .build();
//        return personItemReader;
//    }

    @Bean
    public ItemReader<Person> reader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Person>()
                             {
                                 {
                                     setLineTokenizer(new DelimitedLineTokenizer()
                                                      {
                                                          {
                                                              setNames("firstName", "lastName");
                                                          }
                                                      }
                                     );
                                     setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>()
                                     {
                                         {
                                             setTargetType(Person.class);
                                         }
                                     });
                                 }
                             }
        );
        return reader;
    }

    @Bean
    public ItemWriter<Person> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("insert into people (first_name, last_name) values (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }
//
//    @Bean
//    public PersonItemProcessor personItemProcessor() {//+
//        return new PersonItemProcessor();
//    }
//
//    @Bean
//    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) { //+
//        return new JdbcBatchItemWriterBuilder<Person>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
//                .dataSource(dataSource)
//                .build();
//    }


//    // tag::readerwriterprocessor[]
//    @Bean
//    public ItemReader<Person> reader() {
//        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
//        reader.setResource(new ClassPathResource("sample-data.csv"));
//        reader.setLineMapper(new DefaultLineMapper<Person>() {{
//            setLineTokenizer(new DelimitedLineTokenizer() {{
//                setNames(new String[] { "firstName", "lastName" });
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
//                setTargetType(Person.class);
//            }});
//        }});
//        return reader;
//    }
//
//    @Bean
//    public ItemProcessor<Person, Person> processor() {
//        return new PersonItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<Person> writer(DataSource dataSource) {
//        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
//        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
//        writer.setDataSource(dataSource);
//        return writer;
//    }
//    // end::readerwriterprocessor[]
//
    // tag::jobstep[]
//    @Bean //+
//    public Job personToUpperJob(JobBuilderFactory jobs, Step s1) {
//        Job importUserJob = jobs.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
//                .flow(s1)
//                .end()
//                .build();
//        return importUserJob;
//    }
//
    @Bean //+
    public Job personToUpperJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .build();
    }

//
//    @Bean //+
//    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
//                      ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
//        TaskletStep step1 = stepBuilderFactory.get("step1")
//                .<Person, Person>chunk(2)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//        return step1;
//    }
    @Bean //+
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
                      ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}
