package com.violin.test.java8.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @author guo.lin  2019/5/30
 */
public class Shop {
    private Random random = new Random();
    private String shopName;

    public Shop(String shopName){
        this.shopName = shopName;
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[
                random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", shopName, price, code);
    }

    public CompletableFuture<Double> getPriceAsync(String product) {
        return createFutureFactory(product);
//        return createFutureSimple(product);
    }

    private CompletableFuture<Double> createFutureFactory(String product) {
        return CompletableFuture.supplyAsync(()-> calculatePrice(product));
    }

    // 两种构造的方式：1.使用基本的构造方式 2.使用类中的工厂方法
    private CompletableFuture<Double> createFutureSimple(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception ex) {
                // future中的错误处理
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        return futurePrice;
    }


    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public Object getName() {
        return this.shopName;
    }
}