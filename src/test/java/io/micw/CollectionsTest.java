package io.micw;

import io.vavr.API;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
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
}
