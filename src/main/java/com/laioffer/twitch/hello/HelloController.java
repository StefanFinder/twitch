package com.laioffer.twitch.hello;

import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        System.out.println("hello world");
        return "hello world";
    }

    @GetMapping("/happy")
    public String getError(){
        System.out.println("try happy");
        return "So happy to see you";
    }

    //dynamic data practise
    @GetMapping("/faker")
    public Person getFakeUser(@RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) String country){

        Faker faker = new Faker();
        String name = faker.name().fullName();
        String company = faker.company().name();
        String street = faker.address().streetAddress();
        city = city == null? faker.address().city(): city;
        state = state == null? faker.address().state(): state;
        country = country == null? faker.address().country(): country;

        String bookTitle = faker.book().title();
        String bookAuthor = faker.book().author();

        return new Person(name, company, new Address(street, city, state, country), new Book(bookTitle, bookAuthor));


    }
}
