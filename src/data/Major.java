package src.data;
import java.util.List;
import java.util.ArrayList;

public class Major {
    private String name;
    private List<Course> courses;

    public Major(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public Major(String name, List<Course> courses) {
        this.name = name;
        this.courses = (courses != null) ? courses : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Course> getCoursesByCategory(CourseCategory category) {
        return courses.stream()
            .filter(c -> c.getCourseCategory() == category)
            .toList();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

}
