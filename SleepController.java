/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthe;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.naming.NamingException;

/**
 * FXML Controller class
 *
 * @author watan
 */
public class SleepController implements Initializable {

    @FXML LineChart<String, Number> sleepChart;
    @FXML
    private Button generate;
    @FXML
    private DatePicker date;
    @FXML
    private TextField hours;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblHours;
    @FXML
    private Label userName;
    
    private User user;
    private SleepTimeBean bean;
    /**
     * Initializes the controller class.
     */
    
    void initData(User user) {
        this.user = user;
    }
    
    public void Generate(ActionEvent event) throws IOException, SQLException, NamingException {
        Day[] week = new Day[7];
        List<SleepTime> latest = null;
        date = new DatePicker(LocalDate.now());
        LocalDate aDate = date.getValue();
        System.err.println("Selected date: " + aDate);
        Double hoursSlept = Double.parseDouble(hours.getText());

        try {
            // testing the user "hello"
            latest = bean.getRecentSleepTime("hello");
        } catch (SQLException | NamingException SQLException) {
            System.err.println("Error getting recent sleep times. Sorry.");
        }
        XYChart.Series<Date,Number> series = new XYChart.Series<>();

        while(!latest.isEmpty()) {
            final SleepTime s = latest.get(0);
            latest.remove(0);
            // needs code add to series' data
        }
        
    }
    
//    private void addDate(ObservableList<Data<Date, Double>> data, Date aDate, Double hours) {
//        
//    }
    
    public class Day{
        public Day(String date, int hours){
            String currentDate = date;
            int sleepTime = hours;
        }
    }  
    
    private void setUser(User user) {
        this.user = user;
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}


