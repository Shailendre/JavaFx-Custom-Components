package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by shailendra.singh on 8/27/16.
 */
public class VerticalProgressBarController implements Initializable {

    @FXML
    VBox progress_bar;
    @FXML
    VBox bar;


    private double netvolume = 0.0;
    private double volumepercentage = 0.0;
    private double containerCapacity;
    private double progressBarHeight;


    public void initialize(URL location, ResourceBundle resources) {

        progressBarHeight = progress_bar.getPrefHeight();

        // set bar's max height to the progress bar's height
        bar.setMaxHeight(progressBarHeight);

        // default bar color set to green
        setGreenBar();

        // container capacity represents the total height to be considered for vertical progress bar
        containerCapacity = 100;

        // update volume: pass a value to be shown relative to the container capacity
        updateVolume(20);
    }

    public void setContainerCapacity(double capacity){
        this.containerCapacity = capacity;
    }

    public void setGreenBar(){
        bar.setStyle("-fx-background-color: green");
    }

    public void setYellowBar(){
        bar.setStyle("-fx-background-color: yellow");
    }

    public void setRedBar(){
        bar.setStyle("-fx-background-color: red");
    }

    public VBox getProgress_bar() {
        return progress_bar;
    }

    public void setProgress_bar(VBox progress_bar) {
        this.progress_bar = progress_bar;
    }

    public VBox getBar() {
        return bar;
    }

    public void setBar(VBox bar) {
        this.bar = bar;
    }

    public double getNetvolume() {
        return netvolume;
    }

    public void setNetvolume(double netvolume) {
        this.netvolume = netvolume;
    }

    public double getVolumepercentage() {
        return volumepercentage;
    }

    public void setVolumepercentage(double volumepercentage) {
        this.volumepercentage = volumepercentage;
    }

    public double getContainerCapacity() {
        return containerCapacity;
    }

    /**
     * calculates the percent; how much is
     * @volume of
     * @netVolume ?
     * @param volume
     */
    public void updateVolume(double volume){
        netvolume += volume;
        volumepercentage = netvolume / containerCapacity;
        updateVolumeIndicator(volumepercentage);
    }

    public void resetProgressBar(){
        netvolume = 0;
        volumepercentage = 0.0;
        updateVolume(0.0);
    }

    /**
     * changes the inner bar height dynamically.
     * progress param is percent, as calculated in,
     * @funct  updateVolume
     * @param progress
     */
    public void updateVolumeIndicator(double progress){

        bar.setPrefHeight(progressBarHeight * progress);
        /**
         * added to set different color labels,
         * or warnings
         */
        if(progress <= .60){
            setGreenBar();
        } else if(progress > .60 &&
                progress <= .75){
            setYellowBar();
        }else {
            setRedBar();
        }
    }
}
