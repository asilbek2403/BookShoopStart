package dasturlash.uz.util;

import dasturlash.uz.container.ComponentContainer;

import java.util.InputMismatchException;
import java.util.Scanner;

import static dasturlash.uz.service.HolderService.scannerNumber;

public class ScannerUtil {
    private Scanner scanner ;
    public static int getAction() {
        System.out.print("Enter action: ");
        try {
            return scannerNumber.nextInt();
        } catch (InputMismatchException e) {
            scannerNumber = new Scanner(System.in);
            System.out.println("\nPlease enter number.\n");
            return -1;
        }
    }
}
