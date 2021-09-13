package io.micw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class FunctionalTest {

    @Test
    public void example_11() {
        List<String> sourceList = new ArrayList<>();
        List<String> destinationList = sourceList.stream()
                .filter(word -> word.contains("Hello"))
                .collect(Collectors.toList());
        System.out.println(destinationList);

    }

    @Test
    public void example_1() {
        List<String> sourceList = List.of("Hello Java", "Hello Sir", "Kotlin", "Scala", "Hello Miss");
        List<String> destinationList = new ArrayList<>();
        for (String value : sourceList) {
            if (value.contains("Hello")) {
                destinationList.add(value);
            }
        }
        System.out.println(destinationList);



        boolean a = true;
        boolean b = true;

        assertThat(a, equalTo(b));
        assertThat(a, is(equalTo(b)));
        assertThat(a, is(b));
    }

}
