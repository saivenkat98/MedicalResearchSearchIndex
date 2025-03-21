import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TopicScraper {
    private static final String TOPICS_URL = "https://www.cochranelibrary.com/cdsr/reviews/topics";

    public static void scrapeAllTopics() {
        try {
            List<String> topicUrls = fetchTopicUrls();

            for (String topicUrl : topicUrls) {
                List<Review> reviews = Scraper.fetchReviews(topicUrl);
                FileHandler.writeToFile(reviews);
            }
        } catch (IOException e) {
            System.out.println("Error fetching topic URLs: " + e.getMessage());
        }
    }

    private static List<String> fetchTopicUrls() throws IOException {
        List<String> topicUrls = new ArrayList<>();
        Document doc = Jsoup.connect(TOPICS_URL).get();
        Elements topicLinks = doc.select(".browse-by-term-list .browse-by-list-item"); // Adjust if needed
        for (Element item : topicLinks) {
            // Find the anchor element within each list item
            Element link = item.selectFirst("a");
            if (link != null) {
                String topicUrl = link.absUrl("href");
                if (!topicUrl.isEmpty()) {
                    topicUrls.add(topicUrl);
                }
            }
        }
        return topicUrls;
    }
}
