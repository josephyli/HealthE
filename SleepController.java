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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private Button addDate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField hours;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblHours;
    @FXML
    private Label status;

    private User user;
    private final SleepTimeBean sleepBean;
    private XYChart.Series series;
    private boolean firstLaunch;
    public SleepController() {
        sleepBean = new SleepTimeBean();
        datePicker = new DatePicker();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        sleepChart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Date");
        yAxis.setLabel("Hours slept");
        series = new XYChart.Series<>();

    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    void initData(User userx) {
        this.user = userx;
        status.setText("Welcome, " + user.getName() + "!");
        firstLaunch = true;
        makeChart();
        firstLaunch = false;
    }

    public void generate(ActionEvent event) throws IOException, SQLException, NamingException {
        if (event != null) {
            makeChart();
        }
    }

    public void addDate() {
        if (hours.getText().isEmpty() || datePicker.getValue() == null) {
            showError("Do not leave any blanks. Please fill out the date and number of hours slept");
        } else {

            Double hoursSlept = Double.parseDouble(hours.getText());

            if (hoursSlept >= 0 && hoursSlept <= 24) {

                if (datePicker.getValue() != null) {
                    // we only need 1 series of data
                    LocalDate aDate = datePicker.getValue();

                    try {
                        sleepBean.addSleepTime(user.getName(), aDate, hoursSlept);
                    } catch (SQLException | NamingException ex) {
                        Logger.getLogger(SleepController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    makeChart();
                } else {
                    showError("Date cannot be blank");
                }
            } else {
                showError("Invalid hours slept");
            }
        }
    }

        @Override
        public void initialize
        (URL url, ResourceBundle rb
        
            ) {

        Platform.runLater(new Runnable() {
                @Override

                public void run() {
                    datePicker.requestFocus();
                }
            });
        }

    

    public void makeChart() {
        List<SleepTime> list = null;
        try {
            series.getData().removeAll();
//            series.getData().clear();
        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            list = sleepBean.getSleepTimeList(user.getName());
        } catch (SQLException | NamingException SQLException) {
            System.err.println("Error getting recent sleep times. Sorry.");
        }

        while (!list.isEmpty() && list != null) {
            final SleepTime s = list.get(0);
            list.remove(0);
            series.getData().add(new XYChart.Data(s.getDate().toString(), s.getHours()));
        }
        try {
            if (!series.getData().isEmpty()) {
                if (firstLaunch){
                    sleepChart.getData().add(series);
                    series.setName(user.getName());
                }
                
                if (stage != null) {
                    stage.show();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

}
