package io.micw;

import io.vavr.Function0;
import io.vavr.Function2;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class FunctionalTest {

    @Test
    public void check_1() {
        // given
        String textNumber = "123";
        Integer number1 = 123;
        Integer number2 = 453;
        Function2<String, Integer, Boolean> function = (text, integer) -> Integer.parseInt(text) == integer;
        // when
        Boolean result1 = function.apply(textNumber, number1);
        Boolean result2 = function.apply(textNumber, number2);
        // then
        assertThat(result1, is(true));
        assertThat(result2, is(false));
    }

    @Test
    public void check_2() {
        // given
        String hello = "Hello";
        String world = " world!";
        Function0<String> function1 = () -> hello;
        Function0<String> function2 = function1.andThen((text) -> text + world);
        // when ,then
        assertThat(function1.apply(), equalTo(hello));
        assertThat(function2.apply(), equalTo(hello + world));
    }

    @Test
    public void check_3() {
        // given check cache
        Function0<Double> hashCache =
                Function0.of(() -> {
                            System.out.println("Processing");
                            return Math.random();
                        }
                ).memoized();

        // when
        double randomValue1 = hashCache.apply();
        double randomValue2 = hashCache.apply();

        // then
        assertThat(randomValue1, equalTo(randomValue2));
    }

    @Test
    public void check_4() {
        // given
        Integer number1 = 2;
        Integer number2 = 5;

        Function2<Integer, Integer, Integer> subtraction = (a, b) -> a - b;

        // when
        Integer result1 = subtraction.apply(number1, number2);
        Integer result2 = subtraction.reversed().apply(number1, number2);

        // then
        assertThat(result1, equalTo(-3));
        assertThat(result2, equalTo(3));
    }
}
