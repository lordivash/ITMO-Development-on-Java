package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//        Group root = new Group();
//
//        Text text = new Text("yoloKOADWAODAOWdoaw");
//        text.setX(10);
//        text.setY(5);
//
//        root.getChildren().add(text);
//
//        Circle circle = new Circle();
//        circle.setCenterX(250);
//        circle.setCenterY(200);
//        circle.setRadius(50);
//        circle.setFill(Color.RED);
//
//        root.getChildren().add(circle);
//
//        Rectangle rectangle = new Rectangle();
//        rectangle.setX(200);
//        rectangle.setY(100);
//        rectangle.setHeight(100);
//        rectangle.setWidth(100);
//        rectangle.setFill(Color.YELLOW);
//
//        root.getChildren().add(rectangle);
//
//        Polygon triangle = new Polygon();
//        triangle.getPoints().setAll(
//                200d, 200d,
//                300d, 300d,
//                200d, 300d
//        );
//        triangle.setFill(Color.GREEN);
//
//        root.getChildren().add(triangle);

        stage.setTitle("Drawer");
//        stage.setWidth(600);
//        stage.setHeight(300);

        stage.setScene(new Scene(root));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
