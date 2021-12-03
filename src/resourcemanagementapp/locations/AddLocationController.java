/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.locations;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resourcemanagementapp.database.DBConnection;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class AddLocationController implements Initializable {

    @FXML
    private AnchorPane addlocationpane;
    @FXML
    private JFXTextField txtBuildingName;
    @FXML
    private JFXTextField txtRoomName;
    @FXML
    private JFXRadioButton rbtnLectureHall;
    @FXML
    private ToggleGroup roomType;
    @FXML
    private JFXRadioButton rbtnLaboratory;
    @FXML
    private JFXTextField txtCapacity;
    @FXML
    private Label errLbl;
    
    // DB Connection
    Connection connection = DBConnection.DBConnector();
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Radio button
        radioButton();
    }

    // Radio Button
    public void radioButton() {
        rbtnLectureHall.setToggleGroup(roomType);
        rbtnLaboratory.setToggleGroup(roomType);
    }
    
    // Load Auto Increment ID
    int AID;
    public int autoIncrementID() throws SQLException {
        String query = "SELECT id FROM location ORDER BY id DESC LIMIT 1";
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
    
    // Validation
    public boolean Validation() {
        if("".equals(txtBuildingName.getText())) {
            errLbl.setText("* Please, fill the building name");
            return true;
        }
        
        if(!(rbtnLectureHall.isSelected() || rbtnLaboratory.isSelected())) {
            errLbl.setText("* Please, pick the Room Type");
            return true;
        }
        
        if("".equals(txtCapacity.getText())) {
            errLbl.setText("* Please, fill the capacity of the room");
            return true;
        }
        
        return false;
    }

    @FXML
    private void backBtnPressed() throws IOException {
        // when user clicked Add Lecturer button
        this.addlocationpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("ManageLocations.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Locations - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void clearBtnPressed() {
        txtBuildingName.setText("");
        txtRoomName.setText("");
        rbtnLectureHall.setSelected(false);
        rbtnLaboratory.setSelected(false);
        txtCapacity.setText("");
    }

    // add location
    String rt;
    @FXML
    private void saveBtnPressed() throws SQLException {
        if(Validation() == true) {
            System.out.println("== Statement FALSE ==");
        } else {
            autoIncrementID();
            errLbl.setText("");
            // Add data to DB
            String building_name = txtBuildingName.getText();
            String room_name = txtRoomName.getText();
            if(rbtnLectureHall.isSelected()) {
                rt = "Lecture Hall";
            } else if(rbtnLaboratory.isSelected()){
                rt = "Laboratory";
            } else {
                System.out.println("Please Check Save Button -> Radio Button ");
            }
            String capacity = txtCapacity.getText();
            int capInt = Integer.parseInt(capacity);
            
            
            String query = "INSERT INTO location (id, building_name, room_name, room_type, capacity) VALUES (?,?,?,?,?)";
            
            pst = null;
            
            try {
                pst = connection.prepareStatement(query);
                pst.setInt(1, AID);
                pst.setString(2, building_name);
                pst.setString(3, room_name);
                pst.setString(4, rt);
                pst.setInt(5, capInt);
                 
                int connectStatus = pst.executeUpdate();
                pst.close();
                
                if(connectStatus == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Location");
                    alert.setHeaderText("Successfully Inserted.");
                    alert.setContentText("Location Add Successfully");
                    alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) 
                        {
                            System.out.println("Pressed OK.");
                            clearBtnPressed();                        
                        }
                    });
                    
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Location Error");
                    alert.setHeaderText("Sorry, we can not add Location at this momoment.");
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
    
}
