package beans;

public class Vacancy {
    private int id;
    private int enterpriseID;
    private int salary;
    private int experience;
    private int specialityID;

    public Vacancy(int id, int enterpriseID, int specialityID, int experience, int salary) {
        this.enterpriseID = enterpriseID;
        this.id = id;
        this.salary = salary;
        this.experience = experience;
        this.specialityID = specialityID;
    }

    public int getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(int enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSpecialityID() {
        return specialityID;
    }

    public void setSpecialityID(int specialityID) {
        this.specialityID = specialityID;
    }
}
