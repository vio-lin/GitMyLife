package com.violin.test.java8.future;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static java.util.stream.Collectors.toList;

/**
 * @author guo.lin  2019/5/30
 */
public class FutureTest {
    public static void main(String[] args) {
//        callSync();
//        callAsync();
        callMutilShop();
    }

    private static void callMutilShop() {
        String product = "someProduct";
        List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"));
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> String.format("%s price is %s",
                                        shop.getName(), shop.getPrice(product))))
                        .collect(toList());
        List<String> productList =  priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
        System.out.println(productList);
    }

    private static void callSync() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        String price = shop.getPrice("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime
                + " msecs");
        System.out.printf("Price is %.2f%n", price);

    }

    private static void callAsync() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        CompletableFuture<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime
                + " msecs");
        // 执行更多任务，比如查询其他商店
        doSomethingElse();
        // 在计算商品价格的同时
        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    public static void doSomethingElse() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
