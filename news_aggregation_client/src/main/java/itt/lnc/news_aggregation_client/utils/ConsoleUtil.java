package itt.lnc.news_aggregation_client.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;


public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    public static String readLine(String prompt) {
        System.out.print(YELLOW + prompt + ": ");
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
        System.out.println(GREEN + "\n→ " + message + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "\n✖ " + message + RESET);
    }

    public static void printHeader(String headerText) {
        String border = "=".repeat(headerText.length() + 8);
        System.out.println(BOLD + CYAN + "\n" + border);
        System.out.println("|| " + headerText.toUpperCase() + " ||");
        System.out.println(border + RESET);
    }

    public static void printSeparatorLine() {
        System.out.println(CYAN + "--------------------------------------------------" + RESET);
    }

    public static void displayMenu(List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%s%2d. %s%s%n", CYAN, i + 1, options.get(i), RESET);
        }
    }

    public static int promptMenu(List<String> options) {
        printSeparatorLine();
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
            System.out.printf("%s%2d. %s%s%n", CYAN, i + 1, formatter.apply(items.get(i)), RESET);
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
