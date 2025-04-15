package dasturlash.uz.service;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Category;
import dasturlash.uz.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {
@Autowired
    public  CategoryRepository categoryRepository;

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void create(Category category) {
        CategoryService categoryService = new CategoryService();

        if (!check(category)) {
            System.out.println("Mazgi Category nomida hatolik bor.");
            return;
        }
        Category exists = categoryRepository.getByName(category.getName());
        if (exists != null) {
            System.out.println("category name already exists");
            return;
        }

        category.setCreatedDate(LocalDateTime.now());
        category.setVisible(true);
        int effectedRows =categoryRepository.save(category);
        if (effectedRows != 0) {
            System.out.println("Category is created");
        }
    }

    public boolean check(Category category) {
        if (category.getName() == null || category.getName().isBlank() || category.getName().length() <= 3) {
            return false;
        }
        return true;
    }

    public void list() {
        List<Category> categoryList = categoryRepository.getAll();
        for (Category category : categoryList) {
            System.out.println(category.getId() + " " + category.getName() + " " + category.getCreatedDate());
        }
    }

    public void delete(Integer id) {
        int effectedRows = categoryRepository.deleteById(id);
        if (effectedRows != 0) {
            System.out.println("Category deleted");
        } else {
            System.out.println("Category not deleted");
        }
    }
}
