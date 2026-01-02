package service;

import model.Student;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecordManager {
    private final List<Student> students = new ArrayList<>();

    public void addStudent(Student s) {
        if (findById(s.getId()) != null) {
            throw new IllegalArgumentException("A student with this ID already exists.");
        }
        students.add(s);
    }

    public List<Student> getAll() {
        return new ArrayList<>(students);
    }

    public Student findById(String id) {
        if (id == null) return null;
        String target = id.trim();
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(target)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> searchByName(String keyword) {
        String k = keyword == null ? "" : keyword.trim().toLowerCase();
        List<Student> out = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(k)) {
                out.add(s);
            }
        }
        return out;
    }

    public List<Student> filterByGpa(double min, double max) {
        List<Student> out = new ArrayList<>();
        for (Student s : students) {
            if (s.getGpa() >= min && s.getGpa() <= max) {
                out.add(s);
            }
        }
        return out;
    }

    public void sortByGpaDesc() {
        students.sort(Comparator.comparingDouble(Student::getGpa).reversed());
    }

    public void sortByNameAsc() {
        students.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
    }

    public void updateGpa(String id, double newGpa) {
        if (newGpa < 0.0 || newGpa > 12.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 12.0.");
        }
        Student s = findById(id);
        if (s == null) {
            throw new IllegalArgumentException("Student not found.");
        }
        // Student fields are final, so we replace the object safely
        students.remove(s);
        students.add(new Student(s.getId(), s.getName(), newGpa));
    }

    public void deleteById(String id) {
        Student s = findById(id);
        if (s == null) {
            throw new IllegalArgumentException("Student not found.");
        }
        students.remove(s);
    }

    public void clearAndLoad(List<Student> loaded) {
        students.clear();
        students.addAll(loaded);
    }
}
