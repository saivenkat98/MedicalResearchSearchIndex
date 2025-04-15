# 🧬 Medical Research Search Index

The **Medical Research Search Index** is a full-stack application built to streamline access to evidence-based research articles published on the [Cochrane Library](https://www.cochranelibrary.com/cdsr/reviews/topics). It enables topic-based search and lazy loading of results, all powered by a custom web scraper and a dynamic frontend.

---

## 🖥️ Backend – Java + Jsoup Web Scraper

The backend component is a standalone Java utility that automates the scraping of Cochrane Library’s topic-wise systematic reviews. Key highlights:

### 🔍 Scraping Logic

- **Library Used**: [Jsoup](https://jsoup.org/) was utilized for its powerful HTML parsing and CSS selector support.
- **Target URL**: The base URL used is `https://www.cochranelibrary.com/cdsr/reviews/topics`, which lists all available medical research topics.
- For each topic:
  - The scraper navigates to the individual topic page.
  - It then extracts essential metadata from each review/article such as:
    - **Title of the Review**
    - **Author(s)**
    - **Publication Date**
    - **Linked Topic Name**

### 🧠 Design Reasoning

- **Separation of Concerns**: Scraper logic is modular, separating the topic URL extraction and review detail scraping.
- **Scalability**: Designed in a way that it can be extended to fetch additional details like abstracts, links, or even full content with minimal refactor.
- **Error Handling**: Graceful handling of missing fields or unexpected page structure changes to ensure robustness.

### 📁 Data Storage

- Extracted data is exported to a **CSV file** using Java I/O mechanisms.
- This flat file format was chosen for ease of integration with the frontend and portability for further analysis or transformation.

---

## 🌐 Frontend – ReactJS + JavaScript

The frontend is a **single-page React application** designed to consume the scraped CSV file and provide an intuitive user interface for browsing and searching through medical research topics.

### ⚙️ Core Features

- **CSV Parsing**: Reads the exported CSV using JavaScript (via libraries like `papaparse` or native logic), converting it into an accessible array of JSON objects for use across the app.

- **Search by Topic**:
  - Users can input/select a **topic name** to filter results.
  - The selected topic is visually highlighted and pinned at the top of the results section.

- **Infinite Scrolling (Lazy Load)**:
  - Initially loads a default of **10 articles** to keep UI responsive.
  - On scrolling, more articles are dynamically appended to the DOM.
  - This is implemented via an **intersection observer** or `window.onscroll` to detect when the user reaches the end of the visible list.

- **State Management**:
  - Built-in React `useState` and `useEffect` hooks were used to manage and update state as users interact with search and scrolling features.
  - Efficient rendering ensures only visible data is rendered to DOM, minimizing performance overhead.

### 🎨 UX Considerations

- **Topic Banner**: When a user searches, the selected topic is highlighted prominently to improve navigation context.
- **Responsive Design**: Layout adapts to different screen sizes for better accessibility on tablets and mobiles.
- **Minimal Dependencies**: Chosen to maintain a lightweight and fast user experience.

---

## 🔍 Tech Stack

| Layer      | Technology         | Purpose                                 |
|------------|--------------------|-----------------------------------------|
| Backend    | Java, Jsoup        | Web scraping of research articles       |
| Frontend   | ReactJS, JavaScript| UI and user interaction                 |
| Storage    | CSV File           | Lightweight, portable data storage      |
| Hosting    | (Optional) GitHub Pages / Netlify | Static frontend deployment |

---

## 🚀 Future Enhancements

- Integrate a **Node.js + Express API** layer to serve real-time data without relying on flat files.
- Add **filters** for date range, author, or publication type.
- Enable **direct article links** to open respective Cochrane review pages.
- Cache scraped data with a timestamp and allow refresh when outdated.

 



https://github.com/user-attachments/assets/ae4bba55-2f8c-42d7-b12c-8fcc75d5d1ea

