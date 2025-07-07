package itt.lnc.news_aggregation_client.utils;

import itt.lnc.news_aggregation_client.dto.PaginatedResponse;
import itt.lnc.news_aggregation_client.exception.UserCancelledSelectionException;

import java.util.List;
import java.util.function.Function;

public class PaginatedListSelector<T> {

    private final Function<Integer, PaginatedResponse<T>> paginatedResponse;
    private final Function<T, String> formatter;

    public PaginatedListSelector(Function<Integer, PaginatedResponse<T>> paginatedResponse, Function<T, String> formatter) {
        this.paginatedResponse = paginatedResponse;
        this.formatter = formatter;
    }

    public T select(String prompt, boolean allowSelection) {
        int currentPage = 0;

        while (true) {
            PaginatedResponse<T> response = paginatedResponse.apply(currentPage);
            List<T> items = response.getContent();
            if (items.isEmpty()) {
                throw new RuntimeException("No items available.");
            }

            ConsoleUtil.printMessage("Page " + (response.getCurrentPage() + 1) + " of " + response.getTotalPages());

            ConsoleUtil.displayList(items, formatter);

            ConsoleUtil.println("n. Next Page    p. Previous Page");
            ConsoleUtil.println("0. Cancel");

            String input = ConsoleUtil.readLine(prompt);

            switch (input.toLowerCase()) {
                case "n" -> {
                    if (currentPage + 1 < response.getTotalPages()) currentPage++;
                    else ConsoleUtil.printError("Already on last page.");
                }
                case "p" -> {
                    if (currentPage > 0) currentPage--;
                    else ConsoleUtil.printError("Already on first page.");
                }
                case "0" -> {
                    if (allowSelection) throw new UserCancelledSelectionException();
                    return null;
                }
                default -> {
                    if (allowSelection) {
                        try {
                            int index = Integer.parseInt(input);
                            if (index >= 1 && index <= items.size()) {
                                return items.get(index - 1);
                            } else {
                                ConsoleUtil.printError("Invalid index");
                            }
                        } catch (NumberFormatException e) {
                            ConsoleUtil.printError("Invalid input");
                        }
                    } else {
                        ConsoleUtil.printError("Invalid input.");
                    }
                }

            }

        }
    }
}

