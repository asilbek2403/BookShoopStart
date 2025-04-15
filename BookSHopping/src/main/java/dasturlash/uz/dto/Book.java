package dasturlash.uz.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Book implements Comparable<Book>{
    private Integer id;
    private String title;
    private String author;
    private Integer categoryId;
    private LocalDate publishDate;
    private Integer availableDay;
    private Boolean visible;
    private LocalDateTime createdDate;

    public Category category;

    public String toWrite() {
        return id + "#" + title + "#" + author + "#" + publishDate + "#" + availableDay + "#" + createdDate + "#" +categoryId+"#"+ visible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(Integer availableDay) {
        this.availableDay = availableDay;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public int compareTo(Book o) {
        return this.id-o.id;
    }
}
