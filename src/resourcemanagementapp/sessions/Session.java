/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.sessions;

/**
 *
 * @author thilr_88qp6ap
 */
public class Session {
    private int id;
    private String lecturer1;
    private String lecturer2;
    private String subject_code;
    private String subject_name;
    private String group_id;
    private String tag;

    public Session(int id, String lecturer1, String lecturer2, String subject_code, String subject_name, String group_id, String tag) {
        this.id = id;
        this.lecturer1 = lecturer1;
        this.lecturer2 = lecturer2;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.group_id = group_id;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLecturer1() {
        return lecturer1;
    }

    public void setLecturer1(String lecturer1) {
        this.lecturer1 = lecturer1;
    }

    public String getLecturer2() {
        return lecturer2;
    }

    public void setLecturer2(String lecturer2) {
        this.lecturer2 = lecturer2;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
    
}
