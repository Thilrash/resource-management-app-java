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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resourcemanagementapp.database.DBConnection;
import resourcemanagementapp.subjects.ManageSubjectsController;
import resourcemanagementapp.subjects.Subject;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class ManageLocationsController implements Initializable {

    @FXML
    private AnchorPane managelocationspane;
    @FXML
    private TableView<Location> LocationTable;
    @FXML
    private TableColumn<?, ?> ColoumnLocationID;
    @FXML
    private TableColumn<?, ?> ColoumnBuilding;
    @FXML
    private TableColumn<?, ?> ColoumnRoom;
    @FXML
    private TableColumn<?, ?> ColoumnRoomType;
    @FXML
    private TableColumn<?, ?> ColoumnCapacity;
    @FXML
    private JFXTextField txtBuildingName;
    @FXML
    private JFXTextField txtRoomName;
    @FXML
    private ToggleGroup roomType;
    @FXML
    private JFXRadioButton rbtnLectureHall;
    @FXML
    private JFXRadioButton rbtnLaboratory;
    @FXML
    private JFXTextField txtCapacity;
    @FXML
    private Label errLbl;
    
    // DB Connection
    Connection connection = DBConnection.DBConnector();
    ObservableList<Location> location_data = FXCollections.observableArrayList();
    FilteredList<Location> filteredData = new FilteredList<>(location_data,e->true);
    
    PreparedStatement pst = null;
    ResultSet rs = null;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // radio button
        radioButton();
        
        // Display and Load Table
        ColoumnLocationID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColoumnBuilding.setCellValueFactory(new PropertyValueFactory<>("building_name"));
        ColoumnRoom.setCellValueFactory(new PropertyValueFactory<>("room_name"));
        ColoumnRoomType.setCellValueFactory(new PropertyValueFactory<>("room_type"));
        ColoumnCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        
        // load Table Method
        loadLocatiionData();
    } 
    
    // Radio Button
    public void radioButton() {
        rbtnLectureHall.setToggleGroup(roomType);
        rbtnLaboratory.setToggleGroup(roomType);
    }
    
    // load location datas' from DB
    public void loadLocatiionData() {
        String query = "SELECT * FROM location";
        try {
            location_data.clear();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                location_data.add(new Location(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getInt(5)   
                ));
                LocationTable.setItems(location_data);
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Validation
    public boolean Validation() {
        if("".equals(txtBuildingName.getText())) {
            errLbl.setText("* Please, fill the building name");
            return true;
        }
        
        if("".equals(txtRoomName.getText())) {
            errLbl.setText("* Please, fill the Room Name");
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
    private void backBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.managelocationspane.getScene().getWindow().hide();
        
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
        Location location = LocationTable.getSelectionModel().getSelectedItem();
        txtBuildingName.setText(location.getBuilding_name());
        txtRoomName.setText(location.getRoom_name());
        String rt = location.getRoom_type();
        if("Lecture Hall".equals(rt)) {
            rbtnLectureHall.setSelected(true);
        } else {
            rbtnLaboratory.setSelected(true);
        }
        String cap = String.valueOf(location.getCapacity());
        txtCapacity.setText(cap);
    }

    @FXML
    private void clearBtnPressed() {
        LocationTable.getSelectionModel().clearSelection();
        txtBuildingName.setText("");
        txtRoomName.setText("");
        rbtnLectureHall.setSelected(false);
        rbtnLaboratory.setSelected(false);
        txtCapacity.setText("");
    }

    @FXML
    private void addBtnPressed() throws IOException {
        // when user clicked Add Lecturer button
        this.managelocationspane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("AddLocation.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Location - Resouce Management Application");
        stage.setMaximized(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    // Delete Location
    String rn;
    @FXML
    private void deleteBtnPressed() {
        Location location = LocationTable.getSelectionModel().getSelectedItem();
        rn = location.getRoom_name();
        
        if(LocationTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("Delete Location Error");
            alert.setHeaderText("Location selection from the table is required!");
            alert.setContentText("Please select the Location, then try again!");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) 
                {
                    System.out.println("Pressed OK.");
                    clearBtnPressed();
                    loadLocatiionData();
                }
            });
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                // Get the Stage.
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                alert.setTitle("Delete Location");
                alert.setHeaderText("Confirmation To Delete Location");
                alert.setContentText("Do you wnat to delete "+rn+ " Location Reocord ? Ok to continue...");
                alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        String query = "DELETE FROM location WHERE id = ?";
                        pst = connection.prepareStatement(query);
                        pst.setInt(1, location.getId());
                        rn = location.getRoom_name();
                        int connectStatus = pst.executeUpdate();
                        pst.close();


                        if(connectStatus == 1) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            // Get the Stage.
                            Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert1.setTitle("Delete Location");
                            alert1.setHeaderText("Successfully Deleted");
                            alert1.setContentText(rn + " successfully deleted from system");
                            alert1.showAndWait().ifPresent(rs1 -> {
                            if (rs1 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed();
                                    loadLocatiionData();
                                }
                            });
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            // Get the Stage.
                            Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert2.setTitle("Delete Location Error");
                            alert2.setHeaderText("Unsuccessful Deletion");
                            alert2.setContentText(rn + " can not delete from system");
                            alert2.showAndWait().ifPresent(rs2 -> {
                            if (rs2 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed();
                                    loadLocatiionData();
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

    // Update
    String loc;
    @FXML
    private void updateBtnPressed() {
        Location location = LocationTable.getSelectionModel().getSelectedItem();
        if(LocationTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Resource Management Application");
            alert.setHeaderText("Update Location Error");
            alert.setContentText("Please, Select Location From The Table");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        } else {
            if(Validation() == true) {
                System.out.println("Validation Error");
            } else {
                errLbl.setText("");
                try {
                    
                    // Add data to DB
                    String building_name = txtBuildingName.getText();
                    String room_name = txtRoomName.getText();
                    if(rbtnLectureHall.isSelected()) {
                        loc = "Lecture Hall";
                    } else if(rbtnLaboratory.isSelected()){
                        loc = "Laboratory";
                    } else {
                        System.out.println("Please Check Save Button -> Radio Button ");
                    }
                    String capacity = txtCapacity.getText();
                    int capInt = Integer.parseInt(capacity);
                    
                    String query = "UPDATE location SET id=?, building_name=?, room_name=?, room_type=?, capacity=? WHERE id='"+location.getId()+"'";
                    pst = connection.prepareStatement(query);
                    
                    pst.setInt(1, location.getId());
                    pst.setString(2, building_name);
                    pst.setString(3, room_name);
                    pst.setString(4, loc);
                    pst.setInt(5, capInt);
                    
                    int result = pst.executeUpdate();
                    
                    pst.close();
                    
                    if(result == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Location Succesfull");
                        alert.setContentText("Successfully update "+txtRoomName.getText()+ "'s Data");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alert.showAndWait();
                        clearBtnPressed();
                        loadLocatiionData();  
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Location Error");
                        alert.setContentText("Can not update "+txtRoomName.getText()+ "'s Data at this moment, please try again later.");
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
