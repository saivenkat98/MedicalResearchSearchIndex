class Review {
    private String url;
    private String category;
    private String title;
    private String authors;
    private String publicationDate;

    public Review(String url, String category, String title, String authors, String publicationDate) {
        this.url = url;
        this.category = category;
        this.title = title;
        this.authors = authors;
        this.publicationDate = publicationDate;
    }

    public String formatForFile() {
        return String.join("|", url, category, title, authors, publicationDate);
    }
}