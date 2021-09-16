package io.micw;

import io.vavr.Lazy;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class LazyTest {

    @Test
    public void check_1() {
        // given
        Lazy<Double> lazy1 = Lazy.of(Math::random);
        Lazy<Double> lazy2 = Lazy.of(Math::random);

        // when
        Double random1 = lazy2.getOrElseThrow(RuntimeException::new);
        Double random3 = lazy2.getOrElseThrow(RuntimeException::new);
        Double random2 = lazy2.getOrElseThrow(RuntimeException::new);

        // then
        assertThat(lazy1.isEvaluated(), is(false));
        assertThat(lazy2.isEvaluated(), is(true));
        assertThat(random1, equalTo(random2));
        assertThat(random2, equalTo(random3));
    }

    @Test
    public void check_2() {
        // given
        final String SLOTH = "Sloth";
        AtomicInteger numberOfCalls = new AtomicInteger(0);
        Lazy<String> lazyString = Lazy.of(() -> {
            System.out.println(numberOfCalls.incrementAndGet());
            return SLOTH;
        });

        // when
        String string1 = lazyString.getOrElseThrow(RuntimeException::new);
        String string2 = lazyString.getOrElseThrow(RuntimeException::new);
        String string3 = lazyString.getOrElseThrow(RuntimeException::new);

        // then
        assertThat(lazyString.isEvaluated(), is(true));
        assertThat(string1, equalTo(SLOTH));
        assertThat(string2, equalTo(SLOTH));
        assertThat(string3, equalTo(SLOTH));
        assertThat(numberOfCalls.get(), equalTo(1));
    }

}
