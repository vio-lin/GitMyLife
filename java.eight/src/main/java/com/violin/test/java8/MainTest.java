package com.violin.test.java8;

/**
 * @author guo.lin  2019/5/20
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
 * (2) 交易员都在哪些不同的城市工作过？
 * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
 * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
 * (5) 有没有交易员是在米兰工作的？
 * (6) 打印生活在剑桥的交易员的所有交易额。
 * (7) 所有交易中，最高的交易额是多少？
 * (8) 找到交易额最小的交易。
 */
public class MainTest {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //第一题
        System.out.println("第一题");
        transactions.stream().filter(a -> a.getYear() == 2011).sorted(Comparator.comparing(Transaction::getValue)).forEach(System.out::println);
        //第二题
        System.out.println("第二题");
        transactions.stream().map(Transaction::getTrader).map(Trader::getCity).distinct().forEach(System.out::println);
        //第三题
        System.out.println("第三题");
        transactions.stream().map(Transaction::getTrader).distinct().filter(a->a.getCity().equals("Cambridge")).sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);
        //第四题
        System.out.println("第四题");
        transactions.stream().map(Transaction::getTrader).distinct().sorted(Comparator.comparing(Trader::getName)).forEach(System.out::println);
        //第五题
        System.out.println("第五题");
        System.out.println(transactions.stream().map(Transaction::getTrader).anyMatch(a->a.getCity().equals("Milan")));
        //第六题
        System.out.println("第六题");
        transactions.stream().filter(a->a.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue).forEach(System.out::println);
        //第七题
        System.out.println("第七题");
        System.out.println(transactions.stream().map(Transaction::getValue).reduce(Math::max).get());
        //第八题
        System.out.println("第八题");
        System.out.println(transactions.stream().map(Transaction::getValue).reduce(Math::min).get());

    }

    public static class Trader {
        private final String name;
        private final String city;

        public Trader(String n, String c) {
            this.name = n;
            this.city = c;
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "Trader{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

    public static class Transaction {
        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return this.trader;
        }

        public int getYear() {
            return this.year;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return "{" + this.trader + ", " +
                    "year: " + this.year + ", " +
                    "value:" + this.value + "}";
        }
    }

}