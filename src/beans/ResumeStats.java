package beans;

public class ResumeStats {
    private String speciality;
    private int numberOfResumes;

    public ResumeStats(String speciality, int numberOfResumes) {
        this.speciality = speciality;
        this.numberOfResumes = numberOfResumes;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getNumberOfResumes() {
        return numberOfResumes;
    }

    public void setNumberOfResumes(int numverOfResumes) {
        this.numberOfResumes = numverOfResumes;
    }
}
