package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by shailendra.singh on 9/1/16.
 */
public class CustomBarChartController implements Initializable {

    @FXML private HBox graphHbox;

    private double MIN_VAL=10,MAX_VAL=30;

    // used to resize the bar(line) width, same as in case of real barchart
    private double perBarGap = 0.0;

    private LineChart<String,Number> lineChart;
    // added to customize the no of points on the x axis
    private int noOfReadings = 5;

    public void initialize(URL location, ResourceBundle resources) {

        drawLineGraph(noOfReadings);
        configChart();

        // demo points
        addNewReading(10,1);
        addNewReading(31,2);
        addNewReading(8,5);

        // demo horizontal line
        addHorizonalLine(10);

        //demo vertical line
        addVerticalLine("T3");

    }

    private void configChart(){

        // set the Hbox and chart layout
        graphHbox.setPrefSize(800,600);
        graphHbox.getChildren().add(lineChart);
        graphHbox.setHgrow(lineChart, Priority.ALWAYS);
        lineChart.setMaxWidth(graphHbox.getPrefWidth());
        lineChart.setLegendVisible(false);

        // calc the perBarGap value
        perBarGap = lineChart.getMaxWidth() / noOfReadings;

        /**
         * @workaround: hide the extra start and end
         * labels on the x axis,
         * for @t0 and @tn
         */
        CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
        xAxis.setStartMargin(xAxis.getStartMargin() - getBarWidth()*2);
        xAxis.setEndMargin(xAxis.getEndMargin() - getBarWidth()*2);

    }

    private void drawLineGraph(int noOfreadings){

        ObservableList<String> list = initXAxis(noOfreadings);
        CategoryAxis xAxis = new CategoryAxis(list);
        /**
         * upperbound: 50
         * lowerbound: 0
         * tickunit/step: 10
         */
        NumberAxis yAxis = new NumberAxis(0,50,10);
        lineChart = new LineChart<String, Number>(xAxis,yAxis);
        lineChart.setCreateSymbols(false);

    }

    /**
     * @workaround: to plot line from Ti,0 -> Ti,reading
     * @param reading
     * @param testNo
     */
    private void addNewReading(double reading, int testNo){
        XYChart.Series series = new XYChart.Series();
        series.getData().addAll(new XYChart.Data("T"+testNo,0),
                new XYChart.Data("T"+testNo,reading));
        lineChart.getData().add(series);
        setBarColor(series.getNode(), reading);
    }


    /**
     * to plot the horizontal line.
     * from t0,point -> tn,point
     * @param point
     */
    private void addHorizonalLine(double point){
        XYChart.Series series = new XYChart.Series();
        series.getData().addAll(new XYChart.Data("t0",point),
                new XYChart.Data("tn",point));
        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-stroke: blue; -fx-stroke-width: 2px;");
    }

    /**
     * to plot the vertical line.
     * from point,0 -> point,yAxis.upperbound
     * @param point
     */
    private void addVerticalLine(String point){
        XYChart.Series series = new XYChart.Series();
        series.getData().addAll(new XYChart.Data(point,0),
                new XYChart.Data(point,50));
        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-stroke: blue; -fx-stroke-width: 2px;");
    }


    private void setGreenBar(Node node, double barWidth){
        node.setStyle("-fx-stroke: greenyellow; -fx-stroke-width: " + barWidth + "px; -fx-stroke-line-cap: butt");
    }

    private void setRedBar(Node node, double barWidth){
        node.setStyle("-fx-stroke: red; -fx-stroke-width: " + barWidth + "px; -fx-stroke-line-cap: butt");
    }

    private void setBarColor(Node node, double reading){
        if(reading <= MAX_VAL && reading>= MIN_VAL)
            setGreenBar(node, getBarWidth());
        else
            setRedBar(node, getBarWidth());
    }

    /**
     * @helper_function: to calc the bar/line width
     * @return ideal bar/line width in px
     */
    private double getBarWidth(){
        if(perBarGap >= 250)
            return 80;
        else if(perBarGap < 250 && perBarGap >= 150)
            return 65;
        else return 25;
    }

    /**
     * adding {t0,T1,T2....,T<noOfreading>,tn}
     * @t0: initial extra point which should be hidden
     * @tn: extra point in the end which should also be hidden
     * @reason: to mark the start and end of the category axis
     * @param noOfReading
     * @return
     */
    private ObservableList<String> initXAxis(int noOfReading){
        ObservableList<String> xAxisItems = FXCollections.observableArrayList();
        xAxisItems.add("t0");
        for(int i=1; i<=noOfReading; i++)
            xAxisItems.add("T"+i);
        xAxisItems.add("tn");
        return xAxisItems;
    }

}
