package model;

public class Student {
    private final String id;
    private final String name;
    private final double gpa;

    public Student(String id, String name, double gpa) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student id cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (gpa < 0.0 || gpa > 12.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 12.0.");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.gpa = gpa;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }

    public String toCsvRow() {
        String safeName = name.replace(",", " ");
        return id + "," + safeName + "," + gpa;
    }

    public static Student fromCsvRow(String row) {
        String[] parts = row.split(",", -1);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid CSV row: " + row);
        }
        String id = parts[0].trim();
        String name = parts[1].trim();
        double gpa = Double.parseDouble(parts[2].trim());
        return new Student(id, name, gpa);
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | GPA: %.2f", id, name, gpa);
    }
}
