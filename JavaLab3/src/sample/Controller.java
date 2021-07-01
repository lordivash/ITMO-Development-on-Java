package sample;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML private Circle redCircle;
    @FXML private Rectangle yellowSquare;
    @FXML private Polygon greenTriangle;

    ParallelTransition pt0 = new ParallelTransition();
    ParallelTransition pt1 = new ParallelTransition();
    ParallelTransition pt2 = new ParallelTransition();

    FadeTransition circleLight = new FadeTransition();
    FadeTransition circleFade = new FadeTransition();

    FadeTransition squareLight = new FadeTransition();
    FadeTransition squareFade = new FadeTransition();

    FadeTransition triangleLight = new FadeTransition();
    FadeTransition triangleFade = new FadeTransition();

    SequentialTransition st = new SequentialTransition();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bindLightTransition(circleLight, redCircle);
        bindLightTransition(squareLight, yellowSquare);
        bindLightTransition(triangleLight, greenTriangle);

        bindFadeTransition(circleFade, redCircle);
        bindFadeTransition(squareFade, yellowSquare);
        bindFadeTransition(triangleFade, greenTriangle);

        pt0.getChildren().addAll(circleLight, squareFade, triangleFade);
        pt1.getChildren().addAll(circleFade, triangleFade);
        pt2.getChildren().addAll(circleFade, squareFade);

        st.getChildren().addAll(pt0, pt1, pt2);
        st.setCycleCount(SequentialTransition.INDEFINITE);
    }

    private void bindFadeTransition(FadeTransition ft, Shape shape){
        ft.setDuration(Duration.seconds(1));
        ft.setInterpolator(Interpolator.DISCRETE);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setNode(shape);
    }

    private void bindLightTransition(FadeTransition ft, Shape shape){
        ft.setDuration(Duration.seconds(1));
        ft.setInterpolator(Interpolator.DISCRETE);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setNode(shape);
    }

    public void playTransition(ActionEvent event){
        st.play();
    }

    public void stopTransition(ActionEvent event){
        st.pause();
    }

    @FXML private AnchorPane scenePane;

    Stage stage;
    public void logout(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Think twice");

        if (alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
        }
    }
}
