package itt.lnc.news_aggregation_client.handler;

import itt.lnc.news_aggregation_client.dto.BlockedKeywordDto;
import itt.lnc.news_aggregation_client.service.BlockedKeywordService;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BlockedKeywordHandler {

    private final BlockedKeywordService blockedKeywordService;

    public void listBlockedKeywords() {
        List<BlockedKeywordDto> blockedKeywords = blockedKeywordService.getAllBlockedKeywords();
        ConsoleUtil.println("List of Blocked Keywords:");
        ConsoleUtil.displayList(blockedKeywords, keyword -> keyword.getKeyword() + " (ID: " + keyword.getId() + ")");
    }

    public void blockKeyword() {
        String keyword = ConsoleUtil.readLine("Enter keyword to block:");
        blockedKeywordService.addBlockedKeyword(keyword);
        ConsoleUtil.printMessage("Blocked keyword added");
    }

    public void unblockKeyword() {
        Long id = (long) ConsoleUtil.readInt("Enter ID of the keyword to unblock:");
        blockedKeywordService.removeBlockedKeyword(id);
        ConsoleUtil.printMessage("Blocked keyword removed");
    }
}
