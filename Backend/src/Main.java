import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting TopicScraper...");  // Debug log
        TopicScraper topicScraper = new TopicScraper();
        topicScraper.scrapeAllTopics();
        System.out.println("Finished scraping topics.");
    }
}