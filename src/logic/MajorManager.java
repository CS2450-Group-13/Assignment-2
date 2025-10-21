package src.logic;
import src.data.Major;
import java.util.ArrayList;
import java.util.List;

// Handles data logic for majors
public class MajorManager {
    private List<Major> majors = new ArrayList<>();

    public void addMajor(Major major) {
        majors.add(major);
    }

    public List<Major> getCourses() {
        return majors;
    }

    public Major getMajorByName(String name) {
        return majors.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // If we want admin functionality on the UI
    // public removeMajorById() {

    // }
}

