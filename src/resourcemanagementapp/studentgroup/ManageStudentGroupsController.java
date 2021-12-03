/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.studentgroup;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resourcemanagementapp.database.DBConnection;
import resourcemanagementapp.lecturers.Lecturer;
import resourcemanagementapp.subjects.ManageSubjectsController;
import resourcemanagementapp.subjects.Subject;


/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class ManageStudentGroupsController implements Initializable {

    @FXML
    private AnchorPane managestudentgrouppane;
    @FXML
    private TableView<StrudentGroup> StudentGroupTanle;
    @FXML
    private TableColumn<?, ?> ColoumnStudentID;
    @FXML
    private TableColumn<?, ?> ColoumnAcademic;
    @FXML
    private TableColumn<?, ?> ColoumnProgramme;
    @FXML
    private TableColumn<?, ?> ColoumnGroupNo;
    @FXML
    private TableColumn<?, ?> ColoumnSubGroupNo;
    @FXML
    private TableColumn<?, ?> ColoumnGroupID;
    @FXML
    private TableColumn<?, ?> ColoumnSubGroupID;
    @FXML
    private JFXTextField txtAcademicYear;
    @FXML
    private JFXComboBox comboProgramme;
    @FXML
    private Spinner spnGroupNo;
    @FXML
    private Spinner spnSubGroupNo;
    @FXML
    private JFXTextField txtGroupID;
    @FXML
    private JFXTextField txtSubGroupID;
    @FXML
    private Label errLbl;
    
    // DB Connection
    Connection connection = DBConnection.DBConnector();
    ObservableList<StrudentGroup> student_data = FXCollections.observableArrayList();
    FilteredList<StrudentGroup> filteredData = new FilteredList<>(student_data,e->true);
    
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Combo Box
        showProgramme();
        
        // Spinner
        spinnerValue();
        
        // Display Table Data
        ColoumnStudentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColoumnAcademic.setCellValueFactory(new PropertyValueFactory<>("academic_year"));
        ColoumnProgramme.setCellValueFactory(new PropertyValueFactory<>("programme"));
        ColoumnGroupNo.setCellValueFactory(new PropertyValueFactory<>("group_no"));
        ColoumnSubGroupNo.setCellValueFactory(new PropertyValueFactory<>("sub_group_no"));
        ColoumnGroupID.setCellValueFactory(new PropertyValueFactory<>("group_id"));
        ColoumnSubGroupID.setCellValueFactory(new PropertyValueFactory<>("sub_group_id"));
        
        try {
            // Call Method of Load Data Function
            LoadStudentGroupData();
        } catch (SQLException ex) {
            Logger.getLogger(ManageStudentGroupsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    // Combo box
    public void showProgramme() {
        comboProgramme.getItems().clear();
        comboProgramme.getItems().addAll("IT", "CSSE", "CSE", "IM");
    }
    
    // Spinner Values
    public void spinnerValue() {
        SpinnerValueFactory<Integer> GroupNoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0);
        spnGroupNo.setValueFactory(GroupNoSpinner);
        SpinnerValueFactory<Integer> SubGroupNoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0);
        spnSubGroupNo.setValueFactory(SubGroupNoSpinner);
    }
    
    // Load Data From The Database
    public void LoadStudentGroupData() throws SQLException {
        String query = "SELECT * FROM student_group";
        try {
            student_data.clear();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                student_data.add(new StrudentGroup(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getInt(4), 
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
                StudentGroupTanle.setItems(student_data);
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    // Validation
    public boolean Validation() {
        if("".equals(txtAcademicYear.getText())) {
            errLbl.setText("* Please, fill the Academic Year");
            return true;
        }

        if(comboProgramme.getValue() == null) {
            errLbl.setText("* Please, choose the Programme");
            return true;
        }
        
        if("".equals(txtGroupID.getText())) {
            errLbl.setText("* Please, Generate Group ID");
            return true;
        }
        
        if("".equals(txtSubGroupID.getText())) {
            errLbl.setText("* Please, Generate Sub Group ID");
            return true;
        }
        
        return false;
    }
    
    @FXML
    private void backBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.managestudentgrouppane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/resourcemanagementapp/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void showOnClick() {
        StrudentGroup student = StudentGroupTanle.getSelectionModel().getSelectedItem();
        txtAcademicYear.setText(student.getAcademic_year());
        comboProgramme.setValue(student.getProgramme());
        spnGroupNo.getValueFactory().setValue(student.getGroup_no());
        spnSubGroupNo.getValueFactory().setValue(student.getSub_group_no());
        txtGroupID.setText(student.getGroup_id());
        txtSubGroupID.setText(student.getSub_group_id());
    }

    @FXML
    private void generateBtnPressed(ActionEvent event) {
        String ays = txtAcademicYear.getText();
        errLbl.setText("");
        
        if (!(("Y1.S1".equals(ays)) || ("Y1.S2".equals(ays)) || ("Y2.S1".equals(ays)) || ("Y2.S2".equals(ays)) || ("Y3.S1".equals(ays)) || ("Y3.S2".equals(ays)) || ("Y4.S1".equals(ays)) || ("Y4.S2".equals(ays))) || ("".equals(ays)))  {
            errLbl.setText("*! Please follow the format of Academic Year");
        } else if(comboProgramme.getValue() == null) {
            errLbl.setText("*! Please choose the programme");
        } else {
            String aYear_aSem = txtAcademicYear.getText();
            String prog = comboProgramme.getValue().toString();
            
            int gNo = (int) spnGroupNo.getValue();
            String groupID = aYear_aSem+"."+prog+"."+gNo;
            txtGroupID.setText(groupID);
            
            int sgNo = (int) spnSubGroupNo.getValue();
            String subGroupID = groupID+"."+sgNo;
            txtSubGroupID.setText(subGroupID);
            errLbl.setText("");
        }
    }

    @FXML
    private void clearBtnPressed() {
        StudentGroupTanle.getSelectionModel().clearSelection();
        txtAcademicYear.setText("");
        comboProgramme.setValue(null);
        spinnerValue();
        txtGroupID.setText("");
        txtSubGroupID.setText("");
        errLbl.setText("");
    }

    @FXML
    private void addBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.managestudentgrouppane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("AddStudentGroups.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Student Group - Resouce Management Application");
        stage.setMaximized(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    
    // Delete subject
    String fullgroupid;
    @FXML
    private void deleteBtnPressed(ActionEvent event) {
        StrudentGroup student = StudentGroupTanle.getSelectionModel().getSelectedItem();
        fullgroupid = student.getSub_group_id();
        
        if(StudentGroupTanle.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("Delete Subject Error");
            alert.setHeaderText("Student Group selection from the table is required!");
            alert.setContentText("Please select the Student Group, then try again!");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) 
                {
                    System.out.println("Pressed OK.");
                    clearBtnPressed();
                try {
                    LoadStudentGroupData();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageSubjectsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            });
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                // Get the Stage.
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                alert.setTitle("Delete Student Group");
                alert.setHeaderText("Confirmation To Delete Student Group");
                alert.setContentText("Do you wnat to delete "+fullgroupid+ " Subject Reocord ? Ok to continue...");
                alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        String query = "DELETE FROM student_group WHERE id = ?";
                        pst = connection.prepareStatement(query);
                        pst.setInt(1, student.getId());
                        fullgroupid = student.getSub_group_id();
                        int connectStatus = pst.executeUpdate();
                        pst.close();


                        if(connectStatus == 1) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            // Get the Stage.
                            Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert1.setTitle("Delete Student Group");
                            alert1.setHeaderText("Successfully Deleted");
                            alert1.setContentText(fullgroupid + " successfully deleted from system");
                            alert1.showAndWait().ifPresent(rs1 -> {
                            if (rs1 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed();
                                try {
                                    LoadStudentGroupData();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ManageSubjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                }
                            });
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            // Get the Stage.
                            Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert2.setTitle("Delete Student Group Error");
                            alert2.setHeaderText("Unsuccessful Deletion");
                            alert2.setContentText(fullgroupid + " can not delete from system");
                            alert2.showAndWait().ifPresent(rs2 -> {
                            if (rs2 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed();
                                try {
                                    LoadStudentGroupData();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ManageSubjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                }
                            });
                        } 
                        } catch (SQLException e) {
                            System.out.println(e);
                        }

                } 
                });

            } catch (Exception e) {
                System.out.println(e);
            } 
        
        }
    }
    
    @FXML
    private void updateBtnPressed() {
        
        StrudentGroup student = StudentGroupTanle.getSelectionModel().getSelectedItem();
        if(StudentGroupTanle.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Resource Management Application");
            alert.setHeaderText("Update Student Group Error");
            alert.setContentText("Please, Select Student Group From The Table");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        } else {
            String gID = txtGroupID.getText();
            String sgID = txtSubGroupID.getText();

            int sGno = (int) spnGroupNo.getValue();
            int sSGno = (int) spnSubGroupNo.getValue();

            String string_sGno = String.valueOf(sGno);
            String string_sSGno = String.valueOf(sSGno);

            String cGID = txtAcademicYear.getText()+"."+comboProgramme.getValue().toString()+"."+string_sGno;
            String cSGID = txtAcademicYear.getText()+"."+comboProgramme.getValue().toString()+"."+string_sGno+"."+string_sSGno;
            
            if(Validation() == true) {
                System.out.println("Validation Error");
                
            } else if((!gID.equals(cGID)) || (!sgID.equals(cSGID))) {   
                errLbl.setText("* Group miss matched, please check the rank filed...");
            } else {
                errLbl.setText("");
                try {
                    String query = "UPDATE student_group SET id=?, acadedemic_year=?, programme=?, group_no=?, sub_group_no=?, group_id=?, sub_group_id=? WHERE id='"+student.getId()+"'";
                    pst = connection.prepareStatement(query);
                    
                    pst.setInt(1, student.getId());
                    pst.setString(2, txtAcademicYear.getText());
                    pst.setString(3, comboProgramme.getValue().toString());
                    pst.setInt(4, (int) spnGroupNo.getValue());
                    pst.setInt(5, (int) spnSubGroupNo.getValue());
                    pst.setString(6, txtGroupID.getText());
                    pst.setString(7, txtSubGroupID.getText());
                    
                    int result = pst.executeUpdate();
                    
                    pst.close();
                    
                    if(result == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Student Group Succesfull");
                        alert.setContentText("Successfully update "+txtSubGroupID.getText()+ "'s Data");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alert.showAndWait();
                        clearBtnPressed();
                        LoadStudentGroupData();
                        
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Lecturer Error");
                        alert.setContentText("Can not update "+txtSubGroupID.getText()+ "'s Data at this moment, please try again later.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }
    }
    
}
