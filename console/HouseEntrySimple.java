import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class HouseEntrySimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your Birth Year (YYYY): ");
        int year = sc.nextInt();
        System.out.print("Enter your Birth Month (MM): ");
        int month = sc.nextInt();
        System.out.print("Enter your Birth Day (DD): ");
        int day = sc.nextInt();

        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();

        long days = ChronoUnit.DAYS.between(birthDate, today);

        if (days > 0) {
            System.out.println("Your password is " + days);
            System.out.println("Door Opened");
        } else {
            System.out.println("Invalid Date");
        }

        sc.close();
    }
}
