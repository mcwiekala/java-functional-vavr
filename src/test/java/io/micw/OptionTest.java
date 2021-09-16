package io.micw;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionTest {

    @Test
    public void check_1() {
        // given
        String text = "text";
        String defaultText = "default";
        // when
        Option<String> name = Option.of(text);
        Option<String> nullValue = Option.of(null);
        // then
        assertThat(name.getOrElse(defaultText), equalTo(text));
        assertThat(nullValue.getOrElse(defaultText), equalTo(defaultText));
    }

    @Test
    public void check_2() {
        // given
        Integer result1 = getProcessText("17");
        Integer result2 = getProcessText("3");

        // when ,then
        assertThat(result1, equalTo(17));
        assertThat(result2, equalTo(-1));
    }

    @Test
    public void check_3() {
        // given
        Class<UnsupportedOperationException> exceptionClass = UnsupportedOperationException.class;
        String text = "text";
        Optional<String> stringOptional = Optional.ofNullable(text);
        Optional<String> stringOptional2 = Optional.empty();

        // when
        Option<String> result1 = Option.ofOptional(stringOptional);
        Exception exception = assertThrows(exceptionClass, () -> Option.ofOptional(stringOptional2)
                .getOrElseThrow(UnsupportedOperationException::new));

        // then
        assertThat(result1.getOrElse("default"), equalTo(text));
        assertThat(exception.getClass(), equalTo(exceptionClass));
    }

    @Test
    public void check_4() {
        // given
        AtomicReference<Boolean> bool = new AtomicReference<>(false);
        Option<String> option = Option.none();
        // when
        option.onEmpty(() -> bool.set(true));
        // then
        assertThat(bool.get(), equalTo(true));
    }

    private Integer getProcessText(String numberAsText) {
        return Option.of(numberAsText)
                .map(Integer::parseInt)
                .filter(integer -> integer > 5)
                .getOrElse(-1);
    }

}
