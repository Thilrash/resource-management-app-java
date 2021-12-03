/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resourcemanagementapp.database.DBConnection;

/**
 *
 * @author thilr_88qp6ap
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane dashboradpane;
    @FXML
    private Label lblRegisteredLecture;
    @FXML
    private Label lblRegisteredStudents;
    @FXML
    private Label lblRegisteredSubjects;
    @FXML
    private Label lblRegisteredRooms;
    @FXML
    private BarChart<Integer, String> barChartLocation;
    @FXML
    private Label lblLatestLecturer;
    @FXML
    private Label lblLatestGroup;
    @FXML
    private Label lblLatestSubject;
    
    // Static database connection
    Connection con = DBConnection.DBConnector();
    Statement stmt = null;
    ResultSet rs = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set label as empty initially
        lblRegisteredLecture.setText("");
        lblRegisteredStudents.setText("");
        lblRegisteredSubjects.setText("");
        lblRegisteredRooms.setText("");
        lblLatestLecturer.setText("");
        lblLatestGroup.setText("");
        lblLatestSubject.setText("");
        
        try {
            // Display the counts
            calculateLecturersCount();
            calculateStudentsCount();
            calculateSubjectsCount();
            calculateRoomsCount();
            latestLecturer();
            latestGroup();
            latestSubject();
            displayLocationBarChart();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void clickManageLecturers(ActionEvent event) throws IOException {
        
        // when user clicked Manage Lecturers button
        this.dashboradpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("lecturers/ManageLecturers.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Lecturers - Resouce Management Application");
        // stage.setMaximized(true);
        stage.show();
    }

    @FXML
    private void clickManageSubjects(ActionEvent event) throws IOException {
        // when user clicked Manage Lecturers button
        this.dashboradpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("subjects/ManageSubjects.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Subjects - Resouce Management Application");
        // stage.setMaximized(true);
        stage.show();
    }

    @FXML
    private void clickManageStudents(ActionEvent event) throws IOException {
        // when user clicked Manage Lecturers button
        this.dashboradpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("studentgroup/ManageStudentGroups.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Student - Resouce Management Application");
        // stage.setMaximized(true);
        stage.show();
    }

    @FXML
    private void clickManageLocations(ActionEvent event) throws IOException {
        // when user clicked Manage Lecturers button
        this.dashboradpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("locations/ManageLocations.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Locations- Resouce Management Application");
        // stage.setMaximized(true);
        stage.show();
    }

    @FXML
    private void clickManageTags(ActionEvent event) throws IOException {
        // when user clicked Manage Lecturers button
        this.dashboradpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("tags/ManageTags.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Manage Tags - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void ClickAddWorkingDays(ActionEvent event) throws IOException {
        // when user clicked Manage Lecturers button
        this.dashboradpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("workingdays/AddWorkingDays.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("AddWorking Days - Resouce Management Application");
        stage.show();
    }
    
    // Calculate total number of Lecturers
    public void calculateLecturersCount() throws SQLException {
        try {
            String query = "SELECT COUNT(id) AS COUNT_LECTURER FROM lecturer";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int count = rs.getInt("COUNT_LECTURER");
                lblRegisteredLecture.setText(Integer.toString(count));
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            rs.close();
            stmt.close();
        }
    }
    
    // Calculate total number of Students
    public void calculateStudentsCount() throws SQLException {
        try {
            String query = "SELECT COUNT(id) AS COUNT_STUDENTS FROM student_group";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int count = rs.getInt("COUNT_STUDENTS");
                lblRegisteredStudents.setText(Integer.toString(count));
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            rs.close();
            stmt.close();
        }
    }
    
    // Calculate total number of Subjects
    public void calculateSubjectsCount() throws SQLException {
        try {
            String query = "SELECT COUNT(id) AS COUNT_SUBJECT FROM subject";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int count = rs.getInt("COUNT_SUBJECT");
                lblRegisteredSubjects.setText(Integer.toString(count));
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            rs.close();
            stmt.close();
        }
    }
    
    // Calculate total number of Subjects
    public void calculateRoomsCount() throws SQLException {
        try {
            String query = "SELECT COUNT(id) AS COUNT_ROOMS FROM location";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int count = rs.getInt("COUNT_ROOMS");
                lblRegisteredRooms.setText(Integer.toString(count));
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            rs.close();
            stmt.close();
        }
    }
    
    // Get latest lecturer
    public void latestLecturer() throws SQLException {
        try {
            String query = "SELECT lecturer_name FROM lecturer ORDER BY id DESC LIMIT 1";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String result = rs.getString("lecturer_name");
                lblLatestLecturer.setText(result);
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            rs.close();
            stmt.close();
        }
    }
    
    // Get latest lecturer
    public void latestGroup() throws SQLException {
        try {
            String query = "SELECT sub_group_id FROM student_group ORDER BY id DESC LIMIT 1";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String result = rs.getString("sub_group_id");
                lblLatestGroup.setText(result);
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            rs.close();
            stmt.close();
        }
    }
    
    // Get latest lecturer
    public void latestSubject() throws SQLException {
        try {
            String query = "SELECT subject_name FROM subject ORDER BY id DESC LIMIT 1";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String result = rs.getString("subject_name");
                lblLatestSubject.setText(result);
            }
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            rs.close();
            stmt.close();
        }
    }
    
    // Chart
    public void displayLocationBarChart() throws SQLException {
        XYChart.Series<Integer,String> series1 = new XYChart.Series<>();
        XYChart.Series<Integer,String> series2 = new XYChart.Series<>();
        try {
            String query1 = "select count(id) as COUNT_LECTURE, room_type from location where room_type='Lecture Hall'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query1);
            while(rs.next()) {
                series1.getData().add(new XYChart.Data<>(rs.getInt("COUNT_LECTURE"),rs.getString("room_type")));
            }
            String query2 = "select count(id) as COUNT_LAB, room_type from location where room_type='Laboratory'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query2);
            while(rs.next()) {
                series2.getData().add(new XYChart.Data<>(rs.getInt("COUNT_LAB"),rs.getString("room_type")));
            }
            series1.setName("Lecture Hall");
            series2.setName("Laboratory");
            barChartLocation.getData().addAll(series1,series2);
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            rs.close();
            stmt.close();
        }
    }

    @FXML
    private void clickManageSessions(ActionEvent event) throws IOException {
        // when user clicked Manage Lecturers button
        this.dashboradpane.getScene().getWindow().hide();
        
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("sessions/ManageSessions.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Manage Sessions - Resouce Management Application");
        stage.show();
    }

    @FXML
    private void ClickAddSession(ActionEvent event) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("sessions/AddSession.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Session - Resource Management Application");
        stage.setMaximized(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
}
