package ru.rvsoft.springbatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.rvsoft.springbatch.model.Person;

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
}
