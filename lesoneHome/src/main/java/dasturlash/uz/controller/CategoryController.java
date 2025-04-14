package dasturlash.uz.controller;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Category;
import dasturlash.uz.service.CategoryService;
import dasturlash.uz.service.HolderService;
import dasturlash.uz.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryController {
@Autowired
    public  CategoryService categoryService;
    @Autowired
    private HolderService holderService;


    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void start() {
        boolean loop = true;
        while (loop) {
            showMenu();
            int action = ScannerUtil.getAction();
            switch (action) {
                case 1:
                    categoryService.list();
                    break;
                case 2:
                    deleteCategory();
                    break;
                case 3:
                    addCategory();
                    break;
                case 0:
                    loop = false;
                    break;
                default:
                    System.out.println("Mazgi bu hatoku.");
            }

        }

    }

    public void showMenu() {
        System.out.println("*** Category ***");
        System.out.println("1. Category list");
        System.out.println("2. Delete category");
        System.out.println("3. Add category");
        System.out.println("0. Exit");
    }

    public void addCategory() {
        System.out.print("Enter name: ");
        String name = holderService.scannerText.next();
        //
        Category category = new Category();
        category.setName(name);

        categoryService.create(category);
    }

    public void deleteCategory() {
        System.out.print("Enter id: ");
        Integer id = holderService.scannerNumber.nextInt();

        categoryService.delete(id);
    }
}
