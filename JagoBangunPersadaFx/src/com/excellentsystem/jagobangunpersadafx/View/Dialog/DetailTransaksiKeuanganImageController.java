/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Main;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailTransaksiKeuanganImageController {

    @FXML
    private HBox hbox;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView imageView;
    @FXML
    private Slider zoomLevel;
    private double zoomFactor;
    private double imageHeight;
    private double imageWidth;
    @FXML
    public Button closeButton;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public List<ImageView> listImage = new ArrayList<>();

    public void initialize() {
        final ContextMenu rm = new ContextMenu();
        MenuItem addImage = new MenuItem("Add Image");
        addImage.setOnAction((ActionEvent event) -> {
            addImage();
        });
        MenuItem delete = new MenuItem("Delete Image");
        delete.setOnAction((ActionEvent event) -> {
            deleteImage();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            refresh();
        });
        rm.getItems().addAll(addImage);
        if (selectedImage != null) {
            rm.getItems().addAll(delete);
        }
        rm.getItems().addAll(refresh);
        stackPane.setOnContextMenuRequested((e) -> {
            rm.show(stackPane, e.getScreenX(), e.getScreenY());
        });
        stackPane.setOnMouseClicked((event) -> {
            rm.hide();
        });
        zoomFactor = 1d;
        zoomLevel.setValue(100d);
        imageView.setX(0);
        imageView.setY(0);
        zoomLevel.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            zoomFactor = newValue.doubleValue() / 100 * 1;
            imageView.setFitHeight(imageHeight * zoomFactor);
            imageView.setFitWidth(imageWidth * zoomFactor);
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try {
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setHeight((mainApp.screenSize.getHeight() - 80));
            stage.setWidth((mainApp.screenSize.getWidth() - 80));
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
            imageHeight = (mainApp.screenSize.getHeight() - 400);
            imageWidth = (mainApp.screenSize.getWidth() - 200);
            imageView.setFitHeight(imageHeight);
            imageView.setFitWidth(imageWidth);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }

    }

    public void setImage(List<ImageView> i) {
        listImage = i;
        refresh();
    }

    public void refresh() {
        selectedImage = null;
        imageView.setImage(new Image(Main.class.getResourceAsStream("Resource/noImage.jpg")));
        getListImage();
    }

    private void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image files", "*.jpg"), new FileChooser.ExtensionFilter("image files", "*.png"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                selectedImage = new ImageView(new Image("file:" + file.getPath()));
                imageView.setImage(selectedImage.getImage());

                listImage.add(selectedImage);
                getListImage();
            } catch (Exception e) {
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }

    private void getListImage() {
        hbox.getChildren().clear();
        for (ImageView i : listImage) {
            StackPane pane = new StackPane();
            pane.setPrefSize(120, 120);

            i.setEffect(new ColorAdjust(0, 0, -0.25, 0));
            i.setFitWidth(120);
            i.setFitHeight(120);
            pane.getChildren().add(i);

            pane.setOnMouseEntered((MouseEvent event) -> {
                for (Node n : pane.getChildren()) {
                    if (n instanceof ImageView) {
                        n.setEffect(new ColorAdjust(0, 0, 0, 0));
                    }
                }
            });
            pane.setOnMouseExited((MouseEvent event) -> {
                for (Node n : pane.getChildren()) {
                    if (n instanceof ImageView) {
                        n.setEffect(new ColorAdjust(0, 0, -0.25, 0));
                    }
                }
            });
            pane.setOnMousePressed((MouseEvent event) -> {
                for (Node n : pane.getChildren()) {
                    if (n instanceof ImageView) {
                        n.setEffect(new ColorAdjust(0, 0, 0, 0));
                    }
                }
            });
            pane.setOnMouseReleased((MouseEvent event) -> {
                for (Node n : pane.getChildren()) {
                    if (n instanceof ImageView) {
                        n.setEffect(new ColorAdjust(0, 0, -0.25, 0));
                    }
                }
            });
            pane.setOnMouseClicked((MouseEvent event) -> {
                selectedImage = i;
                imageView.setImage(selectedImage.getImage());
            });

            hbox.getChildren().add(pane);
        }
    }
    private ImageView selectedImage;

    private void deleteImage() {
        listImage.remove(selectedImage);
        refresh();
    }

}
