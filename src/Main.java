
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

    public static BiFunction enterOperator() {

        Predicate<String> validator = (string) -> string.matches("[+-/*]");

        Predicate<String> isExit = (string) -> string.intern() != null && string.intern() == "exit".intern();

        Supplier exit = () -> {
            System.out.println("Exit");
            return null;
        };

        Supplier retry = () -> {
            System.out.println("Type operator only, please. Type 'exit' to exit");
            return enterOperator();
        };

        Predicate<String> isSum = (string) ->
                string.intern() == "+";

        Predicate<String> isMul = (string) ->
                string.intern() == "*";

        Predicate<String> isDiv = (string) ->
                string.intern() == "/";

        BiFunction<String, Predicate, BiFunction> exitOrRetry = (string, isexit) ->
                isExit.test(string) ? (BiFunction) exit.get() : (BiFunction) retry.get();

        Function<String, BiFunction> choose = (string) ->
                isSum.test(string) ? sum : isMul.test(string) ? mul : isDiv.test(string) ? div : dif;


        BiFunction<String, Predicate, BiFunction> getFunc =
                (string, test) -> test.test(string) ? choose.apply(string) : exitOrRetry.apply(string, isExit);

        return getFunc.apply(scanner.nextLine(), validator);

    }

    public static Integer enterNumber() {

        Function<String, Integer> exitOrRetry = (string) -> {
            if (string.intern() == "exit") {
                System.out.println("Exit");
                return null;
            } else {
                System.out.println("Type number only, please. Type 'exit' to exit");
                return enterNumber();
            }
        };

        Function<String, Integer> getInt = (string) -> {
            if (string.matches("\\b[0-9]+\\b")) {
                return Integer.parseInt(string);
            } else {
                return exitOrRetry.apply(string);
            }
        };

        return getInt.apply(scanner.nextLine());

    }
}
