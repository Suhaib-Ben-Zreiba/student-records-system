import model.Student;
import service.RecordManager;
import storage.FileStorage;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Path DATA_PATH = Path.of("data", "students.csv");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RecordManager rm = new RecordManager();

        // Auto-load on start
        try {
            List<Student> loaded = FileStorage.loadFromCsv(DATA_PATH);
            rm.clearAndLoad(loaded);
        } catch (Exception ignored) {}

        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addStudent(sc, rm);
                    case "2" -> listStudents(rm.getAll());
                    case "3" -> search(sc, rm);
                    case "4" -> filterGpa(sc, rm);
                    case "5" -> sortMenu(sc, rm);
                    case "6" -> updateStudent(sc, rm);
                    case "7" -> deleteStudent(sc, rm);
                    case "8" -> save(rm);
                    case "9" -> load(rm);
                    case "0" -> {
                        save(rm);
                        System.out.println("Goodbye.");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("Student Records Management System");
        System.out.println("1) Add student");
        System.out.println("2) List students");
        System.out.println("3) Search (by ID or name)");
        System.out.println("4) Filter by GPA range");
        System.out.println("5) Sort");
        System.out.println("6) Update student GPA");
        System.out.println("7) Delete student");
        System.out.println("8) Save");
        System.out.println("9) Load");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private static void addStudent(Scanner sc, RecordManager rm) {
        System.out.print("Student ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("GPA (0.0 to 12.0): ");
        double gpa = Double.parseDouble(sc.nextLine().trim());

        rm.addStudent(new Student(id, name, gpa));
        System.out.println("Added.");
    }

    private static void listStudents(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : list) {
            System.out.println(s);
        }
    }

    private static void search(Scanner sc, RecordManager rm) {
        System.out.print("Enter ID or name keyword: ");
        String q = sc.nextLine().trim();
        Student byId = rm.findById(q);
        if (byId != null) {
            System.out.println(byId);
            return;
        }
        List<Student> byName = rm.searchByName(q);
        listStudents(byName);
    }

    private static void filterGpa(Scanner sc, RecordManager rm) {
        System.out.print("Min GPA: ");
        double min = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Max GPA: ");
        double max = Double.parseDouble(sc.nextLine().trim());
        listStudents(rm.filterByGpa(min, max));
    }

    private static void sortMenu(Scanner sc, RecordManager rm) {
        System.out.println("1) Sort by GPA (desc)");
        System.out.println("2) Sort by Name (asc)");
        System.out.print("Choose: ");
        String s = sc.nextLine().trim();
        if (s.equals("1")) {
            rm.sortByGpaDesc();
            System.out.println("Sorted by GPA.");
        } else if (s.equals("2")) {
            rm.sortByNameAsc();
            System.out.println("Sorted by name.");
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void updateStudent(Scanner sc, RecordManager rm) {
        System.out.print("Student ID: ");
        String id = sc.nextLine().trim();
        System.out.print("New GPA (0.0 to 12.0): ");
        double gpa = Double.parseDouble(sc.nextLine().trim());
        rm.updateGpa(id, gpa);
        System.out.println("GPA updated.");
    }

    private static void deleteStudent(Scanner sc, RecordManager rm) {
        System.out.print("Student ID: ");
        String id = sc.nextLine().trim();
        rm.deleteById(id);
        System.out.println("Student deleted.");
    }

    private static void save(RecordManager rm) {
        try {
            FileStorage.saveToCsv(DATA_PATH, rm.getAll());
            System.out.println("Saved to " + DATA_PATH);
        } catch (Exception ex) {
            System.out.println("Save failed: " + ex.getMessage());
        }
    }

    private static void load(RecordManager rm) {
        try {
            List<Student> loaded = FileStorage.loadFromCsv(DATA_PATH);
            rm.clearAndLoad(loaded);
            System.out.println("Loaded " + loaded.size() + " students.");
        } catch (Exception ex) {
            System.out.println("Load failed: " + ex.getMessage());
        }
    }
}
