package dasturlash.uz.util;

import dasturlash.uz.dto.Profile;

public class ProfileValidationUtil {
    public static boolean isValid(Profile profile) {
        if (profile.getName() == null || profile.getName().isBlank() || profile.getName().length() < 2) {
            System.out.println("Name is wrong.");
            return false;
        }
        if (profile.getSurname() == null || profile.getSurname().isBlank() || profile.getSurname().length() < 2) {
            System.out.println("Surname is wrong.");
            return false;
        }
        if (profile.getPassword() == null || profile.getPassword().isBlank() || profile.getPassword().length() < 5) {
            System.out.println("Password is wrong.");
            return false;
        }
        if (profile.getLogin() == null || profile.getLogin().isBlank() || profile.getLogin().length() < 3) {
            System.out.println("Password is wrong.");
            return false;
        }
        // 998 91 555 66 77
        if (profile.getPhone() == null
                || profile.getPhone().isBlank()
                || profile.getPhone().length() != 12
                || !profile.getPhone().startsWith("998")
                || !isOnlyNumber(profile.getPhone())) {
            System.out.println("Phone is wrong.");
            return false;
        }
        return true;
    }

    public static boolean isOnlyNumber(String input) {
        char[] arr = input.toCharArray();
        for (char c : arr) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}
