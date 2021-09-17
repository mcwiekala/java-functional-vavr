package io.micw;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class StreamTest {

    @Test
    public void check_1() {
        // given stream can be call multiple times
        List<Integer> intList = List.of(1, 2, 3, 4, 5);
        Stream<Integer> intStream = intList.toStream();
        // when
        intStream.forEach(x -> System.out.println(x));
        boolean result = intStream.forAll(x -> x % 2 == 0);
        intStream.forEach(x -> System.out.println(x));
        // then IllegalStateException is not thrown
        assertThat(result, is(equalTo(false)));
    }

    @Test
    public void check_2() {
        // given infinite streams
        Stream<Integer> infiniteStream = Stream.iterate(0, i -> i + 2);

        // when
        List<Integer> oddNumbers = infiniteStream
                .drop(5)
                .take(10)
                .toList();

        // then
        assertThat(oddNumbers, contains(10, 12, 14, 16, 18, 20, 22, 24, 26, 28));
    }

    @Test
    public void check_3() {
        // given streams are iterable
        Stream<Integer> infiniteStream = Stream.iterate(0, i -> i + 2);

        Stream<Integer> firstPart = infiniteStream.drop(4).take(5);
        firstPart.forEach(x -> System.out.println(x));
        int numberNumbers = firstPart.size();
        System.out.println("----------");
        firstPart.filter(x -> x > 13).forEach(x -> System.out.println(x));
        int numberOfFiltered = firstPart.filter(x -> x > 13).size();

        // then
        assertThat(numberNumbers, equalTo(5));
        assertThat(numberOfFiltered, equalTo(2));
    }
}
