package ru.rvsoft.springbatch.service;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import ru.rvsoft.springbatch.model.Person;

import java.util.Locale;

@Service
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) {
        final  String name = person.getFirstName().toUpperCase(Locale.ROOT);
        final  String lastName = person.getLastName().toUpperCase(Locale.ROOT);
        Person newPerson = new Person(lastName, name);
        System.out.println("Converting: [" + person + "] into [" + newPerson + "]");
        return newPerson;
    }
}
