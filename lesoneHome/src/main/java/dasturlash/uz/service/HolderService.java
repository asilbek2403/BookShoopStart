package dasturlash.uz.service;

import dasturlash.uz.dto.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class HolderService {


    public static Scanner scannerText ;
    public static Scanner scannerNumber ;
    public Profile currentProfile;



    public HolderService(){
        scannerText= new Scanner(System.in);
        scannerNumber = new Scanner(System.in);


    }

    public  Scanner getScannerText() {
        return scannerText;
    }

    public  void setScannerText(Scanner scannerText) {
        HolderService.scannerText = scannerText;
    }

    public static Scanner getScannerNumber() {
        return scannerNumber;
    }

    public  void setScannerNumber(Scanner scannerNumber) {
        HolderService.scannerNumber = scannerNumber;
    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }
}
