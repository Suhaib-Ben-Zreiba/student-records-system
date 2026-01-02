package storage;

import model.Student;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    public static void saveToCsv(Path path, List<Student> students) throws IOException {
        Files.createDirectories(path.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write("id,name,gpa");
            bw.newLine();
            for (Student s : students) {
                bw.write(s.toCsvRow());
                bw.newLine();
            }
        }
    }

    public static List<Student> loadFromCsv(Path path) throws IOException {
        List<Student> out = new ArrayList<>();
        if (!Files.exists(path)) {
            return out;
        }
        List<String> lines = Files.readAllLines(path);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            if (i == 0 && line.toLowerCase().startsWith("id,")) continue; // header
            try {
                out.add(Student.fromCsvRow(line));
            } catch (Exception ex) {
                // Skip bad lines but keep loading the rest
            }
        }
        return out;
    }
}
