package beans;

import java.util.Date;

public class Resume {
    private int specialityID;
    private int employeeID;
    private int id;
    private int experience;
    private Date date;

    public Resume(int id, int employeeID, int specialityID, int experience, Date date) {
        this.specialityID = specialityID;
        this.employeeID = employeeID;
        this.id = id;
        this.experience = experience;
        this.date = date;
    }

    public int getSpecialityID() {
        return specialityID;
    }

    public void setSpecialityID(int specialityID) {
        this.specialityID = specialityID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
