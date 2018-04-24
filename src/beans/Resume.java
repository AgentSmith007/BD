package beans;

import java.util.Date;

public class Resume {
    private int specialityID;
    private int employeeID;
    private int resumeID;
    private Date date;

    public Resume(int resumeID, int employeeID, int specialityID, Date date) {
        this.specialityID = specialityID;
        this.employeeID = employeeID;
        this.resumeID = resumeID;
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

    public int getResumeID() {
        return resumeID;
    }

    public void setResumeID(int resumeID) {
        this.resumeID = resumeID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
