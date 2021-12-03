/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.lecturers;

/**
 *
 * @author thilr_88qp6ap
 */
public class Lecturer {
    private int id;
    private String lecturerName;
    private String emloyeeID;
    private String faculty;
    private String department;
    private String center;
    private String building;
    private String level;
    private String rank;
    
    public Lecturer(int id, String lecturerName, String employeeID, String faculty, String department, String center, String building, String level, String rank) {
        super();
        this.id = id;
        this.lecturerName = lecturerName;
        this.emloyeeID = employeeID;
        this.faculty = faculty;
        this.department = department;
        this.center = center;
        this.building = building;
        this.level = level;
        this.rank = rank;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the lecturerName
     */
    public String getLecturerName() {
        return lecturerName;
    }

    /**
     * @param lecturerName the lecturerName to set
     */
    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    /**
     * @return the emloyeeID
     */
    public String getEmloyeeID() {
        return emloyeeID;
    }

    /**
     * @param emloyeeID the emloyeeID to set
     */
    public void setEmloyeeID(String emloyeeID) {
        this.emloyeeID = emloyeeID;
    }

    /**
     * @return the faculty
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the center
     */
    public String getCenter() {
        return center;
    }

    /**
     * @param center the center to set
     */
    public void setCenter(String center) {
        this.center = center;
    }

    /**
     * @return the building
     */
    public String getBuilding() {
        return building;
    }

    /**
     * @param building the building to set
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(String rank) {
        this.rank = rank;
    }
    
}
