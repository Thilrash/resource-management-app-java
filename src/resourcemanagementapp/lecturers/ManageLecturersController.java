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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resourcemanagementapp.DashboardController;
import resourcemanagementapp.database.DBConnection;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class ManageLecturersController implements Initializable {
    
    // DB Connection
    Connection connection = DBConnection.DBConnector();
    ObservableList<Lecturer> lecturer_data = FXCollections.observableArrayList();
    FilteredList<Lecturer> filteredData = new FilteredList<>(lecturer_data,e->true);
    
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    @FXML
    private TableView<Lecturer> lecturerTable;
    @FXML
    private TableColumn<?, ?> ColoumnLecturerID;
    @FXML
    private TableColumn<?, ?> ColoumnLecturerName;
    @FXML
    private TableColumn<?, ?> ColoumnEmployeeID;
    @FXML
    private TableColumn<?, ?> ColoumnFaculty;
    @FXML
    private TableColumn<?, ?> ColoumnLevel;
    @FXML
    private JFXTextField txtLecturerName;
    @FXML
    private JFXComboBox comboFaculty;
    @FXML
    private JFXTextField txtEmployeeID;
    @FXML
    private JFXComboBox comboDept;
    @FXML
    private JFXTextField txtRank;
    @FXML
    private JFXComboBox comboBuilding;
    @FXML
    private JFXComboBox comboCenter;
    @FXML
    private JFXComboBox comboLevel;
    @FXML
    private AnchorPane managelecturerpane;
    @FXML
    private Label errLabel;
    
    DashboardController dc;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Adding Items to combo box
        comboFaculty.getItems().addAll("Faculty of Computing","Faculty of Business","Faculty of Engineering","Faculty of Humanities & Sciences","Faculty of Architecture","Faculty of Hospitality & Culinary");
        comboCenter.getItems().addAll("Malabe", "Colombo", "Matara", "Kandy", "Kurunagala", "Jaffna");
        comboBuilding.getItems().addAll("Main Building","New Building","A-Block","B-Block","C-Block", "D-Block", "Center Block");
        comboLevel.getItems().addAll("1","2","3","4","5","6");
        // Display and Load Table
        ColoumnLecturerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColoumnLecturerName.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));
        ColoumnEmployeeID.setCellValueFactory(new PropertyValueFactory<>("emloyeeID"));
        ColoumnFaculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        ColoumnLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        try {
            loadLecturerData();
        } catch (SQLException ex) {
            Logger.getLogger(ManageLecturersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Department Combo Box Show
    @FXML
    public void showDeptCombo() {
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

    // Load Lecturer Table
    public void loadLecturerData() throws SQLException {
        String query = "SELECT * FROM lecturer";
        try {
            lecturer_data.clear();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                lecturer_data.add(new Lecturer(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9)
                        
                ));
                lecturerTable.setItems(lecturer_data);
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    // View the data when selected from the table
    @FXML
    public void showOnClick() throws SQLException {
        Lecturer lecturer = lecturerTable.getSelectionModel().getSelectedItem();
        txtLecturerName.setText(lecturer.getLecturerName());
        txtEmployeeID.setText(lecturer.getEmloyeeID());
        comboFaculty.setValue(lecturer.getFaculty());
        comboDept.setValue(lecturer.getDepartment());
        comboCenter.setValue(lecturer.getCenter());
        comboBuilding.setValue(lecturer.getBuilding());
        comboLevel.setValue(lecturer.getLevel());
        txtRank.setText(lecturer.getRank());
        errLabel.setText("");
    }
    
    // Update the lecturer
    @FXML
    private void updateBtnPressed(ActionEvent event) {
        Lecturer lecturer = lecturerTable.getSelectionModel().getSelectedItem();
        if(lecturerTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Resource Management Application");
            alert.setHeaderText("Update Lecturer Error");
            alert.setContentText("Please, Select Lecturer From The Table");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        } else {
            if(Validation() == true) {
                System.out.println("Validation Error");
            } 
            else if(txtRank.getText() == null ? comboLevel.getValue().toString()+"."+txtEmployeeID.getText() != null : !txtRank.getText().equals(comboLevel.getValue().toString()+"."+txtEmployeeID.getText())) {
                System.out.println(comboLevel.getValue().toString());
                System.out.println(txtEmployeeID.getText());
                System.out.println(comboLevel.getValue().toString()+"."+txtEmployeeID.getText());
                errLabel.setText("* Rank miss matched, please check the rank filed...");
            } else {
                errLabel.setText("");
                try {
                    String query = "UPDATE lecturer SET id=?, lecturer_name=?, employee_id=?, faculty=?, department=?, center=?, building=?, level=?, rank=? WHERE id='"+lecturer.getId()+"'";
                    pst = connection.prepareStatement(query);
                    
                    pst.setInt(1, lecturer.getId());
                    pst.setString(2, txtLecturerName.getText());
                    pst.setString(3, txtEmployeeID.getText());
                    pst.setString(4, comboFaculty.getValue().toString());
                    pst.setString(5, comboDept.getValue().toString());
                    pst.setString(6, comboCenter.getValue().toString());
                    pst.setString(7, comboBuilding.getValue().toString());
                    pst.setString(8, comboLevel.getValue().toString());
                    pst.setString(9, txtRank.getText());
                    
                    int result = pst.executeUpdate();
                    
                    pst.close();
                    
                    if(result == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Lecturer Succesfull");
                        alert.setContentText("Successfully update "+txtLecturerName.getText()+ "'s Data");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alert.showAndWait();
                        clearBtnPressed();
                        loadLecturerData();
                        
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Lecturer Error");
                        alert.setContentText("Can not update "+txtLecturerName.getText()+ "'s Data at this moment, please try again later.");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    // Delete the leturer
    String name = "";
    @FXML
    private void deleteBtnPressed(ActionEvent event) throws SQLException {
        Lecturer leturerTemp = lecturerTable.getSelectionModel().getSelectedItem();
        name = leturerTemp.getLecturerName();
        
        if(lecturerTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("Delete Lecturer Error");
            alert.setHeaderText("Lecture selection from the table is required!");
            alert.setContentText("Please select the lecturer, then try again!");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) 
                {
                    System.out.println("Pressed OK.");
                    clearBtnPressed();
                try {
                    loadLecturerData();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageLecturersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            });
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                // Get the Stage.
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                alert.setTitle("Delete Lecturer");
                alert.setHeaderText("Confirmation To Delete Lecture");
                alert.setContentText("Do you wnat to delete "+name+ " Lecturer Reocord ? Ok to continue...");
                alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        Lecturer lecturer  = lecturerTable.getSelectionModel().getSelectedItem();
                        String query = "DELETE FROM lecturer WHERE id = ?";
                        pst = connection.prepareStatement(query);
                        pst.setInt(1, lecturer.getId());
                        name = lecturer.getLecturerName();
                        int connectStatus = pst.executeUpdate();
                        pst.close();


                        if(connectStatus == 1) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            // Get the Stage.
                            Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert1.setTitle("Delete Lecturer");
                            alert1.setHeaderText("Successfully Deleted");
                            alert1.setContentText(name + " successfully deleted from system");
                            alert1.showAndWait().ifPresent(rs1 -> {
                            if (rs1 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed();
                                try {
                                    loadLecturerData();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ManageLecturersController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                }
                            });
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            // Get the Stage.
                            Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert2.setTitle("Delete Lecturer Error");
                            alert2.setHeaderText("Unsuccessful Deletion");
                            alert2.setContentText(name + " can not delete from system");
                            alert2.showAndWait().ifPresent(rs2 -> {
                            if (rs2 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed();
                                try {
                                    loadLecturerData();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ManageLecturersController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void addBtnPressed() throws IOException {
        // when user clicked Add Lecturer button
        this.managelecturerpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("AddLecturer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Lecturer - Resource Management Application");
        stage.setMaximized(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
    
    
    @FXML
    private void generateBtnPressed(ActionEvent event) throws SQLException {
             
        if(comboLevel.getValue() == null || "".equals(txtEmployeeID.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("Generate Rank Error");
            alert.setHeaderText("Sorry, We can not generate the rank for lecturer ");
            alert.setContentText("Please choose the Level or Employee ID for the lecturer");
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
        
        errLabel.setText("");
    }
    
    @FXML
    private void clearBtnPressed() {
        txtLecturerName.setText("");
        txtEmployeeID.setText("");
        comboFaculty.setValue(null);
        comboDept.setValue(null);
        comboCenter.setValue(null);
        comboBuilding.setValue(null);
        comboLevel.setValue(null);
        txtRank.setText("");
        errLabel.setText("");
        lecturerTable.getSelectionModel().clearSelection();
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

    @FXML
    private void backBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.managelecturerpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/resourcemanagementapp/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard - Resouce Management Application");
        // stage.setMaximized(true);
        stage.show();
    }
}
