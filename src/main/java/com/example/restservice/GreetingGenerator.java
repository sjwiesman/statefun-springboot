package com.example.restservice;

public class GreetingGenerator {
    public String text(String name, int seen) {
        switch (seen) {
            case 0:
                return String.format("Hello %s ! \uD83D\uDE0E", name);
            case 1:
                return String.format("Hello again %s ! \uD83E\uDD17", name);
            case 2:
                return String.format("Third time is a charm! %s! \uD83E\uDD73", name);
            case 3:
                return String.format("Happy to see you once again %s ! \uD83D\uDE32", name);
            default:
                return String.format("Hello at the %d-th time %s \uD83D\uDE4C", seen + 1, name);
        }
    }
}
