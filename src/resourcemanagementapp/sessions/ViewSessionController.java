/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.sessions;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import resourcemanagementapp.database.DBConnection;

/**
 * FXML Controller class
 *
 * @author thilr_88qp6ap
 */
public class ViewSessionController implements Initializable {

    @FXML
    private AnchorPane paneviewsession;
    @FXML
    private Label lblLecturer1;
    @FXML
    private Label lblLecturer2;
    @FXML
    private Label lblTag;
    @FXML
    private Label lblGroup;
    @FXML
    private Label lblSubject;
    @FXML
    private Label lblDuration;
    @FXML
    private Label lblNoOfStudents;
    @FXML
    private Label lblMainSession;
    @FXML
    private Label lblSujectCode;
    @FXML
    private Label lblSessionID;
    
    Connection connection = DBConnection.DBConnector();
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set label empty
        lblSessionID.setText("");
        lblLecturer1.setText("");
        lblLecturer2.setText("");
        lblTag.setText("");
        lblGroup.setText("");
        lblSubject.setText("");
        lblDuration.setText("");
        lblNoOfStudents.setText("");
        lblSujectCode.setText("");
        lblMainSession.setText("");
        
        
        
        // get Selected session id
        //int sessionID = msc.passSessionID();
        //System.out.println("Session ID "+ sessionID);
    }    

    @FXML
    private void okBtnPressed(ActionEvent event) {
        this.paneviewsession.getScene().getWindow().hide();
    }
    
    public void setSessionID(int ID) throws SQLException {
        this.lblSessionID.setText(String.valueOf(ID));
        loadSessionLabels();
        mainSession();
    }
    
    // Preparing session
    String mainSessionText;
    public void mainSession() {
        String lecturer_1 = lblLecturer1.getText();
        String lecturer_2 = lblLecturer2.getText();
        String sCode = lblSujectCode.getText();
        String sName = lblSubject.getText();
        String tag = lblTag.getText();
        String group = lblGroup.getText();
        String noOfStudents = lblNoOfStudents.getText();
        String duration = lblDuration.getText();
        
        if(lecturer_2 == null) {
            mainSessionText = lecturer_1 + " - " + sCode + " - " + sName + " - " + tag
                + " - " + group + " - " + noOfStudents + " - " + duration;
        } else {
            mainSessionText = lecturer_1 + " - " + lecturer_2 + " - " + sCode + " - " + sName + " - " + tag
                + " - " + group + " - " + noOfStudents + " - " + duration;
        }
        lblMainSession.setText(mainSessionText);
    }
    
    // load view session labels
    public void loadSessionLabels() throws SQLException {
        int id = Integer.parseInt(lblSessionID.getText());
        String query = "SELECT * FROM session WHERE id="+id;
        try {
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            if(rs.next()) {
              lblLecturer1.setText(rs.getString("lecturer1"));
              lblLecturer2.setText(rs.getString("lecturer2"));
              lblTag.setText(rs.getString("tag"));
              lblGroup.setText(rs.getString("group_id"));
              lblSubject.setText(rs.getString("subject_name"));
              lblSujectCode.setText(rs.getString("subject_code"));
              lblNoOfStudents.setText(rs.getString("no_of_students"));
              lblDuration.setText(rs.getString("duration"));
            }
        } catch (SQLException e) {
        } finally {
            pst.close();
            rs.close();
        }
    }
}
