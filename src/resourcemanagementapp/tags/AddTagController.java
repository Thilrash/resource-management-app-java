/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.tags;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resourcemanagementapp.database.DBConnection;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class AddTagController implements Initializable {

    @FXML
    private AnchorPane addtagpane;
    @FXML
    private JFXTextField txtTagName;
    @FXML
    private JFXTextField txtTagCode;
    @FXML
    private JFXComboBox comboRelatedTag;
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
        // Radio button
        relatedTagRadioBtn();
    }  
    
    // Related tag combo box
    public void relatedTagRadioBtn() {
        comboRelatedTag.getItems().clear();
        comboRelatedTag.getItems().addAll("Lecture", "Tutorial", "Practical");
    }
    
    // Load Auto Increment ID
    int AID;
    public int autoIncrementID() throws SQLException {
        String query = "SELECT id FROM tag ORDER BY id DESC LIMIT 1";
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
    
    // Validation
    public boolean Validation() {
        
        if("".equals(txtTagName.getText())) {
            errLbl.setText("* Please, fill the Tag Name");
            return true;
        }
        
        if("".equals(txtTagCode.getText())) {
            errLbl.setText("* Please, fill the Tag Code");
            return true;
        }
        
        if(comboRelatedTag.getValue() == null) {
            errLbl.setText("* Please, choose the Rlated Tag");
            return true;
        }
        
        return false;
    }

    @FXML
    private void backBtnPressed() throws IOException {
        // when user clicked Add Lecturer button
        this.addtagpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("ManageTags.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Tags - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void clearBtnPressed() {
        txtTagName.setText("");
        txtTagCode.setText("");
        comboRelatedTag.setValue(null);
        errLbl.setText("");
    }

    @FXML
    private void saveBtnPressed() throws SQLException {
        if(Validation() == true) {
            System.out.println("== Statement FALSE ==");
        } else {
            autoIncrementID();
            errLbl.setText("");
            // Add data to DB
            String tagName = txtTagName.getText();
            String tagCode = txtTagCode.getText();
            String relatedTag = comboRelatedTag.getValue().toString();
            
            
            String query = "INSERT INTO tag (id, tag_name, tag_code, related_tag) VALUES (?,?,?,?)";
            
            pst = null;
            
            try {
                pst = connection.prepareStatement(query);
                pst.setInt(1, AID);
                pst.setString(2, tagName);
                pst.setString(3, tagCode);
                pst.setString(4, relatedTag);
                 
                int connectStatus = pst.executeUpdate();
                pst.close();
                
                if(connectStatus == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Tag");
                    alert.setHeaderText("Successfully Inserted.");
                    alert.setContentText("Tag Add Successfully");
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
                    alert.setTitle("Add Tag Error");
                    alert.setHeaderText("Sorry, we can not add Tag at this momoment.");
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
