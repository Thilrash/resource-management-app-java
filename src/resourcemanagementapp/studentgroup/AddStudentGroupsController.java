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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resourcemanagementapp.database.DBConnection;
import resourcemanagementapp.subjects.AddSubjectController;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class AddStudentGroupsController implements Initializable {

    @FXML
    private AnchorPane addstudentgrouppane;
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
    
    // Load Auto Increment ID
    int AID;
    public int autoIncrementID() throws SQLException {
        String query = "SELECT id FROM student_group ORDER BY id DESC LIMIT 1";
        pst = (PreparedStatement) connection.prepareStatement(query);
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

    @FXML
    private void backBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.addstudentgrouppane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("ManageStudentGroups.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Student Groups - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void clearBtnPressed(ActionEvent event) {
        txtAcademicYear.setText("");
        comboProgramme.setValue(null);
        spinnerValue();
        txtGroupID.setText("");
        txtSubGroupID.setText("");
        errLbl.setText("");
    }

    @FXML
    private void saveBtnPressed(ActionEvent event) throws SQLException {
        autoIncrementID();
        if(Validation() == true) {
            System.out.println("== Statement FALSE ==");
        } else {
            errLbl.setText("");
            // Add data to DB
            String accademicYS = txtAcademicYear.getText();
            String programe = comboProgramme.getValue().toString();
            int groupNO = (int) spnGroupNo.getValue();
            int subGroupNo = (int) spnSubGroupNo.getValue();
            String gID = txtGroupID.getText();
            String sgID = txtSubGroupID.getText();
            
            String query = "INSERT INTO student_group (id, acadedemic_year, programme, group_no, sub_group_no, group_id, sub_group_id) VALUES (?,?,?,?,?,?,?)";
            
            pst = null;
            
            try {
                pst = connection.prepareStatement(query);
                pst.setInt(1, AID);
                pst.setString(2, accademicYS);
                pst.setString(3, programe);
                pst.setInt(4, groupNO);
                pst.setInt(5, subGroupNo);
                pst.setString(6, gID);
                pst.setString(7, sgID);
                
                int connectStatus = pst.executeUpdate();
                pst.close();
                
                if(connectStatus == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Student Group");
                    alert.setHeaderText("Successfully Inserted.");
                    alert.setContentText("Student Group Add Successfully");
                    alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) 
                        {
                            System.out.println("Pressed OK.");
                            clearBtnPressed(event);
                        try {
                            autoIncrementID();
                        } catch (SQLException ex) {
                            Logger.getLogger(AddSubjectController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    });
                    
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Student Group Error");
                    alert.setHeaderText("Sorry, we can not add subject at this momoment.");
                    alert.setContentText("Please Try Again Later");
                    alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) 
                        {
                            System.out.println("Pressed OK.");
                        }
                    }); 
                }
            } catch(SQLException e) {
                System.out.println(e);
            }
            finally {
                pst.close();
            }
        }
    }

    @FXML
    private void generateBtnPressed() {
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
}
