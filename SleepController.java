/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private TextField date;
    @FXML
    private TextField hours;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblHours;
    /**
     * Initializes the controller class.
     */
    
    
    public void Generate(ActionEvent event) throws IOException {
        Day[] week = new Day[7];
        String day = date.getText();
        int hoursSlept = Integer.parseInt(hours.getText());
        XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
        
    }
    
    public class Day{
        public Day(String date, int hours){
            String currentDate = date;
            int sleepTime = hours;
        }
}
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}


