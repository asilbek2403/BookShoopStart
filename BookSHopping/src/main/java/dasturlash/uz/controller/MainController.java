package dasturlash.uz.controller;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Profile;
import dasturlash.uz.service.*;
import dasturlash.uz.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainController {
    // lesson finished Bunda Unga setter lar kerakmas Bemalol U ishlaoladi
@Autowired
    public  BookService bookService ;
@Autowired
    public  AuthService authService;
@Autowired
    public  CategoryService categoryService;
@Autowired
    public InitService initService;
@Autowired
    private HolderService holderService;


//    public MainController(BookService bookService, AuthService authService,
//                          CategoryService categoryService, InitService initService,
//                          HolderService holderService) {
//        this.bookService = bookService;
//        this.authService = authService;
//        this.categoryService = categoryService;
//        this.initService = initService;
//        this.holderService = holderService;
//    }

    //Ishlatilgan Holatdagilarni olamiz

    public void setHolderService(HolderService holderService) {
        this.holderService = holderService;
    }


    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public InitService getInitService() {
        return initService;
    }

    public void setInitService(InitService initService) {
        this.initService = initService;
    }

    public void start() {

        initService.initCreateFile();
        initService.initAdmin();
        initService.initTestStudent();

        boolean loop = true;
        while (loop) {
            showMenu();
            int action = ScannerUtil.getAction();
            switch (action) {
                case 1:
                    bookService.all();
                    break;
                case 2:
                    search();
                    break;
                case 3:
                    byCategory();
                    break;
                case 4:
                    login();
                    break;
                case 5:
                    registration();
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
        System.out.println("*** Main Menu ***");
        System.out.println("1. BookList");
        System.out.println("2. Search");
        System.out.println("3. By category");
        System.out.println("4. Login");
        System.out.println("5. Registration");
        System.out.println("0. Exit");
    }

    public void login() {
        System.out.print("Enter login: ");
        String login = holderService.getScannerText().next();

        System.out.print("scannerText password: ");
        String password = holderService.getScannerText().next();
        //
        authService.login(login, password);
    }

    public void registration() {
        System.out.print("Enter name: ");
        String name = holderService.getScannerText().next();

        System.out.print("Enter surname: ");
        String surname = holderService.getScannerText().next();

        System.out.print("Enter phone: ");
        String phone =holderService.getScannerText().next();

        System.out.print("Enter login: ");
        String login = holderService.getScannerText().next();

        System.out.print("Enter password: ");
        String password =holderService.getScannerText().next();

        Profile profile = new Profile();
        profile.setName(name.trim());
        profile.setSurname(surname.trim());
        profile.setPhone(phone.trim());

        profile.setLogin(login.trim()); // valish
        profile.setPassword(password.trim()); // 222

        authService.registration(profile);
    }

    public void search() {
        System.out.print("Enter query:");
        String query = holderService.getScannerText().next();
        bookService.search(query);
    }

    public void byCategory() {
        categoryService.list();
        System.out.print("Enter category id:");
        Integer categoryId = holderService.getScannerText().nextInt();
        bookService.byCategoryId(categoryId);
    }


}
