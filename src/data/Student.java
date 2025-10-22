package src.data;

public class Student {
    private String name;
    private int id;
    private String semStarted; // format Fall/Spring Semester [Year]
    private String expectedGradSem;
    private boolean isUndergrad;
    private Major major;
    private boolean gradStatus;

    public Student(
        String name, 
        int id, 
        String semStarted,
        String expectedGradSem,
        boolean isUndergrad,
        Major major,
        boolean gradStatus
        ) {

        this.name = name;
        this.id = id;
        this.isUndergrad = isUndergrad;
        this.semStarted = semStarted;
        this.expectedGradSem = expectedGradSem;
        this.isUndergrad = isUndergrad;
        this.major = major;
        this.gradStatus = gradStatus;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getUndergradStatus() {
        return (isUndergrad) ? "Undergraduate" : "Graduate";
    }

    public String getStartingSemester() {
        return semStarted;
    }

    public String getExpectedGradSem() {
        return expectedGradSem;
    }

    public Major getMajor() {
        return major;
    }

    public String getGradStatus() {
        return (gradStatus) ? "Applied" : "Not Applied";
    }
}
