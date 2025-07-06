package itt.lnc.news_aggregation_client.utils;

import itt.lnc.news_aggregation_client.exception.UserCancelledSelectionException;

import java.util.List;
import java.util.function.Function;

public class ListSelector<T> {

    private final List<T> items;
    private final Function<T, String> formatter;

    public ListSelector(List<T> items, Function<T, String> formatter) {
        this.items = items;
        this.formatter = formatter;
    }

    public T select(String prompt) {
        if (items == null || items.isEmpty()) {
            ConsoleUtil.printError("No items available.");
            return null;
        }

        ConsoleUtil.displayList(items, formatter);
        ConsoleUtil.println("0. Cancel");

        while (true) {
            int choice = ConsoleUtil.readInt(prompt);
            if (choice == 0) throw new UserCancelledSelectionException();
            if (choice > 1 || choice <= items.size()) {
                return items.get(choice - 1);
            }
            ConsoleUtil.printError("Invalid selection. Try again.");
        }
    }
}