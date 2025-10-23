package src.data;

public enum CourseCategory {
    GENERAL_EDUCATION("General Education", "Courses counted toward the general education requirement"),
    MAJOR("Major", "Required courses for a student's major"),
    MAJOR_ELECTIVE("Major Elective", "Elective courses that count toward the major")
    ;

    private final String displayName;
    private final String description;

    CourseCategory(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
