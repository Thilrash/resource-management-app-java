/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.studentgroup;

/**
 *
 * @author thilr_88qp6ap
 */
public class StrudentGroup {
    private int id;
    private String academic_year;    
    private String programme;
    private int group_no;
    private int sub_group_no;
    private String group_id;
    private String sub_group_id;

    public StrudentGroup(int id, String academic_year, String programme, int group_no, int sub_group_no, String group_id, String sub_group_id) {
        super();
        this.id = id;
        this.academic_year = academic_year;
        this.programme = programme;
        this.group_no = group_no;
        this.sub_group_no = sub_group_no;
        this.group_id = group_id;
        this.sub_group_id = sub_group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public int getGroup_no() {
        return group_no;
    }

    public void setGroup_no(int group_no) {
        this.group_no = group_no;
    }

    public int getSub_group_no() {
        return sub_group_no;
    }

    public void setSub_group_no(int sub_group_no) {
        this.sub_group_no = sub_group_no;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getSub_group_id() {
        return sub_group_id;
    }

    public void setSub_group_id(String sub_group_id) {
        this.sub_group_id = sub_group_id;
    }
    
    
}
