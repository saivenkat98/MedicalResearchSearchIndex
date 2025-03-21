import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class FileHandler {
    private static final String FILE_NAME = "cochrane_reviews.txt";

    public static void writeToFile(List<Review> reviews) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            for (Review review : reviews) {
                writer.write(review.formatForFile() + "\n" + "\n");
            }
            System.out.println("Data written to " + FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
