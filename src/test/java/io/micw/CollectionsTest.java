package io.micw;

import io.vavr.API;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


class CollectionsTest {

    @Test
    public void check_1() {
        // given
        List<String> wordList = API.List("1", "2", "4", "5");
        String element1 = "6";
        String element2 = "7";

        // when
        List<String> changedWordList1 = wordList.append(element1);
        List<String> changedWordList2 = wordList.append(element2);
        // then
        assertThat(wordList, not(hasItem(element1)));
        assertThat(wordList, not(hasItem(element2)));

        assertThat(changedWordList1, hasItem(element1));

        assertThat(changedWordList2, not(hasItem(element1)));
        assertThat(changedWordList2, hasItem(element2));
    }

    @Test
    public void check_2() {
        // given
        java.util.List<Integer> javaList = java.util.List.of(1, 2, 3, 4);
        Integer element = Integer.valueOf(7);

        // when
        List<Integer> vavrList = List.ofAll(javaList);
        List<Integer> vavrListModified = vavrList.append(element);

        java.util.List<Integer> resultJavaList = vavrList.toJavaList();
        // then
        assertThat(vavrListModified, hasItem(element));
        assertThat(javaList, containsInAnyOrder(resultJavaList.toArray()));

    }

    @Test
    public void check_3() {
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
    public void check_4() {
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
    public void check_5() {
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
