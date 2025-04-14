package dasturlash.uz;

import dasturlash.uz.controller.MainController;
import dasturlash.uz.enums.ProfileRole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        // login: adminjon
        // pswd:  12345

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        MainController mainController = (MainController) context.getBean("mainController");
       // MainController mainController = new MainController();
        mainController.start();
        
    }

}