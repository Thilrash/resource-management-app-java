/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.sessions;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import resourcemanagementapp.database.DBConnection;
import resourcemanagementapp.lecturers.ManageLecturersController;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class ManageSessionsController implements Initializable {

    @FXML
    private AnchorPane managesessionspane;
    @FXML
    private TableView<Session> SessionTable;
    @FXML
    private TableColumn<?, ?> ColoumnSessionID;
    @FXML
    private TableColumn<?, ?> ColoumnLecturer1;
    @FXML
    private TableColumn<?, ?> ColoumnLecturer2;
    @FXML
    private TableColumn<?, ?> ColoumnSubjectCode;
    @FXML
    private TableColumn<?, ?> ColoumnSubjectName;
    @FXML
    private TableColumn<?, ?> ColoumnGroupID;
    @FXML
    private TableColumn<?, ?> ColoumnTag;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXComboBox comboFilter;
    

    ObservableList<Session> session_data = FXCollections.observableArrayList();
    FilteredList<Session> filteredData = new FilteredList<>(session_data,e->true);
    Connection connection = DBConnection.DBConnector();
    PreparedStatement pst = null;
    ResultSet rs = null;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private Label lblError;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblError.setText("");
        comboFilter.getItems().addAll("Lecturer","Subject Code","Subject Name","Tag","Year");
        
        // Display and Load Table
        ColoumnSessionID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColoumnLecturer1.setCellValueFactory(new PropertyValueFactory<>("lecturer1"));
        ColoumnLecturer2.setCellValueFactory(new PropertyValueFactory<>("lecturer2"));
        ColoumnSubjectCode.setCellValueFactory(new PropertyValueFactory<>("subject_code"));
        ColoumnSubjectName.setCellValueFactory(new PropertyValueFactory<>("subject_name"));
        ColoumnGroupID.setCellValueFactory(new PropertyValueFactory<>("group_id"));
        ColoumnTag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        
        try {
            loadSessionData();
        } catch (SQLException ex) {
            Logger.getLogger(ManageSessionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    // Load session data
    public void loadSessionData() throws SQLException {
        String query = "select * from session";
        try {
            session_data.clear();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()) {
                session_data.add(new Session(
                    rs.getInt(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getString(4), 
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
                ));
                SessionTable.setItems(session_data);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            pst.close();
            rs.close();
        }
    }

    @FXML
    private void backBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.managesessionspane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/resourcemanagementapp/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard - Resouce Management Application");
        // stage.setMaximized(true);
        stage.show();
    }

    @FXML
    private void addSessionBtnPressed(ActionEvent event) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("AddSession.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Session - Resource Management Application");
        stage.setMaximized(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    private void viewBtnPressed(ActionEvent event) throws IOException, SQLException {
        
        if(SessionTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("View Session Error");
            alert.setHeaderText("Session selection from the table is required!");
            alert.setContentText("Please select the session, then try again!");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) 
                {
                    System.out.println("Pressed OK.");
                try {
                    loadSessionData();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageLecturersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            });
        } else {      
            Session session = SessionTable.getSelectionModel().getSelectedItem();
            int tempSessionID =  session.getId();
            
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("ViewSession.fxml"));
            try{
                Loader.load();
            } catch(IOException e) {
                Logger.getLogger(ManageSessionsController.class.getName()).log(Level.SEVERE, null, e);
            }
            ViewSessionController vsc = Loader.getController();
            vsc.setSessionID(tempSessionID);
            Parent parent = Loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setMaximized(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
        }
        
    }
    
    String sname = "";
    @FXML
    private void deleteBtnPressed() {
        
        if(SessionTable.getSelectionModel().isEmpty()) {
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
                // clearBtnPressed();
                //loadSessionData();
                }
            });
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                // Get the Stage.
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                alert.setTitle("Delete Session");
                alert.setHeaderText("Confirmation To Delete Session");
                alert.setContentText("Do you wnat to delete "+sname+ " Session Reocord ? Ok to continue...");
                alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        Session session = SessionTable.getSelectionModel().getSelectedItem();
                        String query = "DELETE FROM session WHERE id = ?";
                        pst = connection.prepareStatement(query);
                        pst.setInt(1, session.getId());
                        sname = session.getSubject_name();
                        int connectStatus = pst.executeUpdate();

                        if(connectStatus == 1) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            // Get the Stage.
                            Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert1.setTitle("Delete Session");
                            alert1.setHeaderText("Successfully Deleted");
                            alert1.setContentText(sname + " successfully deleted from system");
                            alert1.showAndWait().ifPresent(rs1 -> {
                            if (rs1 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                try {
                                    //clearBtnPressed();
                                    loadSessionData();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ManageSessionsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                }
                            });
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            // Get the Stage.
                            Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert2.setTitle("Delete Session Error");
                            alert2.setHeaderText("Unsuccessful Deletion");
                            alert2.setContentText(sname + " can not delete from system");
                            alert2.showAndWait().ifPresent(rs2 -> {
                            if (rs2 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    //clearBtnPressed();
                                
                                }
                            });
                        } 
                } catch (SQLException e) {
                    System.out.println(e);
                } finally {}
                    try {
                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ManageSessionsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } 
                });
            } catch (Exception e) {
                System.out.println(e);
            } 
        }
    }

    @FXML
    private void searchBtnPressed() {
        lblError.setText("");
        if(comboFilter.getValue() == null) {
            lblError.setText("*! Please Select or Choose The Search Type");
        } else {
            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Session>)sd->{
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(comboFilter.getValue() == "Lecturer") {
                    if(sd.getLecturer1().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (sd.getLecturer2().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        System.out.println("No Lecturers");
                    }
                } else if (comboFilter.getValue() == "Subject Code") {
                    if(sd.getSubject_code().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        System.out.println("No Subject Code");
                    }
                } else if (comboFilter.getValue() == "Subject Name") {
                    if(sd.getSubject_name().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        System.out.println("No Subject Name");
                    }
                } else if (comboFilter.getValue() == "Tag") {
                    if(sd.getTag().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        System.out.println("No Tag");
                    }
                } else if (comboFilter.getValue() == "Year") {
                    if(sd.getGroup_id().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        System.out.println("No Year");
                    }
                } else {
                    lblError.setText("*! Please Select or Choose The Search Type");
                }
                return false;
                });
            });
            SortedList<Session> sortedData=new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(SessionTable.comparatorProperty());
            SessionTable.setItems(sortedData);
        }
    }

    @FXML
    private void refreshBtnPressed(ActionEvent event) throws SQLException {
        txtSearch.clear();
        comboFilter.setValue(null);
        lblError.setText("");
        loadSessionData();
    }

    @FXML
    private void updateBtnPressed(ActionEvent event) throws SQLException {
        if(SessionTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("Update Session Error");
            alert.setHeaderText("Session selection from the table is required!");
            alert.setContentText("Please select the session, then try again!");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) 
                {
                    System.out.println("Pressed OK.");
                try {
                    loadSessionData();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageLecturersController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            });
        } else {      
            Session session = SessionTable.getSelectionModel().getSelectedItem();
            int tempSessionID =  session.getId();
            
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("UpdateSession.fxml"));
            try{
                Loader.load();
            } catch(IOException e) {
                Logger.getLogger(ManageSessionsController.class.getName()).log(Level.SEVERE, null, e);
            }
            UpdateSessionController usc = Loader.getController();
            usc.setSessionID(tempSessionID);
            Parent parent = Loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setMaximized(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
        }
    }
    
    public int passSessionID () {
        Session session = SessionTable.getSelectionModel().getSelectedItem();
        int tempSessionID =  session.getId();
        return tempSessionID;
    }
}
