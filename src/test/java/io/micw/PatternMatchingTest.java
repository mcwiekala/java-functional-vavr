package io.micw;

import io.vavr.MatchError;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.is;
import static io.vavr.Predicates.isIn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class PatternMatchingTest {

    public static final String FEMALE = "female";
    public static final String MALE = "male";
    public static final String EVEN = "even";
    public static final String ODD = "odd";
    public static final String DOG_MESSAGE = "This is a dog, no doubts!";
    public static final String ANIMAL_MESSAGE = "It can be a dog!";

    @Test
    public void check_1() {
        // given
        Integer input1 = 1;
        Integer input2 = 3;
        // when
        String output1 = matchNumber(input1);
        String output2 = matchNumber(input2);
        String output3 = matchNumber(null);
        //then
        assertThat(output1, equalTo("one"));
        assertThat(output2, equalTo("three"));
        assertThat(output3, equalTo("?"));
    }

    private String matchNumber(Integer input) {
        return Match(input).of(
                Case($(is(1)), "one"),
                Case($(is(2)), "two"),
                Case($(is(3)), "three"),
                Case($(), "?"));
    }

    @Test
    public void check_2() {
        // given
        String name1 = "Carolina";
        String name2 = "Boris";
        String notExistingName = "Edmund";

        // when
        String gender1 = matchName(name1);
        String gender2 = matchName(name2);
        Try<String> tryMatch = Try.of(() -> matchName(notExistingName));
        Option<String> optionGender = matchNameSafely(notExistingName);


        // then
        assertThat(gender1, equalTo(FEMALE));
        assertThat(gender2, equalTo(MALE));
        assertThat(tryMatch.getCause().getClass(), equalTo(MatchError.class));
        assertThat(optionGender.isEmpty(), equalTo(true));
    }

    @Test
    public void check_3() {
        // given
        Dog dog = new Dog();
        Animal animal = new Animal();
        // when
        String result1 = matchAnimal(dog);
        String result2 = matchAnimal(animal);
        // then
        assertThat(result1, equalTo(DOG_MESSAGE));
        assertThat(result2, equalTo(ANIMAL_MESSAGE));
    }

    private String matchAnimal(Animal animal) {
        return Match(animal).of(
                Case($(instanceOf(Dog.class)), DOG_MESSAGE),
                Case($(instanceOf(Animal.class)), ANIMAL_MESSAGE));
    }

    @Test
    public void check_4() {
        // given
        int number1 = 7;
        int number2 = 4;

        // when
        String result1 = matchNumberOddOrEven(number1);
        String result2 = matchNumberOddOrEven(number2);

        // then
        assertThat(result1, equalTo(ODD));
        assertThat(result2, equalTo(EVEN));
    }

    private String matchNumberOddOrEven(Integer number) {
        return Match(number).of(
                Case($(n -> n % 2 == 0), EVEN),
                Case($(n -> n % 2 != 0), ODD));
    }

    private String matchName(String name) {
        return Match(name).of(
                Case($(isIn("Anna", "Beata", "Carolina", "Denisa")), FEMALE),
                Case($(isIn("Adam", "Boris", "Cyril", "David")), MALE));
    }

    private Option<String> matchNameSafely(String name) {
        return Match(name).option(
                Case($(isIn("Anna", "Beata", "Carolina", "Denisa")), FEMALE),
                Case($(isIn("Adam", "Boris", "Cyril", "David")), MALE));
    }

    static class Dog extends Animal {
    }

    static class Animal {
    }

}
