package io.micw;

import io.vavr.collection.CharSeq;
import io.vavr.collection.Seq;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

class ValidationTest {

    @Test
    public void check_1() {
        //given
        DogValidator dogValidator = new DogValidator();
        Validation<Seq<String>, Dog> lessieValid = dogValidator.validateDog("Lessie", 4);
        Validation<Seq<String>, Dog> maxInvalid = dogValidator.validateDog("M@x", 6);

        // when
        Dog lessie = lessieValid.get();
        Try<Dog> maxTry = Try.of(maxInvalid::get);

        // then
        assertThat(lessie.getName(), equalTo("Lessie"));
        assertThat(lessie.getLegs(), equalTo(4));
        assertThat(maxInvalid.getError(), contains(DogValidator.NAME_ERROR, DogValidator.LEGS_ERROR));
        assertThat(maxTry.getCause().getClass(), equalTo(NoSuchElementException.class));
    }

    @Value
    static class Dog {
        String name;
        Integer legs;
    }

    static class DogValidator {

        public static final String NAME_ERROR = "Name error";
        public static final String LEGS_ERROR = "Legs error";

        public Validation<Seq<String>, Dog> validateDog(String name, Integer legs) {
            return Validation.combine(
                    validateName(name, NAME_ERROR),
                    validateLegs(legs, LEGS_ERROR))
                    .ap(Dog::new);
        }

        private Validation<String, String> validateName(String name, String error) {
            return CharSeq.of(name)
                    .replaceAll("[A-Za-z ]", "")
                    .transform(seq -> seq.isEmpty()
                            ? Validation.valid(name)
                            : Validation.invalid(error));
        }

        private Validation<String, Integer> validateLegs(Integer legs, String error) {
            return legs >= 0 && legs <= 4 ?
                    Validation.valid(legs)
                    : Validation.invalid(error);
        }

    }
}
