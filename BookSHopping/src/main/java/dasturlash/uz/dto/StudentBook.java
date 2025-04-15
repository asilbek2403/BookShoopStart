package dasturlash.uz.dto;

import dasturlash.uz.enums.StudentBookStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentBook implements Comparable<StudentBook> {
    private Integer id;
    private Integer studentId;
    private Integer bookId;
    private Book book;
    private Profile student;
    private LocalDateTime createdDate;
    private LocalDate deadlineDate;
    private LocalDateTime returnedDate;
    private StudentBookStatus status;
    private Integer takenCount = 0;

    public String toWrite() {
        return id + "#" + studentId + "#" + bookId + "#" + createdDate + "#" + deadlineDate + "#" + status + "#" + returnedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }

    public StudentBookStatus getStatus() {
        return status;
    }

    public void setStatus(StudentBookStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Profile getStudent() {
        return student;
    }

    public void setStudent(Profile student) {
        this.student = student;
    }

    public Integer getTakenCount() {
        return takenCount;
    }

    public void setTakenCount(Integer takenCount) {
        this.takenCount = takenCount;
    }

    @Override
    public int compareTo(StudentBook o) {
        return this.createdDate.compareTo(o.createdDate);
    }
}
