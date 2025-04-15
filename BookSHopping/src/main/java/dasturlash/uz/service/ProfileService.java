package dasturlash.uz.service;

import dasturlash.uz.container.ComponentContainer;
import dasturlash.uz.dto.Profile;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.MD5Util;
import dasturlash.uz.util.ProfileValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProfileService {
@Autowired
    public  ProfileRepository profileRepository;
@Autowired
public  CategoryService categoryService;


    public ProfileRepository getProfileRepository() {
        return profileRepository;
    }

    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void addProfile(Profile profile) {
        categoryService.list();
        // check
        if (!ProfileValidationUtil.isValid(profile)) {
            return;
        }
        // check login
        Profile existProfile = profileRepository.getByLogin(profile.getLogin());
        if (existProfile != null) {
            System.out.println("Login exists. Please choose other login. Mazgi");
            return;
        }
        profile.setCreatedDate(LocalDateTime.now());
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setPassword(MD5Util.encode(profile.getPassword()));
        int effectedRow = profileRepository.create(profile);
        if (effectedRow == 1) {
            System.out.println("Profile added.");
        }
    }

    public void studentList() {
        List<Profile> profileList = profileRepository.getAll(ProfileRole.STUDENT); // student
        profileList.forEach(profile -> {
            System.out.println(profile.getDetailAsString());
        });
    }

    public void list() {
        List<Profile> profileList = profileRepository.getAll(ProfileRole.ADMIN, ProfileRole.STAFF); // admin, staff, (!student)
        profileList.forEach(profile -> {
            System.out.println(profile.getDetailAsString());
        });
    }

    public void searchProfile(String query) {
        Profile profile = null;
        if (ProfileValidationUtil.isOnlyNumber(query)) {
            profile = profileRepository.getById(Integer.parseInt(query));
        }

        List<Profile> profileList = profileRepository.search(query, ProfileRole.ADMIN, ProfileRole.STAFF);

        if (profile != null) {
            profileList.add(profile);
        }
        profileList.forEach(p -> {
            System.out.println(p.getDetailAsString());
        });
    }

    public void searchStudent(String query) {
        Profile profile = null;
        if (ProfileValidationUtil.isOnlyNumber(query)) {
            profile = profileRepository.getById(Integer.parseInt(query));
        }

        List<Profile> profileList = profileRepository.search(query, ProfileRole.STUDENT);

        if (profile != null) {
            profileList.add(profile);
        }
        profileList.forEach(p -> {
            System.out.println(p.getStudentDetailAsString());
        });
    }

    public void search(String query, ProfileRole... roles) {
        Profile profile = null;
        if (ProfileValidationUtil.isOnlyNumber(query)) {
            profile = profileRepository.getById(Integer.parseInt(query));
        }

        List<Profile> profileList = profileRepository.search(query, roles);

        if (profile != null) {
            profileList.add(profile);
        }
        profileList.forEach(p -> {
            System.out.println(p.getDetailAsString());
        });
    }

    public void changeStatus(Integer id) {
        Profile profile = profileRepository.getById(id);
        if (profile == null) {
            System.out.println("Profile not found.");
            return;
        }
        int effectedRow;
        if (profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            effectedRow = profileRepository.updateStatus(id, ProfileStatus.BLOCK);
        } else {
            effectedRow =profileRepository.updateStatus(id, ProfileStatus.ACTIVE);
        }

        if (effectedRow == 1) {
            System.out.println("Status changed");
        } else {
            System.out.println("Status did not changed");
        }
    }

    public void changeStudentStatus(Integer id, ProfileStatus status) {
        Profile profile = profileRepository.getById(id);
        if (profile == null) {
            System.out.println("Profile not found.");
            return;
        }
        if (!profile.getRole().equals(ProfileRole.STUDENT)) {
            System.out.println("Only Student id can be used.");
            return;
        }
        int effectedRow = profileRepository.updateStatus(id, status);
        if (effectedRow == 1) {
            System.out.println("Student status changed");
        } else {
            System.out.println("Status did not changed");
        }
    }
}
