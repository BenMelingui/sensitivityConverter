package com.example.sensitivityconverter;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class JavaFX extends Application {
    int count = 0;

    @Override
    public void start(Stage stage) {
        new SensitivityFile(); // Ensure the file is created and populated

        GridPane pane = new GridPane();

        pane.setPrefSize(1280, 720);
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints(150);
            pane.getColumnConstraints().add(column);
        }

        ComboBox<String> cb = new ComboBox<>();
        HashMap<String, Double> hm = SensitivityFile.getYawsFromFile();

        if (!hm.isEmpty()) {
            cb.setValue(hm.keySet().iterator().next());
            for (String key : hm.keySet()) {
                cb.getItems().add(key);
            }
        }

        ComboBox<String> cb1 = new ComboBox<>();
        if (!hm.isEmpty()) {
            cb1.setValue(hm.keySet().iterator().next());
            for (String key : hm.keySet()) {
                cb1.getItems().add(key);
            }
        }

        TextField txtDPI = new TextField();
        TextField txtSEN = new TextField();

        pane.add(cb, 0, 1);
        GridPane.setHalignment(cb, HPos.CENTER);

        pane.add(txtSEN, 1, 1);
        pane.add(txtDPI, 2, 1);

        pane.add(cb1, 0, 3);
        GridPane.setHalignment(cb1, HPos.CENTER);

        Label lblDPI = new Label("DPI");
        Label lblSEN = new Label("sensitivity");
        Label lblSEN1 = new Label("sensitivity");
        Label lbl360 = new Label("cm/360°");
        Label lbl360Text = new Label("٩(＾◡＾)۶");
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

        pane.add(from, 0, 0);
        pane.add(lblDPI, 2, 0);
        pane.add(to, 0, 2);
        pane.add(lblSEN, 1, 0);
        pane.add(lblSEN1, 1, 2);
        pane.add(lblNewCalSen, 1, 3);
        pane.add(lbl360, 2, 2);
        pane.add(lbl360Text, 2, 3);

        for (Label lbl : lbls) {
            lbl.setFont(new Font("Arial", 24));
            lbl.setTextFill(Color.valueOf("#FFFFFF"));
            GridPane.setHalignment(lbl, HPos.CENTER);
        }

        ArrayList<TextField> textArray = new ArrayList<>();
        textArray.add(txtDPI);
        textArray.add(txtSEN);

        EventHandler<KeyEvent> handlerCalculate = event -> {
            if (txtDPI.getText() != null && !Objects.equals(txtDPI.getText(), "") && txtSEN.getText() != null && !Objects.equals(txtSEN.getText(), "")) {
                calculSen(txtDPI, txtSEN, lbl360Text, hm.get(cb.getValue().toString()));
                convertSen(cb, cb1, txtSEN, lblNewCalSen, hm);
            } else {
                if (count % 2 == 0) {
                    lbl360Text.setText("(づ ◕‿◕ )づ");
                    lblNewCalSen.setText("٩(＾◡＾)۶");
                } else {
                    lbl360Text.setText("٩(＾◡＾)۶");
                    lblNewCalSen.setText("(づ ◕‿◕ )づ");
                }
                count++;
            }
        };

        cb.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (txtDPI.getText() != null && !Objects.equals(txtDPI.getText(), "") && txtSEN.getText() != null && !Objects.equals(txtSEN.getText(), "")) {
                calculSen(txtDPI, txtSEN, lbl360Text, hm.get(newValue.toString()));
                convertSen(cb, cb1, txtSEN, lblNewCalSen, hm);
            } else {
                if (count % 2 == 0) {
                    lbl360Text.setText("(づ ◕‿◕ )づ");
                    lblNewCalSen.setText("٩(＾◡＾)۶");
                } else {
                    lbl360Text.setText("٩(＾◡＾)۶");
                    lblNewCalSen.setText("(づ ◕‿◕ )づ");
                }
                count++;
            }
        });

        cb1.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (txtDPI.getText() != null && !Objects.equals(txtDPI.getText(), "") && txtSEN.getText() != null && !Objects.equals(txtSEN.getText(), "")) {
                convertSen(cb, cb1, txtSEN, lblNewCalSen, hm);
            } else {
                if (count % 2 == 0) {
                    lbl360Text.setText("(づ ◕‿◕ )づ");
                    lblNewCalSen.setText("٩(＾◡＾)۶");
                } else {
                    lbl360Text.setText("٩(＾◡＾)۶");
                    lblNewCalSen.setText("(づ ◕‿◕ )づ");
                }
                count++;
            }
        });

        for (TextField textField : textArray) {
            textField.addEventHandler(KeyEvent.KEY_RELEASED, handlerCalculate);
        }

        BackgroundFill bgfill = new BackgroundFill(Color.valueOf("#6E86A4"), new CornerRadii(0), new Insets(0));
        Background bg = new Background(bgfill);
        pane.setBackground(bg);

        Scene scene = new Scene(pane, 720, 480);

        URL url = JavaFX.class.getResource("/com/example/sensitivityconverter/images/icon.png");
        if (url != null) {
            Image image = new Image(url.toExternalForm());
            stage.getIcons().add(image);
        } else {
            System.out.println("Icon image not found");
        }

        stage.setTitle("sensitivityConverter");
        stage.setScene(scene);
        stage.show();
    }

    public void calculSen(TextField txtDPI, TextField txtSEN, Label lbl360Text, Double yaw) {
        double dpi, sens, cmPer360;
        try {
            if (txtDPI.getText() != null && !Objects.equals(txtDPI.getText(), "") && txtSEN.getText() != null && !Objects.equals(txtSEN.getText(), "")) {
                dpi = Double.parseDouble(txtDPI.getText());
                sens = Double.parseDouble(txtSEN.getText());
                cmPer360 = 2.54 * 360 / (yaw * sens) / dpi;

                BigDecimal round = BigDecimal.valueOf(cmPer360);
                round = round.setScale(2, RoundingMode.HALF_UP);
                lbl360Text.setText(round.toString());
            }
        } catch (NumberFormatException e) {
            if (count % 2 == 0) {
                lbl360Text.setText("(づ ◕‿◕ )づ");
            } else {
                lbl360Text.setText("٩(＾◡＾)۶");
            }
            count++;
        }
    }

    public void convertSen(ComboBox<String> cb, ComboBox<String> cb1, TextField txtSEN, Label lblNewCalSen, HashMap<String, Double> hm) {
        try {
            double originalYaw = hm.get(cb.getValue());
            double targetYaw = hm.get(cb1.getValue());
            double originalSens = Double.parseDouble(txtSEN.getText());

            double newSens = originalSens * (originalYaw / targetYaw);
            BigDecimal round = BigDecimal.valueOf(newSens);
            round = round.setScale(2, RoundingMode.HALF_UP);
            lblNewCalSen.setText(round.toString());
        } catch (NumberFormatException | NullPointerException e) {
            if (count % 2 == 0) {
                lblNewCalSen.setText("٩(＾◡＾)۶");
            } else {
                lblNewCalSen.setText("(づ ◕‿◕ )づ");
            }
            count++;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
