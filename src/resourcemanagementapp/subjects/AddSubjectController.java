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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resourcemanagementapp.database.DBConnection;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class AddSubjectController implements Initializable {

    @FXML
    private AnchorPane addsubjectpane;
    @FXML
    private JFXComboBox comboOfferedYear;
    @FXML
    private JFXRadioButton rbtnFirstSem;
    @FXML
    private ToggleGroup semesterGroup;
    @FXML
    private JFXRadioButton rbtnSecondSem;
    @FXML
    private JFXTextField txtSubjectName;
    @FXML
    private JFXTextField txtSubjectCode;
    @FXML
    private Spinner spnLectureHours;
    @FXML
    private Spinner spnTutorialHours;
    @FXML
    private Spinner spnLabHours;
    @FXML
    private Spinner spnEvaHours;
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
        // Spinner Value
        spinnerValue();
        
        //Radio Button
        radioButtonGroup();
        
        // Combo box 
        showOfferedYear();
    }
    
    // Offered year combo box
    public void showOfferedYear() {
        comboOfferedYear.getItems().clear();
        comboOfferedYear.getItems().addAll("1","2","3","4");
    }

    // Radio button Group
    public void radioButtonGroup() {
        ToggleGroup semesterGroup = new ToggleGroup();
        rbtnFirstSem.setToggleGroup(semesterGroup);
        rbtnSecondSem.setToggleGroup(semesterGroup);
       
    }
    
    // Spinner Value factory
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
    
    // Load Auto Increment ID
    int AID;
    public int autoIncrementID() throws SQLException {
        String query = "SELECT id FROM subject ORDER BY id DESC LIMIT 1";
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
    private void backBtnPressed() throws IOException {
        // when user clicked Add Lecturer button
        this.addsubjectpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("ManageSubjects.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Subjects - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void clearBtnPressed() {
        errLbl.setText("");
        comboOfferedYear.setValue(null);
        rbtnFirstSem.setSelected(false);
        rbtnSecondSem.setSelected(false);
        txtSubjectName.setText("");
        txtSubjectCode.setText("");
        spinnerValue();
    }

    // add subject
    String sem;
    @FXML
    private void saveBtnPressed(ActionEvent event) throws SQLException {
        autoIncrementID();
        if(Validation() == true) {
            System.out.println("== Statement FALSE ==");
        } else {
            errLbl.setText("");
            // Add data to DB
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
            
            String query = "INSERT INTO subject (id, offered_year, offered_semester, subject_name, subject_code, no_lecture_hours, no_tutorial_hours, no_lab_hours, no_evaluvation_hours) VALUES (?,?,?,?,?,?,?,?,?)";
            
            pst = null;
            
            try {
                pst = connection.prepareStatement(query);
                pst.setInt(1, AID);
                pst.setString(2, offered_year);
                pst.setString(3, sem);
                pst.setString(4, subject_name);
                pst.setString(5, subject_code);
                pst.setInt(6, lecture_hours);
                pst.setInt(7, tutorial_hours);
                pst.setInt(8, lab_hours);
                pst.setInt(9, eva_hours);
                
                int connectStatus = pst.executeUpdate();
                pst.close();
                
                if(connectStatus == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    // Add a custom icon.
                    alert.setTitle("Add Subject");
                    alert.setHeaderText("Successfully Inserted.");
                    alert.setContentText("ubject Add Successfully");
                    alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) 
                        {
                            System.out.println("Pressed OK.");
                            clearBtnPressed();
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
                    alert.setTitle("Add Lecturer Error");
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
  
}
