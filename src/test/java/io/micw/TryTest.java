package io.micw;

import io.vavr.API;
import io.vavr.Lazy;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TryTest {

    @Test
    public void check_1() {
        // given try Checked exception
        String badStringUrl = "@@@wrong-url";
        String url = Try.of(() -> new URL(badStringUrl))
                .map(uri -> uri.toString())
                .getOrElse("default");

        // when ,then
        assertThat(url, equalTo("default"));
    }

    @Test
    public void check_2() {
        // given
        String badStringUrl = "@@@wrong-url";
        String recoveryText = "We had a problem!";
        Try<String> t1 = Try.of(() -> recoveryText);

        // when
        String url = Try.of(() -> new URL(badStringUrl))
                .map(uri -> uri.toString())
                .recoverWith(URISyntaxException.class, t1).getOrElse("default");

        // then
        assertThat(url, equalTo(recoveryText));
    }

    @Test
    public void check_3() {
        // given try Unchecked exception
        Try<Integer> result = Try.of(() -> 1 / 0);
        // when ,then
        assertThat(result.getCause().getClass(), equalTo(ArithmeticException.class));
    }

}
