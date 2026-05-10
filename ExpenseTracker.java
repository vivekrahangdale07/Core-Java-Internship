import java.io.*;
import java.time.LocalDate;
import java.util.*;

/*
 * Console Based Expense Tracker System
 * Features:
 * 1. Add Expense
 * 2. View Expenses
 * 3. Filter Expenses
 * 4. Modify Expense
 * 5. Delete Expense
 * 6. Generate Report
 * 7. File Storage
 */

class Expense implements Serializable {
    int id;
    LocalDate date;
    double amount;
    String category;
    String description;

    public Expense(int id, LocalDate date, double amount,
                   String category, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    @Override
    public String toString() {
        return id + " | " + date + " | ₹" + amount +
                " | " + category + " | " + description;
    }
}

public class ExpenseTracker {

    static ArrayList<Expense> expenses = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "expenses.dat";

    // Load data from file
    static void loadExpenses() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists())
                return;

            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            expenses = (ArrayList<Expense>) ois.readObject();

            ois.close();

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }

    // Save data into file
    static void saveExpenses() {
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            oos.writeObject(expenses);

            oos.close();

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    // Add Expense
    static void addExpense() {

        try {
            System.out.print("Enter Expense ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            System.out.print("Enter Category: ");
            String category = sc.nextLine();

            System.out.print("Enter Description: ");
            String description = sc.nextLine();

            Expense exp = new Expense(id, date, amount,
                    category, description);

            expenses.add(exp);

            saveExpenses();

            System.out.println("Expense Added Successfully.");

        } catch (Exception e) {
            System.out.println("Invalid Input.");
            sc.nextLine();
        }
    }

    // View Expenses
    static void viewExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("No Expenses Found.");
            return;
        }

        System.out.println("\n------ Expense List ------");

        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    // Filter Expenses
    static void filterExpenses() {

        sc.nextLine();

        System.out.print("Enter Category to Filter: ");
        String category = sc.nextLine();

        boolean found = false;

        for (Expense e : expenses) {

            if (e.category.equalsIgnoreCase(category)) {
                System.out.println(e);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No Expense Found.");
        }
    }

    // Modify Expense
    static void modifyExpense() {

        System.out.print("Enter Expense ID to Modify: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean found = false;

        for (Expense e : expenses) {

            if (e.id == id) {

                System.out.print("Enter New Amount: ");
                e.amount = sc.nextDouble();
                sc.nextLine();

                System.out.print("Enter New Category: ");
                e.category = sc.nextLine();

                System.out.print("Enter New Description: ");
                e.description = sc.nextLine();

                saveExpenses();

                System.out.println("Expense Updated Successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Expense ID Not Found.");
        }
    }

    // Delete Expense
    static void deleteExpense() {

        System.out.print("Enter Expense ID to Delete: ");
        int id = sc.nextInt();

        Iterator<Expense> iterator = expenses.iterator();

        boolean found = false;

        while (iterator.hasNext()) {

            Expense e = iterator.next();

            if (e.id == id) {

                iterator.remove();

                saveExpenses();

                System.out.println("Expense Deleted Successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Expense ID Not Found.");
        }
    }

    // Generate Report
    static void generateReport() {

        double total = 0;

        HashMap<String, Double> categoryWise = new HashMap<>();

        for (Expense e : expenses) {

            total += e.amount;

            categoryWise.put(
                    e.category,
                    categoryWise.getOrDefault(e.category, 0.0)
                            + e.amount
            );
        }

        System.out.println("\n------ Expense Report ------");

        System.out.println("Total Expenses: ₹" + total);

        System.out.println("\nCategory Wise Expenses:");

        for (Map.Entry<String, Double> entry :
                categoryWise.entrySet()) {

            System.out.println(entry.getKey()
                    + " : ₹" + entry.getValue());
        }
    }

    public static void main(String[] args) {

        loadExpenses();

        int choice;

        do {

            System.out.println("\n===== Expense Tracker =====");

            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Filter Expenses");
            System.out.println("4. Modify Expense");
            System.out.println("5. Delete Expense");
            System.out.println("6. Generate Report");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addExpense();
                    break;

                case 2:
                    viewExpenses();
                    break;

                case 3:
                    filterExpenses();
                    break;

                case 4:
                    modifyExpense();
                    break;

                case 5:
                    deleteExpense();
                    break;

                case 6:
                    generateReport();
                    break;

                case 7:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 7);
    }
}