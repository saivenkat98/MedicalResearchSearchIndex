import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

class Scraper {
    private static final String BASE_URL = "https://www.cochranelibrary.com/en/search?p_p_id=scolarissearchresultsportlet_WAR_scolarissearchresults&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_scolarissearchresultsportlet_WAR_scolarissearchresults_displayText=Child+health&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=Child+health&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryField=topic_id&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=13&_scolarissearchresultsportlet_WAR_scolarissearchresults_orderBy=displayDate-true&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetDisplayName=Child+health&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryTerm=z1209270506397401105880747733814&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetCategory=Topics";

    public static List<Review> fetchReviews(String topicUrl) throws IOException {
        List<Review> reviews = new ArrayList<>();

        Document doc = Jsoup.connect(topicUrl).get();

        Element searchResultText = doc.selectFirst("span#searchResultText");
        String searchtext = searchResultText.text();
        String category = searchtext.substring(0, searchtext.indexOf(" in"));

        Elements links = doc.select(".aui .search-results-item-body .result-title");

        for (Element link : links) {

            Element anchorTag = link.selectFirst("a"); // Selects the <a> inside <h3>
            if (anchorTag != null) {
                String url = anchorTag.absUrl("href"); // Now extracts the absolute URL correctly
                String title = anchorTag.text(); // Extracts the title
            

            // Visit the review page to extract more details
            Document reviewPage = Jsoup.connect(url).get();
            
            String authors = reviewPage.select(".authors").text(); // Modify based on actual structure
            String dateString = reviewPage.select("span.publish-date").text(); //sample date text Version published: 11 March 2025

            String date = dateString.split(":")[1].trim(); // after modification date = 11 March 2025
            
            try {
            // Parse the date using SimpleDateFormat
            SimpleDateFormat inputFormat = new SimpleDateFormat("d MMMM yyyy");
            Date dateparsed = inputFormat.parse(date);
            
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");// coverted to 2025-03-11
            String publicationDate = outputFormat.format(dateparsed); //final string value

            reviews.add(new Review(url, category, title, authors, publicationDate));

        } catch (Exception e) {
            e.printStackTrace();
        }
            }
        }
        return reviews;
    }
}
