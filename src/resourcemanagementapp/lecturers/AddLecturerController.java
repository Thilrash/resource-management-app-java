/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.lecturers;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resourcemanagementapp.database.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class AddLecturerController implements Initializable {

    @FXML
    private JFXTextField txtLecturerName;
    @FXML
    private JFXTextField txtEmployeeID;
    @FXML
    private JFXComboBox comboFaculty;
    @FXML
    private JFXComboBox comboDept;
    @FXML
    private JFXComboBox comboCenter;
    @FXML
    private JFXComboBox comboBuilding;
    @FXML
    private JFXComboBox comboLevel;
    @FXML
    private JFXTextField txtRank;
    @FXML
    private AnchorPane addlecturerpane;
    
    ManageLecturersController mlc;
    
    // DB Connection
    Connection connection = DBConnection.DBConnector();
    PreparedStatement pst = null;
    ResultSet rs = null;
    @FXML
    private Label errLabel;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            generateEmployeeID();
        } catch (SQLException ex) {
            Logger.getLogger(AddLecturerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if("".equals(txtEmployeeID.getText())) 
        {
            txtEmployeeID.setText("000001");
        }
       
        // Adding Items to combo box
        comboFaculty.getItems().addAll("Faculty of Computing","Faculty of Business","Faculty of Engineering","Faculty of Humanities & Sciences","Faculty of Architecture","Faculty of Hospitality & Culinary");
        comboCenter.getItems().addAll("Malabe", "Colombo", "Matara", "Kandy", "Kurunagala", "Jaffna");
        comboBuilding.getItems().addAll("Main Building","New Building","A-Block","B-Block","C-Block", "D-Block", "Center Block");
        comboLevel.getItems().addAll("1","2","3","4","5","6");
    }

    // Generating Employee IDs Automatically
    String eidFinal;
    private void generateEmployeeID() throws SQLException {
        String query = "SELECT employee_id FROM lecturer ORDER BY employee_id DESC LIMIT 1";
        pst = (PreparedStatement) connection.prepareStatement(query);
        rs = pst.executeQuery();
        if(rs.next()) {
            String eID = rs.getString("employee_id");
            int eidInt = Integer.parseInt(eID);
            System.out.println("EID Integer Before Increment Value ==> " + eidInt);
            eidInt++;
            System.out.println("EID Integer After Increment Value ==> " + eidInt);
            String nEid = Integer.toString(eidInt);
            System.out.println("EID String Value ==> " + nEid);
            int length = String.valueOf(eidInt).length();
            System.out.println("PRINT =====> " + length);
            switch (length) {
                case 1:
                    {
                        String preZero = "00000";
                        eidFinal = preZero + nEid;
                        txtEmployeeID.setText(eidFinal);
                        break;
                    }
                case 2:
                    {
                        String preZero = "0000";
                        eidFinal = preZero + nEid;
                        txtEmployeeID.setText(eidFinal);
                        break;
                    }
                case 3:
                    {
                        String preZero = "000";
                        eidFinal = preZero + nEid;
                        txtEmployeeID.setText(eidFinal);
                        break;
                    }
                case 4:
                    {
                        String preZero = "00";
                        eidFinal = preZero + nEid;
                        txtEmployeeID.setText(eidFinal);
                        break;
                    }
                case 5:
                    {
                        String preZero = "0";
                        eidFinal = preZero + nEid;
                        txtEmployeeID.setText(eidFinal);
                        break;
                    }
                case 6:
                    eidFinal = nEid;
                    txtEmployeeID.setText(eidFinal); 
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Lecturer - Employee ID Load Error");
                    alert.setHeaderText("Sorry, We can not load the employee id for lecturer ");
                    alert.setContentText("Employee ID exceeded! Please, Try again later");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK)
                        {
                            // when user clicked Add Lecturer button
                            this.addlecturerpane.getScene().getWindow().hide();

                        }
                    }); break; 
            }
        }   
        rs.close();
        pst.close();
    } 

    @FXML
    private void showDeptCombo() {
        if(comboFaculty.getValue() == "Faculty of Computing") {
            comboDept.getItems().clear();
            comboDept.getItems().addAll(
                "Information Technology",
                "Software Engineering",
                "Cyber Security",
                "Networking",
                "Data Science",
                "Interactive Media",
                "Information System"
            );
        } 
        
        if(comboFaculty.getValue() == "Faculty of Business"){
            comboDept.getItems().clear();
            comboDept.getItems().addAll(
                "Accounting & Finance",
                "Business Analytics",
                "Human Capital",
                "Marketing",
                "Business Management",
                "Logistic & Supply Chain Management",
                "Quality Management"
            );
        }
        
        if(comboFaculty.getValue() == "Faculty of Engineering"){
            comboDept.getItems().clear();
            comboDept.getItems().addAll(
                "Civil Engineering",
                "Electrical & Electronic",
                "Mechanical Engineering",
                "Material",
                "Quantity Serveying",
                "Architecture"
            );
        }
        
        if(comboFaculty.getValue() == "Faculty of Humanities & Sciences"){
            comboDept.getItems().clear();
            comboDept.getItems().addAll(
                "Biological",
                "English",
                "Physical Sciences",
                "Bio Technology",
                "Law",
                "Nursing",
                "Psychology"
            );
        }
        
        if(comboFaculty.getValue() == "Faculty of Hospitality & Culinary"){
            comboDept.getItems().clear();
            comboDept.getItems().addAll(
                "Hospitality Management",
                "Commercial Cookery",
                "Event Management"
            );
        }
        
        if(comboFaculty.getValue() == "Faculty of Architecture"){
            comboDept.getItems().clear();
            comboDept.getItems().addAll(
                "Main Architecture Department"
            );
        }
    }

    @FXML
    private void generateBtnPressed(ActionEvent event) throws SQLException {
             
        if(comboLevel.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("Generate Rank Error");
            alert.setHeaderText("Sorry, We can not generate the rank for lecturer ");
            alert.setContentText("Please choose the Level of the lecturer");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) 
                    {
                        System.out.println("Pressed OK.");
                    }
            }); 
        } else {
            String eid = txtEmployeeID.getText();
            String lvl = comboLevel.getValue().toString();
            String rank = lvl + "." + eid;
            txtRank.setText(rank);
        }
    }

    @FXML
    private void saveBtnPressed(ActionEvent event) throws SQLException {
        autoIncrementID();
        if(Validation() == true) {
            System.out.println("== Statement TRUE ==");
        } else {
            errLabel.setText("");
            // Add data to DB
            String name = txtLecturerName.getText();
            String eid = txtEmployeeID.getText();
            String faculty = comboFaculty.getValue().toString();
            String dept = comboDept.getValue().toString();
            String center = comboCenter.getValue().toString();
            String building = comboBuilding.getValue().toString();
            String lvl = comboLevel.getValue().toString();
            String rank = txtRank.getText();
            
            String query = "INSERT INTO lecturer ( id, lecturer_name, employee_id, faculty, department, center, building, level, rank) VALUES (?,?,?,?,?,?,?,?,?)";
            
            pst = null;
            
            int none = 0;
            
            try {
                pst = connection.prepareStatement(query);
                pst.setInt(1, AID);
                pst.setString(2, name);
                pst.setString(3, eid);
                pst.setString(4, faculty);
                pst.setString(5, dept);
                pst.setString(6, center);
                pst.setString(7, building);
                pst.setString(8, lvl);
                pst.setString(9, rank);
                
                int connectStatus = pst.executeUpdate();
                pst.close();
                
                if(connectStatus == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Lecturer");
                    alert.setHeaderText("Successfully Inserted.");
                    alert.setContentText("Lecturer Add Successfully");
                    alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) 
                        {
                            System.out.println("Pressed OK.");
                            clearBtnPressed();
                            try {
                                generateEmployeeID();
                            } catch (SQLException ex) {
                                Logger.getLogger(AddLecturerController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Lecturer Error");
                    alert.setHeaderText("Sorry, we can not add lecturer at this momoment.");
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
    private void clearBtnPressed() {
        txtLecturerName.setText("");
        comboFaculty.setValue(null);
        comboDept.setValue(null);
        comboCenter.setValue(null);
        comboBuilding.setValue(null);
        comboLevel.setValue(null);
        txtRank.setText("");
        errLabel.setText("");
    }

    @FXML
    private void backBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.addlecturerpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("ManageLecturers.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Lecturers - Resouce Management Application");
        stage.show();
    }
    
    // Validation
    public boolean Validation() {
        if("".equals(txtLecturerName.getText())) {
            errLabel.setText("* Please, fill the Lecturer Name");
            return true;
        }
        
        if(comboFaculty.getValue() == null) {
            errLabel.setText("* Please, choose the Faculty");
            return true;
        }
        
        if(comboDept.getValue() == null) {
            errLabel.setText("* Please, choose the Department");
            return true;
        }
        
        if(comboCenter.getValue() == null){
            errLabel.setText("* Please, choose the Center");
            return true;
        }
        
        if(comboBuilding.getValue() == null){
            errLabel.setText("* Please, choose the Building");
            return true;
        }
        
        if(comboLevel.getValue() == null){
            errLabel.setText("* Please, choose the Level");
            return true;
        }
        
        if("".equals(txtRank.getText())) {
            errLabel.setText("* Please, generate the Rank");
            return true;
        }
        
        return false;
    }
    
    // Load Auto Increment ID
    int AID;
    public int autoIncrementID() throws SQLException {
        String query = "SELECT id FROM lecturer ORDER BY id DESC LIMIT 1";
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
}
