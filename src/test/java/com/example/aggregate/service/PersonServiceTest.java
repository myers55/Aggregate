package com.example.aggregate.service;

import com.example.aggregate.domain.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    PersonService personService;

    @Test
    public void testTransactional() {
        Person person1 = createTestPerson();
        Person person2 = createTestPerson();
        List<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);

        // Cause an error
        person2.setFirstName(null);
        try {
            personService.add(people);
        } catch(Exception e) {
            // expected this
        }

        people = personService.get();
        Person person3 = findInList(people, person1.getFirstName(), person1.getLastName());
        Assert.assertNull("The first person created should have rolled back", person3);
    }



    private Person createTestPerson() {
        // Get unique names every time this test runs
        String firstName = Long.toString(System.currentTimeMillis());
        String lastName = Long.toString(System.currentTimeMillis());

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }


    private Person findInList(List<Person> people, String first, String last) {
        // Find the new person in the list
        boolean found = false;
        for (Person person : people) {
            if (person.getFirstName().equals(first) && person.getLastName().equals(last)) {
                return person;
            }
        }
        return null;
    }
}
