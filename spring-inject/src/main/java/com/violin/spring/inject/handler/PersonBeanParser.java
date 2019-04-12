package com.violin.spring.inject.handler;

import com.violin.spring.inject.entity.Person;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;


public class PersonBeanParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return Person.class;
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        if(name.equals("")){
            name = "default";
        }
        String age = element.getAttribute("age");
        try{
        Integer.parseInt(age);
        }catch (Exception e){
            e.printStackTrace();
        }

        builder.addPropertyValue("name",name);
        builder.addPropertyValue("age",age);
    }
}
