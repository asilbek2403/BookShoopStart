package dasturlash.uz.service;

import dasturlash.uz.dto.Profile;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.util.MD5Util;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


@Service
public class InitService {

    public void initCreateFile() {
        try {
            File book = new File("book.txt");
            File category = new File("category.txt");
            File profile = new File("profile.txt");
            File studentBook = new File("student_book.txt");
            if (!book.exists()) {
                boolean t = book.createNewFile();
            }
            if (!category.exists()) {
                boolean t = category.createNewFile();
            }
            if (!profile.exists()) {
                boolean t = profile.createNewFile();
            }
            if (!studentBook.exists()) {
                boolean t = studentBook.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void initAdmin() {
        String login = "adminjon";
        ProfileRepository profileRepository = new ProfileRepository();
        Profile profile = profileRepository.getByLogin("adminjon");
        if (profile == null) {
            // create
            Profile admin = new Profile();
            admin.setName("Admin");
            admin.setSurname("Adminov");
            admin.setLogin(login);
            admin.setPassword(MD5Util.encode("12345"));
            admin.setPhone("998911234567");
            admin.setStatus(ProfileStatus.ACTIVE);
            admin.setRole(ProfileRole.ADMIN);
            admin.setCreatedDate(LocalDateTime.now());

            profileRepository.create(admin);//create
        }
    }

    public void initTestStudent() {
        String login = "testStudent";
        ProfileRepository profileRepository = new ProfileRepository();
        Profile profile = profileRepository.getByLogin("testStudent");
        if (profile == null) {
            // create
            Profile student = new Profile();
            student.setName("StudentJon");
            student.setSurname("Studentov");
            student.setLogin(login);
            student.setPassword(MD5Util.encode("12345"));
            student.setPhone("998911234567");
            student.setStatus(ProfileStatus.ACTIVE);
            student.setRole(ProfileRole.STUDENT);
            student.setCreatedDate(LocalDateTime.now());
            profileRepository.create(student);//create
        }
    }

}
