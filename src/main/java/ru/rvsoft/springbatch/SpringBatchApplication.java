package ru.rvsoft.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.rvsoft.springbatch.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class SpringBatchApplication {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        System.out.println("start");
        ApplicationContext ctx = SpringApplication.run(SpringBatchApplication.class, args);
        System.out.println("stop");

//        SpringApplication.exit(ctx, () -> 9);

//        JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");
//        Job job = (Job) ctx.getBean("personToUpperJob");
//        JobExecution execution = jobLauncher.run(job, new JobParameters());
//
//        System.out.println(execution.getStatus());
//
//        List<Person> results = ctx.getBean(JdbcTemplate.class).query("SELECT first_name, last_name FROM people", new RowMapper<Person>() {
//            @Override
//            public Person mapRow(ResultSet rs, int row) throws SQLException {
//                return new Person(rs.getString(1), rs.getString(2));
//            }
//        });
//
//        for (Person person : results) {
//            System.out.println("Found <" + person + "> in the database.");
//        }
    }

    public void run(String[] args) {
        List<Person> personList =
                jdbcTemplate.query("select first_name, last_name from people",
                        (rs, row) -> new Person(rs.getString("first_name"), rs.getString("last_name")));

        for (Person p: personList) {
            System.out.println(p);
        }

    }
/**
 * org.springframework.batch.item.file.FlatFileParseException: Parsing error at line: 1 in resource=[class path resource [sample-data.csv]], input=[Jill;Doe]
 * 	at org.springframework.batch.item.file.FlatFileItemReader.doRead(FlatFileItemReader.java:189)
 * 	at org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader.read(AbstractItemCountingItemStreamItemReader.java:93)
 * 	at org.springframework.batch.core.step.item.SimpleChunkProvider.doRead(SimpleChunkProvider.java:99)
 * 	at org.springframework.batch.core.step.item.SimpleChunkProvider.read(SimpleChunkProvider.java:180)
 * 	at org.springframework.batch.core.step.item.SimpleChunkProvider$1.doInIteration(SimpleChunkProvider.java:126)
 * 	at org.springframework.batch.repeat.support.RepeatTemplate.getNextResult(RepeatTemplate.java:375)
 * 	at org.springframework.batch.repeat.support.RepeatTemplate.executeInternal(RepeatTemplate.java:215)
 * 	at org.springframework.batch.repeat.support.RepeatTemplate.iterate(RepeatTemplate.java:145)
 * 	at org.springframework.batch.core.step.item.SimpleChunkProvider.provide(SimpleChunkProvider.java:118)
 * 	at org.springframework.batch.core.step.item.ChunkOrientedTasklet.execute(ChunkOrientedTasklet.java:71)
 * 	at org.springframework.batch.core.step.tasklet.TaskletStep$ChunkTransactionCallback.doInTransaction(TaskletStep.java:407)
 * 	at org.springframework.batch.core.step.tasklet.TaskletStep$ChunkTransactionCallback.doInTransaction(TaskletStep.java:331)
 * 	at org.springframework.transaction.support.TransactionTemplate.execute(TransactionTemplate.java:140)
 * 	at org.springframework.batch.core.step.tasklet.TaskletStep$2.doInChunkContext(TaskletStep.java:273)
 * 	at org.springframework.batch.core.scope.context.StepContextRepeatCallback.doInIteration(StepContextRepeatCallback.java:82)
 * 	at org.springframework.batch.repeat.support.RepeatTemplate.getNextResult(RepeatTemplate.java:375)
 * 	at org.springframework.batch.repeat.support.RepeatTemplate.executeInternal(RepeatTemplate.java:215)
 * 	at org.springframework.batch.repeat.support.RepeatTemplate.iterate(RepeatTemplate.java:145)
 * 	at org.springframework.batch.core.step.tasklet.TaskletStep.doExecute(TaskletStep.java:258)
 * 	at org.springframework.batch.core.step.AbstractStep.execute(AbstractStep.java:208)
 * 	at org.springframework.batch.core.job.SimpleStepHandler.handleStep(SimpleStepHandler.java:148)
 * 	at org.springframework.batch.core.job.flow.JobFlowExecutor.executeStep(JobFlowExecutor.java:68)
 * 	at org.springframework.batch.core.job.flow.support.state.StepState.handle(StepState.java:68)
 * 	at org.springframework.batch.core.job.flow.support.SimpleFlow.resume(SimpleFlow.java:169)
 * 	at org.springframework.batch.core.job.flow.support.SimpleFlow.start(SimpleFlow.java:144)
 * 	at org.springframework.batch.core.job.flow.FlowJob.doExecute(FlowJob.java:137)
 * 	at org.springframework.batch.core.job.AbstractJob.execute(AbstractJob.java:319)
 * 	at org.springframework.batch.core.launch.support.SimpleJobLauncher$1.run(SimpleJobLauncher.java:147)
 * 	at org.springframework.core.task.SyncTaskExecutor.execute(SyncTaskExecutor.java:50)
 * 	at org.springframework.batch.core.launch.support.SimpleJobLauncher.run(SimpleJobLauncher.java:140)
 * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
 * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
 * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
 * 	at java.lang.reflect.Method.invoke(Method.java:498)
 * 	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)
 * 	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198)
 * 	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
 * 	at org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration$PassthruAdvice.invoke(SimpleBatchConfiguration.java:127)
 * 	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
 * 	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:212)
 * 	at com.sun.proxy.$Proxy42.run(Unknown Source)
 * 	at ru.rvsoft.springbatch.SpringBatchApplication.main(SpringBatchApplication.java:33)
 * Caused by: org.springframework.batch.item.file.transform.IncorrectTokenCountException: Incorrect number of tokens found in record: expected 2 actual 1
 * 	at org.springframework.batch.item.file.transform.AbstractLineTokenizer.tokenize(AbstractLineTokenizer.java:143)
 * 	at org.springframework.batch.item.file.mapping.DefaultLineMapper.mapLine(DefaultLineMapper.java:43)
 * 	at org.springframework.batch.item.file.FlatFileItemReader.doRead(FlatFileItemReader.java:185)
 * 	... 41 more
 */
}
