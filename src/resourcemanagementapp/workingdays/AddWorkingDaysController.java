/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.workingdays;

import com.jfoenix.controls.JFXCheckBox;
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
import resourcemanagementapp.subjects.ManageSubjectsController;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class AddWorkingDaysController implements Initializable {

    @FXML
    private AnchorPane addworkingdayspane;
    @FXML
    private Spinner spnNoWorkingDays;
    @FXML
    private Spinner spnHours;
    @FXML
    private Spinner spnMinutes;
    @FXML
    private JFXCheckBox chkMonday;
    @FXML
    private JFXCheckBox chkTuesday;
    @FXML
    private JFXCheckBox chkWednesday;
    @FXML
    private JFXCheckBox chkThursday;
    @FXML
    private JFXCheckBox chkFriday;
    @FXML
    private JFXCheckBox chkStaurday;
    @FXML
    private JFXCheckBox chkSunday;
    @FXML
    private Label errLbl;
    
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
        // Spinner
        spinnerValue();
        
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(AddWorkingDaysController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Spinner Value factory
    public void spinnerValue() {
        SpinnerValueFactory<Integer> NoWorkingDays = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 0);
        spnNoWorkingDays.setValueFactory(NoWorkingDays);
        SpinnerValueFactory<Integer> WorkingHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, 0);
        spnHours.setValueFactory(WorkingHours);
        SpinnerValueFactory<Integer> WorkingMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300);
        spnMinutes.setValueFactory(WorkingMinutes);
    }
    
    // Validation
    public boolean Validation() {
        if (!(chkMonday.isSelected() || chkTuesday.isSelected() || chkWednesday.isSelected()|| chkThursday.isSelected() || chkFriday.isSelected() || chkStaurday.isSelected() || chkSunday.isSelected())) {
            errLbl.setText("* Please, Atleast choose a working day");
            return true;
        }

        return false;
    }
    
    // Clear
    public void clearLabel() {
        errLbl.setText("");
    }
    
    // load data
    public void loadData() throws SQLException {
        String query = "SELECT * FROM add_working_days";
        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();
        rs.next();
        spnNoWorkingDays.getValueFactory().setValue(rs.getInt(2));
        spnHours.getValueFactory().setValue(rs.getInt(3));
        spnMinutes.getValueFactory().setValue(rs.getInt(4));
        
        boolean monday_result = rs.getBoolean(5);
        boolean tuesday_result = rs.getBoolean(6);
        boolean wednesday_result = rs.getBoolean(7);
        boolean thursday_result = rs.getBoolean(8);
        boolean friday_result = rs.getBoolean(9);
        boolean saturday_result = rs.getBoolean(10);
        boolean sunday_result = rs.getBoolean(11);
        
        chkMonday.setSelected(monday_result);
        chkTuesday.setSelected(tuesday_result);
        chkWednesday.setSelected(wednesday_result);
        chkThursday.setSelected(thursday_result);
        chkFriday.setSelected(friday_result);
        chkStaurday.setSelected(saturday_result);
        chkSunday.setSelected(sunday_result);
        
        pst.close();
        rs.close();
    }

    @FXML
    private void backBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.addworkingdayspane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/resourcemanagementapp/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void deleteBtnPressed(ActionEvent event) throws SQLException {
        
        String query = "DELETE FROM add_working_days WHERE id="+1;
        pst = connection.prepareStatement(query);
        
        int connectStatus = pst.executeUpdate();
        pst.close();
        if(connectStatus == 1) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            // Get the Stage.
            Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert1.setTitle("Delete Working Days");
            alert1.setHeaderText("Successfully Deleted");
            alert1.setContentText(" successfully deleted from system");
            alert1.showAndWait().ifPresent(rs1 -> {
            if (rs1 == ButtonType.OK) 
                {
                    clearLabel();
                    spinnerValue();
                    chkMonday.setSelected(false);
                    chkTuesday.setSelected(false);
                    chkWednesday.setSelected(false);
                    chkThursday.setSelected(false);
                    chkFriday.setSelected(false);
                    chkStaurday.setSelected(false);
                    chkSunday.setSelected(false);
                    
                }
            });
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert2.setTitle("Delete Working Days Error");
            alert2.setHeaderText("Unsuccessful Deletion");
            alert2.setContentText("Sorry, can not delete from system");
            alert2.showAndWait().ifPresent(rs2 -> {
            if (rs2 == ButtonType.OK) 
                {
                }
            });
        }
    }

    @FXML
    private void updateBtnPressed() throws SQLException {
        
        String query = "SELECT * FROM add_working_days";
        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();
        if(rs.next()) {
            if(Validation() == true) {
                System.out.println("Validation Error");
            } else {
                clearLabel();

                int nwd = (int) spnNoWorkingDays.getValue();
                int hours = (int) spnHours.getValue();
                int min = (int) spnMinutes.getValue();

                boolean monday_result = chkMonday.isSelected();
                boolean tuesday_result = chkTuesday.isSelected();
                boolean wednesday_result = chkWednesday.isSelected();
                boolean thursday_result = chkThursday.isSelected();
                boolean friday_result = chkFriday.isSelected();
                boolean saturday_result = chkStaurday.isSelected();
                boolean sunday_result = chkSunday.isSelected();

                String query_update = "UPDATE add_working_days SET id=?, no_working_days=?, hours=?, minutes=?, monday=?, tuesday=?, wednesday=?, thursday=?, friday=?, saturday=?, sunday=? WHERE id="+1;
                pst = connection.prepareStatement(query_update);
                pst.setInt(1, 1);
                pst.setInt(2, nwd);
                pst.setInt(3, hours);
                pst.setInt(4, min);
                pst.setBoolean(5, monday_result);
                pst.setBoolean(6, tuesday_result);
                pst.setBoolean(7, wednesday_result);
                pst.setBoolean(8, thursday_result);
                pst.setBoolean(9, friday_result);
                pst.setBoolean(10, saturday_result);
                pst.setBoolean(11, sunday_result);
                
                int result = pst.executeUpdate();
                pst.close();

                if(result == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Resource Management Application");
                    alert.setHeaderText("Update Woking Days Succesfull");
                    alert.setContentText("Successfully update working days");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                    loadData();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Resource Management Application");
                    alert.setHeaderText("Update Working Days Error");
                    alert.setContentText("Can not update's Data at this moment, please try again later.");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                }
            } 
        } else {
            int nwd = (int) spnNoWorkingDays.getValue();
            int hours = (int) spnHours.getValue();
            int min = (int) spnMinutes.getValue();

            boolean monday_result = chkMonday.isSelected();
            boolean tuesday_result = chkTuesday.isSelected();
            boolean wednesday_result = chkWednesday.isSelected();
            boolean thursday_result = chkThursday.isSelected();
            boolean friday_result = chkFriday.isSelected();
            boolean saturday_result = chkStaurday.isSelected();
            boolean sunday_result = chkSunday.isSelected();
            
            clearLabel();
                String query_insert = "INSERT INTO add_working_days (id, no_working_days, hours, minutes, monday, tuesday, wednesday, thursday, friday, saturday, sunday) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                pst = connection.prepareStatement(query_insert);
                pst.setInt(1, 1);
                pst.setInt(2, nwd);
                pst.setInt(3, hours);
                pst.setInt(4, min);
                pst.setBoolean(5, monday_result);
                pst.setBoolean(6, tuesday_result);
                pst.setBoolean(7, wednesday_result);
                pst.setBoolean(8, thursday_result);
                pst.setBoolean(9, friday_result);
                pst.setBoolean(10, saturday_result);
                pst.setBoolean(11, sunday_result);
                
                int result = pst.executeUpdate();
                pst.close();
                if(result == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Resource Management Application");
                    alert.setHeaderText("Add Woking Days Succesfull");
                    alert.setContentText("Successfully Inserted working days");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                    loadData();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Resource Management Application");
                    alert.setHeaderText("Add Working Days Error");
                    alert.setContentText("Can not Add Data at this moment, please try again later.");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                }
                
            pst.close();
            rs.close();
        }
    }
    
}
