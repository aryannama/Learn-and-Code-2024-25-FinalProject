package itt.lnc.news_aggregation_client.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;


public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public static int readInt(String prompt) {
        String input = readLine(prompt);
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a number.");
        }
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void printMessage(String message) {
        System.out.println("\n--- " + message + " ---");
    }

    public static void printError(String message) {
        System.out.println("\n!!! " + message + " !!!");
    }

    public static void printHeader(String headerText) {
        String border = "=".repeat(headerText.length() + 6);
        System.out.println("\n" + border);
        System.out.println("|| " + headerText.toUpperCase() + " ||");
        System.out.println(border + "\n");
    }

    public static void printSeparatorLine() {
        System.out.println("----------------------------------------------------");
    }

    public static void displayMenu(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, options.get(i));
        }
    }

    public static int promptMenu(List<String> options) {
        displayMenu(options);
        printSeparatorLine();
        return readInt("Enter your choice");
    }

    public static <T> void displayList(List<T> items, Function<T, String> formatter) {
        if (items.isEmpty()) {
            println("No items to display.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, formatter.apply(items.get(i)));
        }
    }

    public static LocalDate readDate(String label) {
        while (true) {
            String input = ConsoleUtil.readLine(label + " (YYYY-MM-DD or leave blank): ");
            if (input.isBlank()) {
                return null;
            }
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                ConsoleUtil.printError("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

}
