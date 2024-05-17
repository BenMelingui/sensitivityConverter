package com.example.sensitivityconverter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
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
            ColumnConstraints column = new ColumnConstraints(150);
           pane.getColumnConstraints().add(column);
        }

        ComboBox cb =  new ComboBox();
        HashMap<String,Double> hm = SensitivityFile.getYawsFromFile();;
        cb.setValue(hm.keySet().toArray()[0]);
        for(Object o : hm.keySet()){
            cb.getItems().add(o.toString());
        }
        Double yaw =  SensitivityFile.getYawsFromFile().get(cb.getValue().toString());

        ComboBox cb1 =  new ComboBox();
        cb1.setValue(hm.keySet().toArray()[0]);
        for(Object o : hm.keySet()){
            cb1.getItems().add(o.toString());
        }
        Double yaw1 =  SensitivityFile.getYawsFromFile().get(cb1.getValue().toString());


        TextField txtDPI = new TextField();
        TextField txtSEN = new TextField();

        pane.add(cb,0,1);
        GridPane.setHalignment(cb, HPos.CENTER);

        pane.add(txtSEN,1,1);
        pane.add(txtDPI,2,1);


        pane.add(cb1,0,3);
        GridPane.setHalignment(cb1,HPos.CENTER);

        Label lblDPI = new Label("DPI");
        Label lblSEN = new Label("sensitivity");
        Label lblSEN1 = new Label("sensitivity");
        Label lbl360 = new Label("cm/360°");
        Label lbl360Text =  new Label("٩(＾◡＾)۶");
        Label lblNewCalSen = new Label("(づ ◕‿◕ )づ");
        Label from = new Label("from");
        Label to = new Label("to");



        ArrayList<Label> lbls = new ArrayList<>();
        lbls.add(lblDPI);
        lbls.add(lblSEN);
        lbls.add(lblSEN1);
        lbls.add(lbl360);
        lbls.add(lbl360Text);
        lbls.add(lblNewCalSen);
        lbls.add(from);
        lbls.add(to);

        pane.add(from,0,0);
        pane.add(lblDPI,2,0);
        pane.add(to,0,2);
        pane.add(lblSEN,1,0);
        pane.add(lblSEN1,1,2);
        pane.add(lblNewCalSen,1,3);
        pane.add(lbl360,2,2);
        pane.add(lbl360Text,2,3);



        for(int i = 0; i<lbls.size();i++){
            lbls.get(i).setFont(new Font("Arial",24));
            lbls.get(i).setTextFill(Color.valueOf("#FFFFFF"));
            GridPane.setHalignment(lbls.get(i),HPos.CENTER);
        }



        ArrayList<TextField> textArray = new ArrayList<>();
        textArray.add(txtDPI);
        textArray.add(txtSEN);



        EventHandler handlerCalculate = new EventHandler() {
            @Override

            public void handle(Event event) {
                calculSen(txtDPI,txtSEN,lbl360Text,hm.get(cb.getValue().toString()));
                convertSen(cb,cb1,txtSEN,lblNewCalSen,hm);
            }
        };
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

                calculSen(txtDPI,txtSEN,lbl360Text,hm.get(t1.toString()));
                convertSen(cb,cb1,txtSEN,lblNewCalSen,hm);
            }
        });

        cb1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                convertSen(cb,cb1,txtSEN,lblNewCalSen,hm);
            }
        });



        for(int i = 0; i<textArray.size();i++){
            textArray.get(i).addEventHandler(KeyEvent.KEY_RELEASED,handlerCalculate);
        }




        BackgroundFill bgfill = new BackgroundFill(Color.valueOf("#6E86A4"), new CornerRadii(0),new Insets(0));
        Background bg = new Background(bgfill);
        pane.setBackground(bg);


        Scene scene = new Scene(pane,720,480);

        URL url = JavaFX.class.getResource("images/icon.png");
        Image image = new Image(url.toExternalForm());


        stage.getIcons().add(image);
        stage.setTitle("sensitivityConverter");
        stage.setScene(scene);
        stage.show();
    }

    public void calculSen(TextField txtDPI, TextField txtSEN, Label lbl360Text,Double yaw){
        double dpi,sens,cmPer360;
        try {
            if(txtDPI.getText()!=null || !Objects.equals(txtSEN.getText(), "") && txtSEN.getText()!=null || !Objects.equals(txtSEN.getText(), "")){
             dpi = Double.parseDouble(txtDPI.getText());
             //System.out.println(txtDPI.getText());
             sens = Double.parseDouble(txtSEN.getText());
             //System.out.println(txtSEN.getText());
             cmPer360 = 2.54 * 360 / (yaw * sens) / dpi;

             BigDecimal round = BigDecimal.valueOf(cmPer360);
             round = round.setScale(2, RoundingMode.HALF_UP);

            lbl360Text.setText(String.valueOf(round));}
        }catch(NumberFormatException ex){
            //System.out.println("inputs not double");
        }

    }

    public void convertSen(ComboBox cb, ComboBox cb1, TextField sensi, Label lblNewCalSen,HashMap<String,Double> hm ){
        hm=SensitivityFile.getYawsFromFile();
        try{
            //System.out.println("yaw : " +hm.get(cb.getValue().toString()) + " | sens : " + Double.parseDouble(sensi.getText()) + " | yaw1 : " + hm.get(cb1.getValue().toString()));

            double newCalSen = (hm.get(cb.getValue().toString())*Double.parseDouble(sensi.getText()))/hm.get(cb1.getValue().toString());
            BigDecimal round = BigDecimal.valueOf(newCalSen);
            round = round.setScale(2, RoundingMode.HALF_UP);
            lblNewCalSen.setText(String.valueOf(round));
        }catch(NumberFormatException e){
            // System.out.println("cm/360 vide");
        }

    }

    public static void main(String [] args){
        Application.launch(args);
    }


}
