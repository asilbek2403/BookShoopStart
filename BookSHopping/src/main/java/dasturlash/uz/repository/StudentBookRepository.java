package dasturlash.uz.repository;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Book;
import dasturlash.uz.dto.Category;
import dasturlash.uz.dto.Profile;
import dasturlash.uz.dto.StudentBook;
import dasturlash.uz.enums.StudentBookStatus;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;


@Repository
public class StudentBookRepository {



    public  BookRepository bookRepository;
    public  ProfileRepository profileRepository;


    public ProfileRepository getProfileRepository() {
        return profileRepository;
    }

    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private Integer serialId = 1;

    public int save(StudentBook studentBook) {
        PrintWriter printWriter = null;
        try {
            studentBook.setId(serialId++);
            printWriter = new PrintWriter(new FileWriter("student_book.txt", true));
            printWriter.println(studentBook.toWrite());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
        return 1;
    }

    public List<StudentBook> studentBookOnHand(Integer sId, StudentBookStatus status) {
        try {
            Stream<String> stream = Files.lines(Paths.get("student_book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[1]).equals(sId)
                        && str[5].equals(status.name());
            }).map(line -> {
                String[] str = line.split("#");
                StudentBook studentBook = new StudentBook();
                studentBook.setId(Integer.valueOf(str[0]));
                studentBook.setCreatedDate(LocalDateTime.parse(str[3]));
                if (str[6] != null) studentBook.setReturnedDate(LocalDateTime.parse(str[6]));
                studentBook.setBook(bookRepository.get(Integer.valueOf(str[2])));
                return studentBook;
            }).sorted().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StudentBook getStudentBook(Integer sId, Integer bId) {
        try {
            Stream<String> stream = Files.lines(Paths.get("student_book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[1]).equals(sId)
                        && Integer.valueOf(str[2]).equals(bId)
                        && str[5].equals("TAKEN");
            }).map(line -> {
                String[] str = line.split("#");
                StudentBook studentBook = new StudentBook();
                studentBook.setId(Integer.valueOf(str[0]));
                studentBook.setStudentId(Integer.valueOf(str[1]));
                studentBook.setBookId(Integer.valueOf(str[2]));
                studentBook.setCreatedDate(LocalDateTime.parse(str[3]));
                studentBook.setDeadlineDate(LocalDate.parse(str[4]));
                studentBook.setStatus(StudentBookStatus.valueOf(str[5]));
                return studentBook;
            }).findAny().orElse(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int returnBook(Integer sbId) {
        List<StudentBook> list = new ArrayList<>();
        try {
            Stream<String> stream = Files.lines(Paths.get("student_book.txt"));
            list = stream.map(line -> {
                String[] str = line.split("#");
                StudentBook studentBook = new StudentBook();
                studentBook.setId(Integer.valueOf(str[0]));
                studentBook.setStudentId(Integer.valueOf(str[1]));
                studentBook.setBookId(Integer.valueOf(str[2]));
                studentBook.setCreatedDate(LocalDateTime.parse(str[3]));
                studentBook.setDeadlineDate(LocalDate.parse(str[4]));
                studentBook.setStatus(StudentBookStatus.valueOf(str[5]));
                if (str[6] != null) studentBook.setReturnedDate(LocalDateTime.parse(str[6]));
                return studentBook;
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (StudentBook studentBook : list) {
            if (studentBook.getStudentId().equals(sbId)
                    && studentBook.getStatus().equals(StudentBookStatus.TAKEN)) {
                studentBook.setStatus(StudentBookStatus.RETURNED);
                studentBook.setReturnedDate(LocalDateTime.now());
                rewrite(list);
                return 1;
            }
        }
        return 0;
    }

    public int returnBook(Integer sId, Integer bId) {
        List<StudentBook> list = new ArrayList<>();
        try {
            Stream<String> stream = Files.lines(Paths.get("student_book.txt"));
            list = stream.map(line -> {
                String[] str = line.split("#");
                StudentBook studentBook = new StudentBook();
                studentBook.setId(Integer.valueOf(str[0]));
                studentBook.setStudentId(Integer.valueOf(str[1]));
                studentBook.setBookId(Integer.valueOf(str[2]));
                studentBook.setCreatedDate(LocalDateTime.parse(str[3]));
                studentBook.setDeadlineDate(LocalDate.parse(str[4]));
                studentBook.setStatus(StudentBookStatus.valueOf(str[5]));
                if (str[6] != null) studentBook.setReturnedDate(LocalDateTime.parse(str[6]));
                return studentBook;
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (StudentBook studentBook : list) {
            if (studentBook.getStudentId().equals(sId)
                    && studentBook.getBookId().equals(bId)
                    && studentBook.getStatus().equals(StudentBookStatus.TAKEN)) {
                studentBook.setStatus(StudentBookStatus.RETURNED);
                studentBook.setReturnedDate(LocalDateTime.now());
                rewrite(list);
                return 1;
            }
        }
        return 0;
    }


    public List<StudentBook> booksOnHand() {
        try {
            Stream<String> stream = Files.lines(Paths.get("student_book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return str[5].equals("TAKEN");
            }).map(line -> {
                String[] str = line.split("#");
                StudentBook studentBook = new StudentBook();
                studentBook.setCreatedDate(LocalDateTime.parse(str[3]));
                studentBook.setDeadlineDate(LocalDate.parse(str[4]));
                studentBook.setBook(bookRepository.get(Integer.valueOf(str[2])));
                studentBook.setStudent(profileRepository.get(Integer.valueOf(str[1])));
                return studentBook;
            }).sorted().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<StudentBook> bookHistory(Integer bId) {
        try {
            Stream<String> stream = Files.lines(Paths.get("student_book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[2]).equals(bId);
            }).map(line -> {
                String[] str = line.split("#");
                StudentBook studentBook = new StudentBook();
                studentBook.setCreatedDate(LocalDateTime.parse(str[3]));
                if (str[6] != null) studentBook.setReturnedDate(LocalDateTime.parse(str[6]));
                studentBook.setStudent(profileRepository.get(Integer.valueOf(str[1])));
                return studentBook;
            }).sorted().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentBook> bestBooks() {
        List<StudentBook> bookList = new LinkedList<>();
        try {
            Stream<String> stream = Files.lines(Paths.get("student_book.txt"));
            bookList = stream.map(line -> {
                String[] str = line.split("#");
                StudentBook studentBook = new StudentBook();
                studentBook.setBook(bookRepository.get(Integer.valueOf(str[2])));
                return studentBook;
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, StudentBook> map = new HashMap<>();
        bookList.forEach(studentBook -> map.putIfAbsent(studentBook.getBook().getId(), studentBook));
        for (StudentBook b : bookList) {
            StudentBook book = map.get(b.getBook().getId());
            if (book != null) book.setTakenCount(book.getTakenCount() + 1);
        }
        List<StudentBook> result = new LinkedList<>(map.values());
        Comparator<StudentBook> sort = new Comparator<StudentBook>() {
            @Override
            public int compare(StudentBook o1, StudentBook o2) {
                return o1.getTakenCount() - o2.getTakenCount();
            }
        };
        result.sort(sort);
        return result;
    }

    private void rewrite(List<StudentBook> list) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter("student_book.txt"));
            for (StudentBook studentBook : list) {
                printWriter.println(studentBook.toWrite());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

}
