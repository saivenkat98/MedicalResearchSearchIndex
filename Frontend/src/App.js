import React, { useState, useEffect, useRef } from "react";
import './App.css';

const App = () => {
  const [reviews, setReviews] = useState([]);
  const [filteredReviews, setFilteredReviews] = useState([]);
  const [topics, setTopics] = useState([]);
  const [search, setSearch] = useState("");
  const [searchCount, setSearchCount] = useState(0);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const [visibleCount, setVisibleCount] = useState(10);
  const loadMoreRef = useRef(null);

  useEffect(() => {
    fetch("/cochrane_reviews.json")
      .then((res) => res.json())
      .then((data) => {
        setReviews(data);
        setFilteredReviews(data);
        setTopics([...new Set(data.flat().map((review) => review.topic))]);
      });
  }, []);
  const handleSearch = (e) => {
    const query = e.target.value;
    setSearch(query);
    setShowSuggestions(query.length > 0);
    setSearchCount(0)
  };

  const selectTopic = (topic) => {
    setSearch(topic);
    setFilteredReviews(reviews.flat().filter((review) => review.topic === topic));
    setSearchCount(filteredReviews.flat().length);
    setShowSuggestions(false);
    setVisibleCount(10);
  };

  const clearTopic = () => {
    setSearch("")
    setFilteredReviews(reviews)
    setSearchCount(0)
  }

  const loadMore = () => {
    setVisibleCount((prev) => prev + 10);
  };

  useEffect(() => {
    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          loadMore(); // Load more reviews when reaching the bottom
        }
      },
      {
        rootMargin: '0px',
        threshold: 1.0,
      }
    );

    if (loadMoreRef.current) {
      observer.observe(loadMoreRef.current); // Observe the "load more" trigger element
    }

    return () => {
      if (loadMoreRef.current) {
        observer.unobserve(loadMoreRef.current); // Clean up the observer on unmount
      }
    };
  }, []);

  return (
    <div className="container">
      <div className="search-box">
        <input
          type="text"
          value={search}
          onChange={handleSearch}
          placeholder="Search by Topic..."
          style={{ borderColor: "#962d91" }}
        />
        {showSuggestions && (
          <ul className="suggestions">
            {topics
              .filter((topic) => topic.toLowerCase().includes(search.toLowerCase()))
              .map((topic) => (
                <li key={topic} onClick={() => selectTopic(topic)}>
                  {topic}
                </li>
              ))}
          </ul>
        )}
      </div>

      {searchCount>0 && (
      <>
        <div className="selected-topics">
          <h4>Topics: 
            <span className="topic-tag" onClick={() => clearTopic()}>
              {search}
            </span>
          </h4>
        </div>
        
        {/* Review count display */}
        <div>
          <p>
            <span className="review-count">{searchCount}</span> Cochrane Reviews matching{" "}
            <span className="review-count">{search}</span> in Cochrane Topic
          </p>
        </div>
      </>
    )}

      <br></br>
      <br></br>
      <ul className="review-list">
        {filteredReviews.length > 0 ? (
          filteredReviews.flat().slice(0, visibleCount).map((review, index) => (
            <li key={index}>
              <a href={review.url} target="_blank" rel="noopener noreferrer">
                {review.title}
              </a>
              <p>{review.author} - <span style={{ color: "#962d91" }}>{review.date}</span></p>
            </li>
          ))
        ) : (
          <p>No reviews found. Try searching for a different topic.</p>
        )}
      </ul>
      <div ref={loadMoreRef} className="load-more-trigger" style={{ height: '20px' }}></div>
    </div>
  );
};

export default App;