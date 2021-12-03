/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.subjects;

/**
 *
 * @author thilr_88qp6ap
 */
public class Subject {
    private int id;
    private String offered_year;
    private String offered_semester;
    private String subject_name;
    private String subject_code;
    private int no_lecture_hours;
    private int no_tutorial_hours;
    private int no_lab_hours;
    private int no_evaluvation_hours;

    public Subject(int id, String offered_year, String offered_semester, String subject_name, String subject_code, int no_lecture_hours, int no_tutorial_hours, int no_lab_hours, int no_evaluvation_hours) {
        super();
        this.id = id;
        this.offered_year = offered_year;
        this.offered_semester = offered_semester;
        this.subject_name = subject_name;
        this.subject_code = subject_code;
        this.no_lecture_hours = no_lecture_hours;
        this.no_tutorial_hours = no_tutorial_hours;
        this.no_lab_hours = no_lab_hours;
        this.no_evaluvation_hours = no_evaluvation_hours;
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
     * @return the offered_year
     */
    public String getOffered_year() {
        return offered_year;
    }

    /**
     * @param offered_year the offered_year to set
     */
    public void setOffered_year(String offered_year) {
        this.offered_year = offered_year;
    }

    /**
     * @return the offered_semester
     */
    public String getOffered_semester() {
        return offered_semester;
    }

    /**
     * @param offered_semester the offered_semester to set
     */
    public void setOffered_semester(String offered_semester) {
        this.offered_semester = offered_semester;
    }

    /**
     * @return the subject_name
     */
    public String getSubject_name() {
        return subject_name;
    }

    /**
     * @param subject_name the subject_name to set
     */
    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    /**
     * @return the subject_code
     */
    public String getSubject_code() {
        return subject_code;
    }

    /**
     * @param subject_code the subject_code to set
     */
    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    /**
     * @return the no_lecture_hours
     */
    public int getNo_lecture_hours() {
        return no_lecture_hours;
    }

    /**
     * @param no_lecture_hours the no_lecture_hours to set
     */
    public void setNo_lecture_hours(int no_lecture_hours) {
        this.no_lecture_hours = no_lecture_hours;
    }

    /**
     * @return the no_tutorial_hours
     */
    public int getNo_tutorial_hours() {
        return no_tutorial_hours;
    }

    /**
     * @param no_tutorial_hours the no_tutorial_hours to set
     */
    public void setNo_tutorial_hours(int no_tutorial_hours) {
        this.no_tutorial_hours = no_tutorial_hours;
    }

    /**
     * @return the no_lab_hours
     */
    public int getNo_lab_hours() {
        return no_lab_hours;
    }

    /**
     * @param no_lab_hours the no_lab_hours to set
     */
    public void setNo_lab_hours(int no_lab_hours) {
        this.no_lab_hours = no_lab_hours;
    }

    /**
     * @return the no_evaluvation_hours
     */
    public int getNo_evaluvation_hours() {
        return no_evaluvation_hours;
    }

    /**
     * @param no_evaluvation_hours the no_evaluvation_hours to set
     */
    public void setNo_evaluvation_hours(int no_evaluvation_hours) {
        this.no_evaluvation_hours = no_evaluvation_hours;
    }
    
    
}
