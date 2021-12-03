/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.subjects;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resourcemanagementapp.database.DBConnection;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class ManageSubjectsController implements Initializable {

    @FXML
    private AnchorPane managesubjectpane;
    @FXML
    private JFXComboBox comboOfferedYear;
    @FXML
    private JFXRadioButton rbtnFirstSem;
    @FXML
    private JFXRadioButton rbtnSecondSem;
    @FXML
    private JFXTextField txtSubjectName;
    @FXML
    private JFXTextField txtSubjectCode;
    @FXML
    private TableView<Subject> SubjectTable;
    @FXML
    private TableColumn<?, ?> ColoumnSubjectID;
    @FXML
    private TableColumn<?, ?> ColoumnSubjectName;
    @FXML
    private TableColumn<?, ?> ColoumnSubjectCode;
    @FXML
    private TableColumn<?, ?> ColoumnOfferedYear;
    @FXML
    private TableColumn<?, ?> ColoumnOfferedSem;
    @FXML
    private Spinner spnLectureHours;
    @FXML
    private Spinner spnTutorialHours;
    @FXML
    private Spinner spnLabHours;
    @FXML
    private Spinner spnEvaHours;
    @FXML
    private ToggleGroup semesterGroup;
    @FXML
    private Label errLbl;
    
    // DB Connection
    Connection connection = DBConnection.DBConnector();
    ObservableList<Subject> subject_data = FXCollections.observableArrayList();
    FilteredList<Subject> filteredData = new FilteredList<>(subject_data,e->true);
    
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Spinner Value
        spinnerValue();
        
        //Radio Button
        radioButtonGroup();
        
        // Combo box 
        showOfferedYear();
        
        // Display and Load Table
        ColoumnSubjectID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColoumnSubjectName.setCellValueFactory(new PropertyValueFactory<>("subject_name"));
        ColoumnSubjectCode.setCellValueFactory(new PropertyValueFactory<>("subject_code"));
        ColoumnOfferedYear.setCellValueFactory(new PropertyValueFactory<>("offered_year"));
        ColoumnOfferedSem.setCellValueFactory(new PropertyValueFactory<>("offered_semester"));
        
        try {
            // Load Table Method
            LoadSubjectData();
        } catch (SQLException ex) {
            Logger.getLogger(ManageSubjectsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Load Data From The Database
    public void LoadSubjectData()throws SQLException {
        String query = "SELECT * FROM subject";
        try {
            subject_data.clear();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                subject_data.add(new Subject(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9)
                        
                ));
                SubjectTable.setItems(subject_data);
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    // Validation
    public boolean Validation() {
        if(comboOfferedYear.getValue() == null) {
            errLbl.setText("* Please, choose the offered Year");
            return true;
        }
        
        if(!(rbtnFirstSem.isSelected() || rbtnSecondSem.isSelected())) {
            errLbl.setText("* Please, pick the Semester");
            return true;
        }
        
        if("".equals(txtSubjectName.getText())) {
            errLbl.setText("* Please, fill the Subject Name");
            return true;
        }
        
        if("".equals(txtSubjectCode.getText())) {
            errLbl.setText("* Please, fill the Subject Code");
            return true;
        }
        
        return false;
    }

    @FXML
    private void backBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.managesubjectpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/resourcemanagementapp/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard - Resouce Management Application");
        stage.show();
    }

    private void showOfferedYear() {
        comboOfferedYear.getItems().clear();
        comboOfferedYear.getItems().addAll("1","2","3","4");
    }

    public void radioButtonGroup() {
        ToggleGroup semesterGroup = new ToggleGroup();
        rbtnFirstSem.setToggleGroup(semesterGroup);
        rbtnSecondSem.setToggleGroup(semesterGroup);
       
    }
    
    public void spinnerValue() {
        SpinnerValueFactory<Integer> LHValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, 0);
        spnLectureHours.setValueFactory(LHValueFactory);
        SpinnerValueFactory<Integer> THValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, 0);
        spnTutorialHours.setValueFactory(THValueFactory);
        SpinnerValueFactory<Integer> LaHValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300);
        spnLabHours.setValueFactory(LaHValueFactory);
        SpinnerValueFactory<Integer> EHValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 300, 0);
        spnEvaHours.setValueFactory(EHValueFactory);
    }

    @FXML
    private void showOnClick() {
        Subject subject = SubjectTable.getSelectionModel().getSelectedItem();
        comboOfferedYear.setValue(subject.getOffered_year());
        String sem = subject.getOffered_semester();
        if("1".equals(sem)) {
            rbtnFirstSem.setSelected(true);
        } else {
            rbtnSecondSem.setSelected(true);
        }
        txtSubjectName.setText(subject.getSubject_name());
        txtSubjectCode.setText(subject.getSubject_code());
        spnLectureHours.getValueFactory().setValue(subject.getNo_lecture_hours());
        spnTutorialHours.getValueFactory().setValue(subject.getNo_tutorial_hours());
        spnLabHours.getValueFactory().setValue(subject.getNo_lab_hours());
        spnEvaHours.getValueFactory().setValue(subject.getNo_evaluvation_hours());        
    }

    @FXML
    private void clearBtnPressed(ActionEvent event) {
        SubjectTable.getSelectionModel().clearSelection();
        comboOfferedYear.setValue(null);
        rbtnFirstSem.setSelected(false);
        rbtnSecondSem.setSelected(false);
        txtSubjectName.setText("");
        txtSubjectCode.setText("");
        spinnerValue();
    }

    @FXML
    private void addBtnPressed(ActionEvent event) throws IOException {
        // when user clicked Add Lecturer button
        this.managesubjectpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("AddSubject.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Subject - Resouce Management Application");
        stage.setMaximized(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    // Delete subject
    String name;
    @FXML
    private void deleteBtnPressed(ActionEvent event) {
        Subject subject = SubjectTable.getSelectionModel().getSelectedItem();
        name = subject.getSubject_name();
        
        if(SubjectTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("Delete Subject Error");
            alert.setHeaderText("Subject selection from the table is required!");
            alert.setContentText("Please select the subject, then try again!");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) 
                {
                    System.out.println("Pressed OK.");
                    clearBtnPressed(event);
                try {
                    LoadSubjectData();
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
                alert.setTitle("Delete Subject");
                alert.setHeaderText("Confirmation To Delete Subject");
                alert.setContentText("Do you wnat to delete "+name+ " Subject Reocord ? Ok to continue...");
                alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        String query = "DELETE FROM subject WHERE id = ?";
                        pst = connection.prepareStatement(query);
                        pst.setInt(1, subject.getId());
                        name = subject.getSubject_name();
                        int connectStatus = pst.executeUpdate();
                        pst.close();


                        if(connectStatus == 1) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            // Get the Stage.
                            Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert1.setTitle("Delete Subject");
                            alert1.setHeaderText("Successfully Deleted");
                            alert1.setContentText(name + " successfully deleted from system");
                            alert1.showAndWait().ifPresent(rs1 -> {
                            if (rs1 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed(event);
                                try {
                                    LoadSubjectData();
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
                            alert2.setTitle("Delete Lecturer Error");
                            alert2.setHeaderText("Unsuccessful Deletion");
                            alert2.setContentText(name + " can not delete from system");
                            alert2.showAndWait().ifPresent(rs2 -> {
                            if (rs2 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed(event);
                                try {
                                    LoadSubjectData();
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
    
    // Update 
    String sem;
    @FXML
    private void updateBtnPressed(ActionEvent event) {
        Subject subject = SubjectTable.getSelectionModel().getSelectedItem();
        if(SubjectTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Resource Management Application");
            alert.setHeaderText("Update Subject Error");
            alert.setContentText("Please, Select Subject From The Table");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        } else {
            if(Validation() == true) {
                System.out.println("Validation Error");
            } else {
                errLbl.setText("");
                try {
                    
                    String offered_year = comboOfferedYear.getValue().toString();
                    if(rbtnFirstSem.isSelected()) {
                        sem = "1";
                    } else if(rbtnSecondSem.isSelected()){
                        sem = "2";
                    } else {
                        System.out.println("Please Check Save Button -> Radio Button ");
                    }
                    String subject_name = txtSubjectName.getText();
                    String subject_code = txtSubjectCode.getText();
                    int lecture_hours = (int) spnLectureHours.getValue();
                    int tutorial_hours = (int) spnTutorialHours.getValue();
                    int lab_hours = (int) spnLabHours.getValue();
                    int eva_hours = (int) spnEvaHours.getValue();
                    
                    String query = "UPDATE subject SET id=?, offered_year=?, offered_semester=?, subject_name=?, subject_code=?, no_lecture_hours=?, no_tutorial_hours=?, no_lab_hours=?, no_evaluvation_hours=? WHERE id='"+subject.getId()+"'";
                    pst = connection.prepareStatement(query);
                    
                    pst.setInt(1, subject.getId());
                    pst.setString(2, offered_year);
                    pst.setString(3, sem);
                    pst.setString(4, subject_name);
                    pst.setString(5, subject_code);
                    pst.setInt(6, lecture_hours);
                    pst.setInt(7, tutorial_hours);
                    pst.setInt(8, lab_hours);
                    pst.setInt(9, eva_hours);
                    
                    int result = pst.executeUpdate();
                    
                    pst.close();
                    
                    if(result == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Subject Succesfull");
                        alert.setContentText("Successfully update "+txtSubjectName.getText()+ "'s Data");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alert.showAndWait();
                        clearBtnPressed(event);
                        LoadSubjectData();
                        
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Subject Error");
                        alert.setContentText("Can not update "+txtSubjectName.getText()+ "'s Data at this moment, please try again later.");
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
