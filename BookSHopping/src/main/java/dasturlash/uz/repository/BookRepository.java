package dasturlash.uz.repository;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Book;
import dasturlash.uz.dto.Category;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class BookRepository {

    public  CategoryRepository categoryRepository;

    private Integer bookId = 1;

    public BookRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

//    public Integer getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(Integer bookId) {
//        this.bookId = bookId;
//    }

    public int save(Book book) {
        PrintWriter printWriter = null;
        try {
            book.setId(bookId++);
            printWriter = new PrintWriter(new FileWriter("book.txt", true));
            printWriter.println(book.toWrite());
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

    public List<Book> getAll() {
        try {
            Stream<String> stream = Files.lines(Paths.get("book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Boolean.parseBoolean(str[7]);
            }).map(this::toDTO).sorted().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> search(String query) {
        try {
            Stream<String> stream = Files.lines(Paths.get("book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return str[1].toLowerCase().contains(query.toLowerCase())
                        || str[2].toLowerCase().contains(query.toLowerCase())
                        && Boolean.parseBoolean(str[7]);
            }).map(line -> {
                String[] str = line.split("#");
                Book book = toDTO(line);
                book.setCategoryId(Integer.valueOf(str[6]));
                return book;
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int delete(Integer bookId) {
        List<Book> list = new ArrayList<>();
        try {
            Stream<String> stream = Files.lines(Paths.get("book.txt"));
            list = stream.map(line -> {
                String[] str = line.split("#");
                Book book = toDTO(line);
                book.setCategoryId(Integer.valueOf(str[6]));
                book.setVisible(Boolean.valueOf(str[7]));
                return book;
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (Book book : list) {
            if (book.getId().equals(bookId)) {
                book.setVisible(false);
                rewrite(list);
                return 1;
            }
        }

        return 0;
    }

    public Book getById(Integer id) {
        try {
            Stream<String> stream = Files.lines(Paths.get("book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[0]).equals(id);
            }).map(line -> {
                String[] str = line.split("#");
                Book book = toDTO(line);
                book.setCategoryId(Integer.valueOf(str[6]));
                return book;
            }).findAny().orElse(null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> getAllByCategoryId(Integer categoryId) {
        try {
            Stream<String> stream = Files.lines(Paths.get("book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[6]).equals(categoryId)
                        && Boolean.parseBoolean(str[7]);
            }).map(line -> {
                String[] str = line.split("#");
                Book book = new Book();
                book.setId(Integer.valueOf(str[0]));
                book.setTitle(str[1]);
                book.setAuthor(str[2]);
                book.setCategory(categoryRepository.get(Integer.valueOf(str[6])));
                return book;
            }).sorted().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void rewrite(List<Book> list) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter("book.txt"));
            for (Book book : list) {
                printWriter.println(book.toWrite());
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

    private Book toDTO(String line) {
        String[] str = line.split("#");
        Book book = new Book();
        book.setId(Integer.valueOf(str[0]));
        book.setTitle(str[1]);
        book.setAuthor(str[2]);
        book.setPublishDate(LocalDate.parse(str[3]));
        book.setAvailableDay(Integer.valueOf(str[4]));
        book.setCreatedDate(LocalDateTime.parse(str[5]));
        book.setCategory(categoryRepository.get(Integer.valueOf(str[6])));
        return book;
    }

    public Book get(Integer id) {
        try {
            Stream<String> stream = Files.lines(Paths.get("book.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[0]).equals(id) && Boolean.parseBoolean(str[7]);
            }).map(line -> {
                String[] str = line.split("#");
                Book book = new Book();
                book.setId(Integer.valueOf(str[0]));
                book.setTitle(str[1]);
                book.setAuthor(str[2]);
                book.setAvailableDay(Integer.valueOf(str[4]));
                book.setCategory(categoryRepository.get(Integer.valueOf(str[6])));
                return book;
            }).findAny().orElse(null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
