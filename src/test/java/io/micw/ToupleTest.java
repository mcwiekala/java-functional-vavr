package io.micw;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ToupleTest {

    @Test
    public void check_1() {
        //given
        String javaText = "Java";
        int version = 8;
        // when
        Tuple2<String, Integer> java8 = Tuple.of(javaText, version);
        String element1 = java8._1;
        int element2 = java8._2();
        // then
        assertThat(element1, equalTo(javaText));
        assertThat(element2, equalTo(version));
    }

    @Test
    public void check_2() {
        //given
        String javaText = "Hello Touple";
        int version = 8;
        double parts = 3.0;
        // when
        Tuple3<String, Integer, Double> java8 = Tuple.of(javaText, version, parts);
        String element1 = java8._1;
        int element2 = java8._2();
        double element3 = java8._3();
        // then
        assertThat(element1, equalTo(javaText));
        assertThat(element2, equalTo(version));
        assertThat(element3, equalTo(parts));
    }
}
