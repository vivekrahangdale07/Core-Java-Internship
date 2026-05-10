import java.util.*;

/**
 * Employee class stores employee details and attendance/leave records.
 */
class Employee {
    int empId; // Unique employee ID
    String name; // Employee name
    String designation; // Employee designation
    String department; // Department name
    String contact; // Contact number/email
    HashMap<String, String> attendance = new HashMap<>(); // Attendance record: date -> status
    ArrayList<Leave> leaveRecords = new ArrayList<>(); // Leave request list

    // Constructor to initialize employee object
    Employee(int empId, String name, String designation, String department, String contact) {
        this.empId = empId;
        this.name = name;
        this.designation = designation;
        this.department = department;
        this.contact = contact;
    }

    // Display employee details
    void display() {
        System.out.println("ID: " + empId + ", Name: " + name + ", Designation: " + designation +
                ", Dept: " + department + ", Contact: " + contact);
    }
}

/**
 * Leave class stores leave request details for an employee.
 */
class Leave {
    String type; // Type of leave (e.g., Sick, Casual, Annual)
    String startDate; // Leave start date
    String endDate; // Leave end date
    String status; // Leave status (Pending/Approved/Rejected)

    // Constructor to initialize leave request
    Leave(String type, String startDate, String endDate) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "Pending"; // Default status
    }
}

/**
 * HRMS main class for managing employee info, attendance, leaves.
 */
public class HRMS {
    static Scanner sc = new Scanner(System.in);
    static HashMap<Integer, Employee> employees = new HashMap<>(); // Stores employees by ID
    static int employeeCounter = 1001; // Auto-generated employee ID

    public static void main(String[] args) {
        while (true) {
            // Main menu
            System.out.println("\n--- HRMS Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Mark Attendance");
            System.out.println("6. Manage Leaves");
            System.out.println("7. Search Employee");
            System.out.println("8. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> updateEmployee();
                case 4 -> deleteEmployee();
                case 5 -> markAttendance();
                case 6 -> manageLeaves();
                case 7 -> searchEmployee();
                case 8 -> {
                    System.out.println("Exiting HRMS...");
                    return; // Exit the system
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Add a new employee to the system.
     */
    static void addEmployee() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter designation: ");
        String designation = sc.nextLine();
        System.out.print("Enter department: ");
        String department = sc.nextLine();
        System.out.print("Enter contact: ");
        String contact = sc.nextLine();

        // Create new employee with auto-generated ID
        Employee emp = new Employee(employeeCounter++, name, designation, department, contact);
        employees.put(emp.empId, emp);

        System.out.println("Employee added successfully with ID: " + emp.empId);
    }

    /**
     * View all employees in the system.
     */
    static void viewEmployees() {
        System.out.println("\n--- Employee List ---");
        for (Employee emp : employees.values()) {
            emp.display();
        }
    }

    /**
     * Update details of an existing employee.
     */
    static void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (employees.containsKey(id)) {
            Employee emp = employees.get(id);
            System.out.print("Enter new name (" + emp.name + "): ");
            emp.name = sc.nextLine();
            System.out.print("Enter new designation (" + emp.designation + "): ");
            emp.designation = sc.nextLine();
            System.out.print("Enter new department (" + emp.department + "): ");
            emp.department = sc.nextLine();
            System.out.print("Enter new contact (" + emp.contact + "): ");
            emp.contact = sc.nextLine();

            System.out.println("Employee updated successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    /**
     * Delete an employee from the system.
     */
    static void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (employees.containsKey(id)) {
            employees.remove(id);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    /**
     * Mark attendance for an employee.
     */
    static void markAttendance() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (employees.containsKey(id)) {
            Employee emp = employees.get(id);
            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = sc.nextLine();
            System.out.print("Enter status (Present/Absent/Leave): ");
            String status = sc.nextLine();

            emp.attendance.put(date, status);
            System.out.println("Attendance marked successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    /**
     * Add leave request for an employee.
     */
    static void manageLeaves() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (employees.containsKey(id)) {
            Employee emp = employees.get(id);
            System.out.print("Enter leave type: ");
            String type = sc.nextLine();
            System.out.print("Enter start date (YYYY-MM-DD): ");
            String start = sc.nextLine();
            System.out.print("Enter end date (YYYY-MM-DD): ");
            String end = sc.nextLine();

            emp.leaveRecords.add(new Leave(type, start, end));
            System.out.println("Leave request added successfully.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    /**
     * Search employee by name or ID.
     */
    static void searchEmployee() {
        System.out.print("Enter name or ID: ");
        String input = sc.nextLine();

        System.out.println("Search Results:");
        for (Employee emp : employees.values()) {
            if (emp.name.equalsIgnoreCase(input) || String.valueOf(emp.empId).equals(input)) {
                emp.display();
            }
        }
    }
}


