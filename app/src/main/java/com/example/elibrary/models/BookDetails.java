package com.example.elibrary.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ACIS SAPKOTA on 9/25/2019.
 */
public class BookDetails {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("book")
    @Expose
    private Book book;
    @SerializedName("similar_books")
    @Expose
    private List<SimilarBook> similarBooks = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<SimilarBook> getSimilarBooks() {
        return similarBooks;
    }

    public void setSimilarBooks(List<SimilarBook> similarBooks) {
        this.similarBooks = similarBooks;
    }

    public class Book {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("author_name")
        @Expose
        private String authorName;
        @SerializedName("publish_by")
        @Expose
        private String publishBy;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("featured_image")
        @Expose
        private String featuredImage;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("comment")
        @Expose
        private List<Object> comment = null;
        @SerializedName("favourite")
        @Expose
        private Integer favourite;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("favourite_count")
        @Expose
        private Integer favouriteCount;
        @SerializedName("category")
        @Expose
        private String category;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getPublishBy() {
            return publishBy;
        }

        public void setPublishBy(String publishBy) {
            this.publishBy = publishBy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFeaturedImage() {
            return featuredImage;
        }

        public void setFeaturedImage(String featuredImage) {
            this.featuredImage = featuredImage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Object> getComment() {
            return comment;
        }

        public void setComment(List<Object> comment) {
            this.comment = comment;
        }

        public Integer getFavourite() {
            return favourite;
        }

        public void setFavourite(Integer favourite) {
            this.favourite = favourite;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public Integer getFavouriteCount() {
            return favouriteCount;
        }

        public void setFavouriteCount(Integer favouriteCount) {
            this.favouriteCount = favouriteCount;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    public class SimilarBook {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("author_name")
        @Expose
        private String authorName;
        @SerializedName("publish_by")
        @Expose
        private String publishBy;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("featured_image")
        @Expose
        private String featuredImage;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("favourite")
        @Expose
        private Integer favourite;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("url")
        @Expose
        private String url;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getPublishBy() {
            return publishBy;
        }

        public void setPublishBy(String publishBy) {
            this.publishBy = publishBy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFeaturedImage() {
            return featuredImage;
        }

        public void setFeaturedImage(String featuredImage) {
            this.featuredImage = featuredImage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getFavourite() {
            return favourite;
        }

        public void setFavourite(Integer favourite) {
            this.favourite = favourite;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}



