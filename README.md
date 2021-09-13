# Functional Java with Vavr
![](docs/functional.png)
[source](https://shastri-shankar9.medium.com/functional-programming-in-scala-through-q-as-part-1-45802a72d62a)
## Functional Java
Historically Java supported only imperative apporach of coding. Which means that a developer had to specify each step that the computer must take to deliver the goal. 

```java
List<String> destinationList = new ArrayList<>();
for (String value : sourceList) {
    if (value.contains("Hello")) {
        destinationList.add(value);
    }
}
```
Since 8 version, Java introduced more declarative features which allows Functional Programming. So instead of describing each step with state you are able to pass only behaviour. The similar code you can write with Java 8 in this way:

```java
List<String> destinationList = sourceList.stream()
      .filter(word -> word.contains("Hello"))
      .collect(Collectors.toList());
```

###New features includes:
* Lambda
* Stream/primitive streams
* Optional
* Functional Interfaces (Supplier, Consumer, Predicate...  BiFunction etc.)
* Method references

However this features are not ideal and it could be delivered in better way. That's the reason why Vavr was invented (formerly called Javaslang). 

## Vavr

>Vavr is an object-functional language extension to Java 8, which aims to reduce the lines of code and increase code quality. It provides persistent collections, functional abstractions for error handling, concurrent programming, pattern matching and much more.

https://github.com/vavr-io/vavr

##Project goals
This is a simple project to check Vavr features. 