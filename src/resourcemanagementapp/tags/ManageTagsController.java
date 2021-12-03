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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import resourcemanagementapp.subjects.ManageSubjectsController;
import resourcemanagementapp.subjects.Subject;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class ManageTagsController implements Initializable {

    @FXML
    private AnchorPane managetagpane;
    @FXML
    private JFXTextField txtTagName;
    @FXML
    private JFXTextField txtTagCode;
    @FXML
    private JFXComboBox comboRelatedTag;
    @FXML
    private TableView<Tag> TagTable;
    @FXML
    private TableColumn<?, ?> ColoumnID;
    @FXML
    private TableColumn<?, ?> ColoumnTagName;
    @FXML
    private TableColumn<?, ?> ColoumnTagCode;
    @FXML
    private TableColumn<?, ?> ColoumnRelatedTag;
    @FXML
    private Label errlbl;

    // DB Connection
    Connection connection = DBConnection.DBConnector();
    ObservableList<Tag> tag_data = FXCollections.observableArrayList();
    FilteredList<Tag> filteredData = new FilteredList<>(tag_data,e->true);
    
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Radio Button
        relatedTagRadioBtn();
        
        // Display and Load Table
        ColoumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColoumnTagName.setCellValueFactory(new PropertyValueFactory<>("tag_name"));
        ColoumnTagCode.setCellValueFactory(new PropertyValueFactory<>("tag_code"));
        ColoumnRelatedTag.setCellValueFactory(new PropertyValueFactory<>("related_tag"));

        // Load Table Method
        loadTagData();
    }
    
    // Related tag combo box
    public void relatedTagRadioBtn() {
        comboRelatedTag.getItems().clear();
        comboRelatedTag.getItems().addAll("Lecture", "Tutorial", "Practical");
    }
    
    // Load Data From The Database
    public void loadTagData() {
        String query = "SELECT * FROM tag";
        try {
            tag_data.clear();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                tag_data.add(new Tag(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4) 
                ));
                TagTable.setItems(tag_data);
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    // Validation
    public boolean Validation() {
        
        if("".equals(txtTagName.getText())) {
            errlbl.setText("* Please, fill the Tag Name");
            return true;
        }
        
        if("".equals(txtTagCode.getText())) {
            errlbl.setText("* Please, fill the Tag Code");
            return true;
        }
        
        if(comboRelatedTag.getValue() == null) {
            errlbl.setText("* Please, choose the Rlated Tag");
            return true;
        }
        
        return false;
    }

    @FXML
    private void backBtnPressed() throws IOException {
        // when user clicked Add Lecturer button
        this.managetagpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/resourcemanagementapp/Dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dashboard - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void clearBtnPressed() {
        TagTable.getSelectionModel().clearSelection();
        txtTagName.setText("");
        txtTagCode.setText("");
        comboRelatedTag.setValue(null);
        errlbl.setText("");
    }

    @FXML
    private void showOnClick() {
        Tag tag = TagTable.getSelectionModel().getSelectedItem();
        txtTagName.setText(tag.getTag_name());
        txtTagCode.setText(tag.getTag_code());
        comboRelatedTag.setValue(tag.getRelated_tag());
    }

    @FXML
    private void addBtnPressed() throws IOException {
        // when user clicked Add Lecturer button
        this.managetagpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("AddTag.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Tag - Resouce Management Application");
        stage.setMaximized(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    // Delete tag
    String name;
    @FXML
    private void deleteBtnPressed() {
        Tag tag = TagTable.getSelectionModel().getSelectedItem();
        name = tag.getTag_name();
        
        if(TagTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Get the Stage.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            // Add a custom icon.
            alert.setTitle("Delete Tag Error");
            alert.setHeaderText("Tag selection from the table is required!");
            alert.setContentText("Please select the Tag, then try again!");
            alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) 
                {
                    System.out.println("Pressed OK.");
                    clearBtnPressed();
                    loadTagData();
                }
            });
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                // Get the Stage.
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                // Add a custom icon.
                alert.setTitle("Delete Tag");
                alert.setHeaderText("Confirmation To Delete Tag");
                alert.setContentText("Do you wnat to delete "+name+ " Tag Reocord ? Ok to continue...");
                alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        String query = "DELETE FROM tag WHERE id = ?";
                        pst = connection.prepareStatement(query);
                        pst.setInt(1, tag.getId());
                        name = tag.getTag_name();
                        int connectStatus = pst.executeUpdate();
                        pst.close();


                        if(connectStatus == 1) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            // Get the Stage.
                            Stage stage1 = (Stage) alert1.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert1.setTitle("Delete Tag");
                            alert1.setHeaderText("Successfully Deleted");
                            alert1.setContentText(name + " successfully deleted from system");
                            alert1.showAndWait().ifPresent(rs1 -> {
                            if (rs1 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed();
                                    loadTagData();
                                }
                            });
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            // Get the Stage.
                            Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                            // Add a custom icon.
                            alert2.setTitle("Delete Tag Error");
                            alert2.setHeaderText("Unsuccessful Deletion");
                            alert2.setContentText(name + " can not delete from system");
                            alert2.showAndWait().ifPresent(rs2 -> {
                            if (rs2 == ButtonType.OK) 
                                {
                                    System.out.println("Pressed OK.");
                                    clearBtnPressed();
                                    loadTagData();
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
    private void updateBtnPressed() {
        Tag tag = TagTable.getSelectionModel().getSelectedItem();
        if(TagTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Resource Management Application");
            alert.setHeaderText("Update Tag Error");
            alert.setContentText("Please, Select Tag From The Table");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        } else {
            if(Validation() == true) {
                System.out.println("Validation Error");
            } else {
                errlbl.setText("");
                try {
                    
                    String tagName = txtTagName.getText();
                    String tagCode = txtTagCode.getText();
                    String relatedTag = comboRelatedTag.getValue().toString();
                    
                    String query = "UPDATE tag SET id=?, tag_name=?, tag_code=?, related_tag=? WHERE id='"+tag.getId()+"'";
                    pst = connection.prepareStatement(query);
                    
                    pst.setInt(1, tag.getId());
                    pst.setString(2, tagName);
                    pst.setString(3, tagCode);
                    pst.setString(4, relatedTag);
                    
                    
                    int result = pst.executeUpdate();
                    
                    pst.close();
                    
                    if(result == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Tag Succesfull");
                        alert.setContentText("Successfully update "+txtTagName.getText()+ "'s Data");
                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alert.showAndWait();
                        clearBtnPressed();
                        loadTagData();
                        
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Resource Management Application");
                        alert.setHeaderText("Update Tag Error");
                        alert.setContentText("Can not update "+txtTagName.getText()+ "'s Data at this moment, please try again later.");
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
