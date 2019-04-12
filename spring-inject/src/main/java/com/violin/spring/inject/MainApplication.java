package com.violin.spring.inject;

import com.violin.spring.inject.entity.Person;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author guo.lin
 */
public class MainApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Person person =context.getBean(Person.class);
        System.out.println(person);
    }
}
