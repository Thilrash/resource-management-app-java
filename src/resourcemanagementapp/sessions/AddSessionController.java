/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.sessions;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resourcemanagementapp.database.DBConnection;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class AddSessionController implements Initializable {

    @FXML
    private AnchorPane addsessionpane;
    @FXML
    private Tab selectLecturer;
    @FXML
    private JFXComboBox cmbSelectLecturer;
    @FXML
    private JFXComboBox cmbSelectTag;
    @FXML
    private Label lblLectuer1;
    @FXML
    private Label lblLectuer2;
    @FXML
    private Tab selectGroup;
    @FXML
    private JFXComboBox cmbSelectGroup;
    @FXML
    private JFXComboBox cmbSelectSubject;
    @FXML
    private JFXTextField txtNoOfStudents;
    @FXML
    private JFXTextField txtDuration;
    @FXML
    private JFXTabPane tabpane;

    Connection connection = DBConnection.DBConnector();
    PreparedStatement pst = null;
    ResultSet rs = null;
    @FXML
    private Label lblError;
    @FXML
    private Label lblError2;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadCmbLecturer();
            loadCmbTag();
            loadCmbSubject();
        } catch (SQLException ex) {
            Logger.getLogger(AddSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    // Load lecturers to select lecturer combo box 
    public void loadCmbLecturer() throws SQLException {
        try {
            String query = "SELECT lecturer_name FROM lecturer order by id";
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()) {
                String tempString = rs.getString("lecturer_name");
                cmbSelectLecturer.getItems().add(tempString);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            pst.close();
            rs.close();
        }
    }
    
    // Get values from lecturer combobox
    @FXML
    public void displayLecturerOnLbl() throws SQLException {
        if("".equals(lblLectuer1.getText())) {
            lblError.setText("");
            lblLectuer1.setText((String) cmbSelectLecturer.getValue());
            cmbSelectLecturer.getItems().clear();
            loadCmbLecturer();   
        } else {
            if(lblLectuer1.getText().equals((String) cmbSelectLecturer.getValue())) {
                lblError.setText("*! The Same Lecturer Can not be add to the one session");
            } else {
                lblError.setText("");
                lblLectuer2.setText((String) cmbSelectLecturer.getValue());
            }
        }
    }

    // Load the tag to combo box
    public void loadCmbTag() throws SQLException {
        try {
            String query = "SELECT tag_name FROM tag order by id";
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()) {
                String tempString = rs.getString("tag_name");
                cmbSelectTag.getItems().add(tempString);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            pst.close();
            rs.close();
        }
    }
    
    // Load Group Depend on Tag
    @FXML
    public void loadCmbGroup() throws SQLException {
        cmbSelectGroup.getItems().clear();
        System.out.println("136 Line Executed");
        String tempTag = (String) cmbSelectTag.getValue();
        if("Lecture".equals(tempTag) || "Tutorial".equals(tempTag)) {
            System.out.println("139 Line Executed");
            try {
                String query1 = "SELECT DISTINCT group_id FROM student_group order by id";
                pst = connection.prepareStatement(query1);
                rs = pst.executeQuery();
                while(rs.next()) {
                    String tempString = rs.getString("group_id");
                    cmbSelectGroup.getItems().add(tempString);
                }
            } catch (SQLException e) {
                System.err.println(e);
            } finally {
                pst.close();
                rs.close();
            }
        } else {
            try {
                String query2 = "SELECT DISTINCT sub_group_id FROM student_group order by id";
                pst = connection.prepareStatement(query2);
                rs = pst.executeQuery();
                while(rs.next()) {
                    String tempString = rs.getString("sub_group_id");
                    cmbSelectGroup.getItems().add(tempString);
                }
            } catch (SQLException e) {
                System.err.println(e);
            } finally {
                pst.close();
                rs.close();
            }
        }
    }
    
    // Load the subject to combo box
    public void loadCmbSubject() throws SQLException {
        try {
            String query = "SELECT DISTINCT subject_name FROM subject order by id";
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()) {
                String tempString = rs.getString("subject_name");
                cmbSelectSubject.getItems().add(tempString);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            pst.close();
            rs.close();
        }
    }
    
    // Validation
    public boolean validation() {
        if(cmbSelectGroup.getItems() == null) {        }
        
        if(cmbSelectGroup.getValue() == null) {
            lblError2.setText("*! Please Select Student Group");
            return true;
        }
        
        if(cmbSelectSubject.getValue() == null) {
            lblError2.setText("*! Please Select Subject");
            return true;
        }

        if("".equals(txtNoOfStudents.getText())) {
            lblError2.setText("*! Please Fill Number OF Students To Add Session");
            return true;
        }
        
        if("".equals(txtDuration.getText())) {
            lblError2.setText("*! Please Fill Duration Of The Session");
            return true;
        }
        
        return false;
    }
    
    @FXML
    private void nextBtnPressed(ActionEvent event) {
        if("".equals(lblLectuer1.getText()) || "".equals(lblLectuer2.getText())) {
            lblError.setText("");
            lblError.setText("*! Please Select Lecturer For The Session");
        } else if(cmbSelectTag.getValue() == null) {
            lblError.setText("");
            lblError.setText("*! Please Select Tag For The Session");
        } else {
            lblError.setText("");
            tabpane.getSelectionModel().selectNext();
        } 
    }

    @FXML
    private void clearBtnPressed() {
        cmbSelectLecturer.setValue(null);
        cmbSelectTag.setValue(null);
        cmbSelectGroup.setValue(null);
        cmbSelectGroup.getItems().clear();
        lblLectuer1.setText("");
        lblLectuer2.setText(""); 
        lblError.setText("");
    }

    @FXML
    private void backTabBtnPressed(ActionEvent event) {
        tabpane.getSelectionModel().selectFirst();
    }
    
    // Load Auto Increment ID
    int AID;
    public int autoIncrementID() throws SQLException {
        String query = "SELECT id FROM session ORDER BY id DESC LIMIT 1";
        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();

        if(rs.next()) {
            AID = rs.getInt("id");
            AID = AID + 1;
        } else {
            AID = 1;
        }
        System.out.println("Auto Increment ID : " + AID);
        rs.close();
        pst.close();
        return AID;
    }
    
    // get subject code from database
    String SubjectCode;
    public String getSubjectCode() throws SQLException{
        String SubjectName = cmbSelectSubject.getValue().toString();
        String query = "SELECT subject_code FROM subject WHERE subject_name = '" + SubjectName + "'";
        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();
        if (rs.next()) {
            SubjectCode = rs.getString("subject_code");
        } else {
            SubjectCode = "";
        }
        rs.close();
        pst.close();
        return SubjectCode;
    }
    
    @FXML
    private void submitBtnPressed(ActionEvent event) throws SQLException {
        if (validation() == true) {
            System.out.println("Validation true");
        } else {
            System.out.println("381 Line executed");
            autoIncrementID();
            System.out.println("Auto Increment ID : "+AID);
            getSubjectCode();
            lblError2.setText("");
            lblError.setText("");
            // Add data to database
            try {
                String query = "INSERT INTO session (id, lecturer1, lecturer2, subject_code, subject_name, group_id, tag, no_of_students, duration) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)";

                pst = connection.prepareStatement(query);
                pst.setInt(1,AID);
                pst.setString(2, lblLectuer1.getText());
                pst.setString(3, lblLectuer2.getText());
                pst.setString(4, SubjectCode);
                pst.setString(5, cmbSelectSubject.getValue().toString());
                pst.setString(6, cmbSelectGroup.getValue().toString());
                pst.setString(7, cmbSelectTag.getValue().toString());
                pst.setInt(8, Integer.parseInt(txtNoOfStudents.getText()));
                pst.setInt(9, Integer.parseInt(txtDuration.getText()));

                int connectUpdate = pst.executeUpdate();
                pst.close();

                if (connectUpdate == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Session");
                    alert.setHeaderText("Successfully Inserted.");
                    alert.setContentText("Session Inserted Successfully");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK)
                        {
                            System.out.println("Pressed OK.");
                            clearBtnPressed();
                            clearBtn2Pressed();
                        }
                    });
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Session Error");
                    alert.setHeaderText("Sorry, we can not add session at this moment.");
                    alert.setContentText("Please Try Again Later");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK)
                        {
                            System.out.println("Pressed OK.");
                        }
                    });
                }
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                pst.close();
                rs.close();
            }
        }
    }

    @FXML
    private void clearBtn2Pressed() {
        cmbSelectGroup.setValue(null);
        cmbSelectSubject.setValue(null);
        txtDuration.setText("");
        txtNoOfStudents.setText("");
        lblError2.setText("");
    }
    
    @FXML
    private void checkDataInCmbGroup() {
        System.out.println("373");
        if(cmbSelectGroup.getItems() == null) {
            lblError2.setText("");
            lblError2.setText("*! Please Select Tag Before");
        } else {
            lblError2.setText("");
            System.out.println("376");
        }
    }
    
}
