package dasturlash.uz.controller;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Book;
import dasturlash.uz.service.BookService;
import dasturlash.uz.service.HolderService;
import dasturlash.uz.service.StudentBookService;
import dasturlash.uz.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
@Controller
public class BookController {

    @Autowired
    public  StudentBookService studentBookService;
    @Autowired
    public  BookService bookService;
    @Autowired
    private HolderService holderService;


    public StudentBookService getStudentBookService() {
        return studentBookService;
    }

    public void setStudentBookService(StudentBookService studentBookService) {
        this.studentBookService = studentBookService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void start() {
        boolean loop = true;
        while (loop) {
            showMenu();
            int action = ScannerUtil.getAction();
            switch (action) {
                case 1:
                    list();
                    break;
                case 2:
                    search();
                    break;
                case 3:
                    add();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    studentBookService.booksOnHand();
                    break;
                case 6:
                    bookHistory();
                    break;
                case 7:
                    studentBookService.bestBooks();
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
        System.out.println("*** Book ***");
        System.out.println("1. Book list");
        System.out.println("2. Search");
        System.out.println("3. Add book");
        System.out.println("4. Remove book");
        System.out.println("5. Book on hand");
        System.out.println("6. Book history");
        System.out.println("7. Best books:");
        System.out.println("0. Exits");
    }


    public void add() {
        System.out.print("Enter title: ");
        String title = holderService.scannerText.next();

        System.out.print("Enter author: ");
        String author = holderService.scannerText.next();

        System.out.print("Enter category id: ");
        Integer categoryId = holderService.scannerNumber.nextInt();

        System.out.print("Enter available day: ");
        Integer availableDay = holderService.scannerNumber.nextInt();

        System.out.print("Enter publishDate (yyyy-MM-dd): "); // 2023-07-15
        String publishDate = holderService.scannerText.next();

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategoryId(categoryId);
        book.setPublishDate(LocalDate.parse(publishDate));
        book.setAvailableDay(availableDay);

        bookService.add(book);
    }

    public void list() {
        System.out.println("--- Book list ---");
        bookService.all();
    }

    public void search() {
        System.out.print("Enter query:");
        String query = holderService.scannerText.next();
        bookService.search(query);
    }

    public void delete() {
        System.out.print("Enter id:");
        Integer bookId = holderService.scannerNumber.nextInt();
        bookService.delete(bookId);
    }

    private void bookHistory() {
        System.out.print("Enter book Id:");
        Integer bookId = holderService.scannerNumber.nextInt();
        studentBookService.bookHistory(bookId);
    }
}
