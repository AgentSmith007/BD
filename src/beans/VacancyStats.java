package beans;

public class VacancyStats {
    private String speciality;
    private int numberOfVacancies;
    private int averageSalary;

    public VacancyStats(String speciality, int numberOfVacancies, int averageSalary) {
        this.speciality = speciality;
        this.numberOfVacancies = numberOfVacancies;
        this.averageSalary = averageSalary;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getNumberOfVacancies() {
        return numberOfVacancies;
    }

    public void setNumberOfVacancies(int numberOfVacancies) {
        this.numberOfVacancies = numberOfVacancies;
    }

    public int getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(int averageSalary) {
        this.averageSalary = averageSalary;
    }
}
