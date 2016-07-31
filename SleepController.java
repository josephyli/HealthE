/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthe;

import static healthe.Login.stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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

    @FXML
    private BarChart<String, Number> sleepChart;
    @FXML
    private Button generate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hours;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblHours;
    @FXML
    private Label userName;

    private User user;
    private SleepTimeBean sleepBean;
    private XYChart.Series series = new XYChart.Series();

    public SleepController() {
        datePicker = new DatePicker();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        sleepChart = new BarChart<String,Number>(xAxis,yAxis);
        xAxis.setLabel("Date");       
        yAxis.setLabel("Number of hours slept");
        series = new XYChart.Series<>();
        series.setName("user's name");
        
//        user.setName("hello");
    }

    void initData(User user) {
        this.user = user;
    }

    public void Generate(ActionEvent event) throws IOException, SQLException, NamingException {
        Day[] week = new Day[7];
        
        // if a value was inputted, add day to database
//        if (datePicker.getValue() != null && !hours.getText().isEmpty()) {
//            // add day if not empty
//            addDate();
//        }
//        try {
//            // testing the user "hello"... not working yet
//            latest = sleepBean.getSleepTimeList("hello");
//        } catch (SQLException | NamingException SQLException) {
//            System.err.println("Error getting recent sleep times. Sorry.");
//        }

//        while (!latest.isEmpty()) {
//            final SleepTime s = latest.get(0);
//            latest.remove(0);
//            series.getData().add(new XYChart.Data(s.getDate(), s.getHours()));
//        }
        makeChart();
    }

    private void addDate() {
        LocalDate aDate = datePicker.getValue();
        System.err.println("Selected date: " + aDate);
        Double hoursSlept = Double.parseDouble(hours.getText());
        
        /// not completed yet
    }
    
    public class Day {

        public Day(String date, int hours) {
            String currentDate = date;
            int sleepTime = hours;
        }
    }

    private void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(new Runnable() {
            @Override

            public void run() {
                datePicker.requestFocus();
            }
        });
        
    }

    public void makeChart() {
//        List<SleepTime> list = null;
//        try {
//            // testing the user "hello"... not working yet
//            list = sleepBean.getSleepTimeList("hello");
//        } catch (SQLException | NamingException SQLException) {
//            System.err.println("Error getting recent sleep times. Sorry.");
//        }
//        XYChart.Series<Date, Double> series = new XYChart.Series<>();
//
//        while (!list.isEmpty()) {
//            final SleepTime s = list.get(0);
//            list.remove(0);
//            series.getData().add(new XYChart.Data(s.getDate(), s.getHours()));
//        }

        // we only need 1 series of data
        Double hoursSlept = Double.parseDouble(hours.getText());
        LocalDate aDate = datePicker.getValue();
        System.out.println("Selected date: " + aDate);
        series.getData().add(new XYChart.Data(aDate.toString(), hoursSlept));
        sleepChart.getData().add(series);
        stage.show();
    }

}
