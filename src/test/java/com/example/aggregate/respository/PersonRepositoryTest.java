package com.example.aggregate.respository;

import com.example.aggregate.common.PersonUtils;
import com.example.aggregate.domain.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.aggregate.common.PersonUtils.createTestPerson;
import static com.example.aggregate.common.PersonUtils.findInList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void testAddGet() {
        // Get unique names every time this test runs
        String firstName = Long.toString(System.currentTimeMillis());
        String lastName = Long.toString(System.currentTimeMillis());

        Person person1 = new Person();
        person1.setFirstName(firstName);
        person1.setLastName(lastName);
        personRepository.add(person1);

        List<Person> people = personRepository.get();

        Person person2 = findInList(people, firstName, lastName);
        Assert.assertNotNull(person2);

        Person person3 = personRepository.getById(person2.getId());
        Assert.assertNotNull(person3);
        Assert.assertEquals(firstName, person3.getFirstName());
        Assert.assertEquals(lastName, person3.getLastName());
    }

    @Test
    public void testUpdate() {
        Person person1 = createTestPerson();
        personRepository.add(person1);

        List<Person> people = personRepository.get();

        Person person2 = findInList(people, person1.getFirstName(), person1.getLastName());
        Assert.assertNotNull(person2);

        String updateFirstName = Long.toString(System.currentTimeMillis());
        String updateLastName = Long.toString(System.currentTimeMillis());

        person2.setFirstName(updateFirstName);
        person2.setLastName(updateLastName);
        personRepository.update(person2);

        people = personRepository.get();

        Person person3 = findInList(people, updateFirstName, updateLastName);
        Assert.assertNotNull(person3);
        Assert.assertEquals(person2.getId(), person3.getId());
    }

    @Test
    public void testDelete() {
        Person person1 = createTestPerson();
        personRepository.add(person1);

        List<Person> people = personRepository.get();

        Person person2 = findInList(people, person1.getFirstName(), person1.getLastName());
        Assert.assertNotNull(person2);

        personRepository.delete(person2.getId());

        people = personRepository.get();
        Person person3 = PersonUtils.findInList(people, person1.getFirstName(), person1.getLastName());
        Assert.assertNull(person3);
    }
    @Test
    public void testDeleteMuliple() {
        Person one = new Person();
        one.setFirstName("Jackson");
        one.setLastName("Levi");

        Person two = new Person();
        two.setFirstName("Dylan");
        two.setLastName("Myers");

        personRepository.add(one);
        personRepository.add(two);

        List<Person> people = personRepository.get();

        List<Integer> ids = Arrays.asList(people.get(0).getId(), people.get(1).getId());

        personRepository.deleteAll(ids);

//        Assert.assertNull(personRepository.getById(one.getId()));
//        Assert.assertNull(personRepository.getById(two.getId()));

        people = personRepository.get();
        Assert.assertTrue(people.isEmpty());
        //create a person
        // create a second person
        // add to database
        // get people from the database
        // remove people from the database
        // get people from the database(no one should be in there)

    }

}
