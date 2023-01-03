package ru.rvsoft.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.rvsoft.springbatch.model.Person;

import java.util.*;
import java.util.function.Function;

import static java.lang.String.format;

@SpringBootApplication
public class SpringBatchApplication implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private static String rs() {
        return null;
    }

    public static void main(String[] args) {
//⭐️
        ApplicationContext ctx = SpringApplication.run(SpringBatchApplication.class, args);
    }

    @Override
    public void run(String[] args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("file.input", "sample-data.csv");
        jobParametersBuilder.addString("file.output", "output.json");
        jobParametersBuilder.addString("trial", "1");
        this.jobLauncher.run(job, jobParametersBuilder.toJobParameters());
    }
}
