package com.violin.test.java8.stream;

import java.util.stream.IntStream;

/**
 * @author guo.lin  2019/6/11
 */
public class PrimeList {
    public static void main(String[] args) {
       primes(numbers()).forEach(System.out::println);
    }

    static IntStream numbers(){
        return IntStream.iterate(2, n -> n + 1);
    }

    static int head(IntStream numbers){
        return numbers.findFirst().getAsInt();
    }

    static IntStream tail(IntStream numbers){
        return numbers.skip(1);
    }

    static IntStream primes(IntStream numbers) {
        int head = head(numbers);
        return IntStream.concat(
                IntStream.of(head),
                primes(tail(numbers).filter(n -> n % head != 0))
        );
    }

}
