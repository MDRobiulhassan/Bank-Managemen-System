import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.Scanner;

public class Main {
    Scanner in = new Scanner(System.in);

    void Registration() {
        System.out.println("\t\t\t\t\tSign Up");
        System.out.print("Enter Your Name : ");
        String name = in.nextLine();
        System.out.print("Enter Password : ");
        String password = in.nextLine();

        try {
            Formatter f = new Formatter(new FileOutputStream("UserData.txt", true));

            if (new File("UserData.txt").length() == 0)
                f.format("%s\t\t\t%s\n", "Name", "Password");

            f.format("%s\t\t%s\n", name, password);
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Registered Successfully");
        Operation();
    }

    String Login() {
        System.out.println("\t\t\t\t\tLogin");

        System.out.print("Enter Your Name : ");
        String username = in.nextLine();
        System.out.print("Enter Password : ");
        String password = in.nextLine();

        try (Scanner fileScanner = new Scanner(new File("UserData.txt"))) {
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\t\t");
                if (parts.length == 2 && parts[0].trim().equals(username) && parts[1].trim().equals(password)) {
                    System.out.println("Login successful!");
                    return username;
                }
            }
            System.out.println("Incorrect username or password.Try Again:)");
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found.");
        }
        return null;
    }

    void Operation() {
        int choice = 0;
        String username = Login();

        if (username != null) {
            GenerateAccount(username);
            System.out.print("\t\t\t\t" + username + "'s Account");

            while (choice != 4) {
                System.out.println("\nUser Menu");
                System.out.println("1.Credit\n2.Debit\n3.Account Details\n4.Log Out");
                choice = in.nextInt();
                switch (choice) {
                    case 1:
                        Credit(username);
                        break;
                    case 2:
                        Debit(username);
                        break;
                    case 3:
                        AccountDetails(username);
                        break;
                    default:
                        System.out.println("Logged Out Successfully");
                        break;
                }
            }
        }
    }

    void GenerateAccount(String username) {
        String filename = username + ".txt";
        try {
            Formatter f = new Formatter(new FileOutputStream(filename, true));

            if (new File(filename).length() == 0) {
                f.format("%s\t\t\t%s\t\t\t%s\t\t\t%s\n", "Amount", "Transaction Date", "Transction Type", "Total");
            }

            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Registered Successfully");
    }

    void Credit(String username) {
        String filename = username + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(filename, true))) {

            System.out.print("Enter Amount : ");
            int amount = in.nextInt();
            in.nextLine();
            System.out.print("Enter Transaction Type : ");
            String type = in.nextLine();
            System.out.print("Enter Date (DD-MM-YYYY): ");
            String date = in.nextLine();

            int total = 0;
            try (Scanner fileScanner = new Scanner(new File(filename))) {
                fileScanner.nextLine();
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split("\\s+");
                    if (parts.length > 0 && !parts[0].isEmpty()) {
                        total += Integer.parseInt(parts[0]);
                    }
                }
            }
            total += amount;

            writer.format("%d\t\t\t%s\t\t\t\t\t%s\t\t\t%d\n", amount, type, date, total);
            System.out.println("Money Added Successfully\nYour Current Balance is " + total);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    void Debit(String username) {
        String filename = username + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(filename, true))) {

            System.out.print("Enter Amount : ");
            int amount = in.nextInt();
            in.nextLine();
            System.out.print("Enter Transaction Type : ");
            String type = in.nextLine();
            System.out.print("Enter Date (DD-MM-YYYY): ");
            String date = in.nextLine();

            int total = 0;
            try (Scanner fileScanner = new Scanner(new File(filename))) {
                fileScanner.nextLine();
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split("\\s+");
                    if (parts.length > 0 && !parts[0].isEmpty()) {
                        total += Integer.parseInt(parts[0]);
                    }
                }
            }

            if (amount > total) {
                System.out.println("Not Enough Money.Your Current Balance is " + total);
            } else {
                total -= amount;
                writer.format("%d\t\t\t%s\t\t\t\t\t%s\t\t\t%d\n", -amount, type, date, total);
                System.out.println("Money Withdrawn Successfully. Your Current Balance is " + total);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    void AccountDetails(String username) {
        String filename = username + ".txt";
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            if (fileScanner.hasNextLine()) {
                String header = fileScanner.nextLine();
                System.out.println(header);
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.print(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        System.out.println("\t\t\t\tWelcome To Swiss Bank\n");
        // System.out.println("Menu");
        System.out.println("1.Create Account\n2.Login");

        Main ob = new Main();
        int choice = ob.in.nextInt();
        ob.in.nextLine();
        if (choice == 1)
            ob.Registration();
        else
            ob.Operation();

        ob.in.close();
    }
}
