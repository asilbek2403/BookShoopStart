package dasturlash.uz.repository;

import dasturlash.uz.dto.Book;
import dasturlash.uz.dto.Category;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;


@Repository
public class CategoryRepository {

    private Integer catId = 1;

    public CategoryRepository(Integer catId) {
        this.catId = catId;
    }

    public CategoryRepository() {

    }

    public Category getByName(String name) {
        try {
            Stream<String> stream = Files.lines(Paths.get("category.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return str[1].equals(name);
            }).map(this::toDTO).findFirst().orElse(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int save(Category category) {
        PrintWriter printWriter = null;
        try {
            category.setId(catId++);
            printWriter = new PrintWriter(new FileWriter("category.txt", true));
            printWriter.println(category.toWrite());
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

    public List<Category> getAll() { //
        try {
            Stream<String> stream = Files.lines(Paths.get("category.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Boolean.parseBoolean(str[3]);
            }).map(this::toDTO).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteById(Integer id) {
        List<Category> list = new ArrayList<>();
        try {
            Stream<String> stream = Files.lines(Paths.get("category.txt"));
            list = stream.map(this::toDTO).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Category category : list) {
            if (category.getId().equals(id)) {
                category.setVisible(false);
                rewrite(list);
                return 1;
            }
        }
        return 0;

    }


    public Category getById(Integer id) {
        try {
            Stream<String> stream = Files.lines(Paths.get("category.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[0]).equals(id);
            }).map(this::toDTO).findFirst().orElse(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void rewrite(List<Category> list) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter("category.txt"));
            for (Category category : list) {
                printWriter.println(category.toWrite());
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

    private Category toDTO(String line) {
        String[] str = line.split("#");
        Category category = new Category();
        category.setId(Integer.valueOf(str[0]));
        category.setName(str[1]);
        category.setCreatedDate(LocalDateTime.parse(str[2]));
        category.setVisible(Boolean.parseBoolean(str[3]));
        return category;
    }

    public Category get(Integer id) {
        try {
            Stream<String> stream = Files.lines(Paths.get("category.txt"));
            return stream.filter(line -> {
                String[] str = line.split("#");
                return Integer.valueOf(str[0]).equals(id) && Boolean.parseBoolean(str[3]);
            }).map(line -> {
                String[] str = line.split("#");
                Category category = new Category();
                category.setId(Integer.valueOf(str[0]));
                category.setName(str[1]);
                return category;
            }).findAny().orElse(null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
