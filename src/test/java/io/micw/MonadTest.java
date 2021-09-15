package io.micw;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MonadTest {

    public static final String ERROR_MESSAGE = "Marks not acceptable";
    public static final String DEFAULT_MESSAGE = "default";
    public static final int DEFAULT_NUMBER = -1;

    static class SucessEvent {
        String message = "SUCESS";
    }

    static class EventFailure {
        Integer errorCode = 502;
    }

    @Test
    public void check_1() {
        // given, when
        Either<String, Integer> either1 = computeWithEither(80);
        Either<String, Integer> either2 = computeWithEither(89);

        String result_a1 = either1.left().getOrElse(DEFAULT_MESSAGE);
        Integer result_b1 = either1.right().getOrElse(DEFAULT_NUMBER);

        String result_a2 = either2.left().getOrElse(DEFAULT_MESSAGE);
        Integer result_b2 = either2.right().getOrElse(DEFAULT_NUMBER);

        // then
        assertThat(result_a1, equalTo(ERROR_MESSAGE));
        assertThat(result_b1, equalTo(DEFAULT_NUMBER));

        assertThat(result_a2, equalTo(DEFAULT_MESSAGE));
        assertThat(result_b2, equalTo(89));
    }

    @Test
    public void check_2() {
        // given
        String message = "";
        Integer code = -1;
        Either<EventFailure, SucessEvent> either1 = getMessageOnGreaterThan20(21);
        Either<EventFailure, SucessEvent> either2 = getMessageOnGreaterThan20(12);

        // when
        if (either1.isRight()) {
            SucessEvent sucessEvent = either1.right().get();
            message = sucessEvent.message;
        }
        EventFailure eventFailure = either2.left().getOrElseThrow((Supplier<RuntimeException>) RuntimeException::new);
        code = eventFailure.errorCode;

        // then
        assertThat(message, equalTo("SUCESS"));
        assertThat(code, equalTo(502));
    }

    private static Either<String, Integer> computeWithEither(int marks) {
        if (marks < 85) {
            return Either.left(ERROR_MESSAGE);
        } else {
            return Either.right(marks);
        }
    }

    private static Either<EventFailure, SucessEvent> getMessageOnGreaterThan20(int number) {
        if (number < 20) {
            return Either.left(new EventFailure());
        } else {
            return Either.right(new SucessEvent());
        }
    }
}
