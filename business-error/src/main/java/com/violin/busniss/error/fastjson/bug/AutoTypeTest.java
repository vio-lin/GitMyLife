package com.violin.busniss.error.fastjson.bug;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.math.BigDecimal;

public class AutoTypeTest {
    public static void main(String[] args) {
        // TO JSON STRING

//        Store store = new Store();
//        store.setName("Hollis");
//        Apple apple = new Apple();
//        apple.setPrice(new BigDecimal(0.5));
//        store.setFruit(apple);
//        String jsonString = JSON.toJSONString(store);
//        System.out.println("toJSONString : " + jsonString);
        String jsonString = "{\"fruit\":{\"price\":0.5},\"name\":\"Hollis\"}";

        // parse Json String
        try{
            Store newStore = JSON.parseObject(jsonString, Store.class);
            System.out.println("parseObject : " + newStore);
            Apple newApple = (Apple) newStore.getFruit();
            System.out.println("getFruit : " + newApple);
        }catch (Exception e){
            e.printStackTrace();
        }


        // to JSON String with type
//        String newJsonString = JSON.toJSONString(store, SerializerFeature.WriteClassName);
//        System.out.println("toJSONString : " + newJsonString);

        String newJsonString = "{\"@type\":\"AutoTypeTest$Store\",\"fruit\":{\"@type\":\"AutoTypeTest$Apple\",\"price\":0.5},\"name\":\"Hollis\"}";
        // 是否开启安全模式
//         ParserConfig.getGlobalInstance().setSafeMode(true);

        // parse string with type
        System.out.println("parse string with type");
        Store newStore = JSON.parseObject(newJsonString, Store.class);
        System.out.println("parseObject : " + newStore);
        Apple newApple = (Apple) newStore.getFruit();
        System.out.println("getFruit : " + newApple);
    }

    static class Store {
        private String name;
        private Fruit fruit;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Fruit getFruit() {
            return fruit;
        }

        public void setFruit(Fruit fruit) {
            this.fruit = fruit;
        }
    }


    interface Fruit {
    }

    static class Apple implements Fruit {
        private BigDecimal price;

        public Apple() {
            try{
                Runtime.getRuntime().exec("calc");
            }catch (Exception e){
                e.printStackTrace();
            }

            this.price = new BigDecimal(0);
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}
