
import java.util.Objects;
import java.util.Scanner;
import java.util.function.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static  Predicate<Integer> isNull = Objects::isNull;

    private static  BiFunction<Integer, Integer, Integer> sum = (a, b) ->
            isNull.test(a) || isNull.test(b) ? null : a + b;

    private static  BiFunction<Integer, Integer, Integer> mul = (a, b) ->
            isNull.test(a) || isNull.test(b) ? null : a * b;

    private static  BiFunction<Integer, Integer, Integer> div = (a, b) ->
            isNull.test(a) || isNull.test(b) ? null : a / b;

    private static  BiFunction<Integer, Integer, Integer> dif = (a, b) ->
            isNull.test(a) || isNull.test(b) ? null : a - b;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        Integer a = enterNumber();
        if (a == null) return;
        BiFunction function = enterOperator();
        if (function == null) return;
        Integer b = enterNumber();
        if (b == null) return;
        System.out.println(function.apply(a, b));

    }

    private static BiFunction enterOperator() {

        Predicate<String> validator = (string) -> string.matches("[+-/*]");

        Predicate<String> isExit = (string) -> string.intern().equals("exit");

        Supplier exit = () -> {
            System.out.println("Exit");
            return null;
        };

        Supplier retry = () -> {
            System.out.println("Type operator only, please. Type 'exit' to exit");
            return enterOperator();
        };

        Predicate<String> isSum = (string) ->
                string.intern().equals("+");

        Predicate<String> isMul = (string) ->
                string.intern().equals("*");

        Predicate<String> isDiv = (string) ->
                string.intern().equals("/");

        BiFunction<String, Predicate, BiFunction> exitOrRetry = (string, isexit) ->
                isExit.test(string) ? (BiFunction) exit.get() : (BiFunction) retry.get();

        Function<String, BiFunction> choose = (string) ->
                isSum.test(string) ? sum : isMul.test(string) ? mul : isDiv.test(string) ? div : dif;


        BiFunction<String, Predicate, BiFunction> getFunc =
                (string, test) -> test.test(string) ? choose.apply(string) : exitOrRetry.apply(string, isExit);

        return getFunc.apply(scanner.nextLine(), validator);

    }

    private static Integer enterNumber() {

        Predicate<String> validator = (string) -> string.matches("\\b[0-9]+\\b");

        Predicate<String> isExit = (string) -> string.intern().equals("exit");

        Supplier exit = () -> {
            System.out.println("Exit");
            return null;
        };

        Supplier retry = () -> {
            System.out.println("Type number only, please. Type 'exit' to exit");
            return enterNumber();
        };

        BiFunction<String, Predicate, Integer> exitOrRetry = (string, isexit) ->
                isexit.test(string) ? (Integer) exit.get() : (Integer) retry.get();

        BiFunction<String, Predicate, Integer> getInt =
                (string, test) -> test.test(string) ? Integer.parseInt(string) : exitOrRetry.apply(string, isExit);

        return getInt.apply(scanner.nextLine(), validator);

    }
}
