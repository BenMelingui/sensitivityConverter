package com.example.sensitivityconverter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.InputMethodEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class JavaFX extends Application{
    int count = 0;
    @Override
    public void start(Stage stage){



       GridPane pane = new GridPane();


        pane.setPrefSize(1280,720);
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        for(int i = 0; i<3; i++){
            ColumnConstraints column = new ColumnConstraints(115);
           pane.getColumnConstraints().add(column);


        }

        ComboBox cb =  new ComboBox();
        HashMap<String,Double> hm = Games.getYawsFromFile();;
        cb.setValue(hm.keySet().toArray()[0]);
        for(Object o : hm.keySet()){
            cb.getItems().add(o.toString());
        }
        Double yaw =  Games.getYawsFromFile().get(cb.getValue().toString());



        pane.add(cb,1,4);
        GridPane.setHalignment(cb, HPos.CENTER);

        Label lblDPI = new Label("DPI");
        Label lblSEN = new Label("Sensitivity");
        Label lbl360 = new Label("cm/360°");
        Label lbl360Text =  new Label("");

        ArrayList<Label> lbls = new ArrayList<>();
        lbls.add(lblDPI);
        lbls.add(lblSEN);
        lbls.add(lbl360);
        lbls.add(lbl360Text);

        for(int i = 0; i<lbls.size();i++){
            lbls.get(i).setFont(new Font("Arial",24));
            lbls.get(i).setTextFill(Color.valueOf("#FFFFFF"));
            if(lbls.get(i)!=lbl360Text){

                pane.add(lbls.get(i),i,0);

            }
            else{
                pane.add(lbls.get(i),2,1);

            }
            GridPane.setHalignment(lbls.get(i),HPos.CENTER);
        }



        TextField txtDPI = new TextField();
        TextField txtSEN = new TextField();



        EventHandler handlerCalculate = new EventHandler() {
            @Override

            public void handle(Event event) {
                calculSen(txtDPI,txtSEN,lbl360Text,hm.get(cb.getValue().toString()));
            }
        };
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

                calculSen(txtDPI,txtSEN,lbl360Text,hm.get(t1.toString()));
            }
        });



        txtDPI.addEventHandler(KeyEvent.KEY_RELEASED,handlerCalculate);
        txtSEN.addEventHandler(KeyEvent.KEY_RELEASED,handlerCalculate);

        ArrayList<TextField> textArray = new ArrayList<>();
        textArray.add(txtDPI);
        textArray.add(txtSEN);

        for(int i = 0; i<textArray.size();i++){
            pane.add(textArray.get(i),i,1);
            GridPane.setHalignment(textArray.get(i),HPos.CENTER);
        }



        BackgroundFill bgfill = new BackgroundFill(Color.valueOf("#6E86A4"), new CornerRadii(0),new Insets(0));
        Background bg = new Background(bgfill);
        pane.setBackground(bg);


        Scene scene = new Scene(pane,720,480);

        stage.setTitle("sensitivityConverter");
        stage.setScene(scene);
        stage.show();
    }

    public void calculSen(TextField txtDPI, TextField txtSEN, Label lbl360Text,Double yaw){
        double dpi,sens,cmPer360;
        try {
            if(txtDPI.getText()!=null || !Objects.equals(txtSEN.getText(), "") && txtSEN.getText()!=null || !Objects.equals(txtSEN.getText(), "")){
             dpi = Double.parseDouble(txtDPI.getText());
             System.out.println(txtDPI.getText());
             sens = Double.parseDouble(txtSEN.getText());
             System.out.println(txtSEN.getText());
             cmPer360 = 2.54 * 360 / (yaw * sens) / dpi;

             BigDecimal round = BigDecimal.valueOf(cmPer360);
             round = round.setScale(2, RoundingMode.HALF_UP);

            lbl360Text.setText(String.valueOf(round));}
        }catch(NumberFormatException ex){
            System.out.println("inputs not double");
        }

    }
    public static void main(String [] args){
        Application.launch(args);
    }


}
