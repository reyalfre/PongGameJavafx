package com.alfredo.juego;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ViewGame extends BorderPane {
    private GameController controller;
    private Rectangle racketLeft, racketRight;
    private Circle ball;
    private StackPane court;

    private Rectangle wallLeft, wallRight, wallUp, wallDown;

    public ViewGame() {
        this.court = new StackPane();

        this.racketLeft = new Rectangle();
        this.racketLeft.setFill(Color.BLACK);
        this.racketLeft.heightProperty().bind(court.heightProperty().divide(5));
        this.racketLeft.widthProperty().bind(court.heightProperty().divide(20));
        this.racketLeft.translateXProperty().bind(court.widthProperty().divide(-2.1));

        this.racketRight = new Rectangle();
        this.racketRight.setFill(Color.BLACK);
        this.racketRight.heightProperty().bind(court.heightProperty().divide(5));
        this.racketRight.widthProperty().bind(court.heightProperty().divide(20));
        this.racketRight.translateXProperty().bind(court.widthProperty().divide(2.1));
        /*this.racketRight.heightProperty().bind(this.heightProperty().divide(10));
        this.racketRight.widthProperty().bind(this.heightProperty().divide(40));*/
        this.racketRight.setFill(Color.RED);



        this.ball = new Circle();
        this.racketRight.setFill(Color.BLACK);
        this.ball.radiusProperty().bind(racketRight.widthProperty().divide(2));


        this.wallLeft = new Rectangle();
        this.wallLeft.heightProperty().bind(court.heightProperty());
        this.wallLeft.widthProperty().bind(court.heightProperty().divide(20));
        this.wallLeft.translateXProperty().bind(court.widthProperty().divide(-2));
        this.wallLeft.setFill(Color.BLUE);


        this.wallRight = new Rectangle();
        this.wallRight.heightProperty().bind(court.heightProperty());
        this.wallRight.widthProperty().bind(court.heightProperty().divide(20));
        this.wallRight.translateXProperty().bind(court.widthProperty().divide(2));
        this.wallRight.setFill(Color.YELLOW);


        this.wallUp = new Rectangle();
        this.wallUp.heightProperty().bind(wallRight.widthProperty());
        this.wallUp.widthProperty().bind(court.widthProperty());
        this.wallUp.translateYProperty().bind(court.heightProperty().divide(-2));
        this.wallUp.setFill(Color.GREEN);


        this.wallDown = new Rectangle(15,10);
        this.wallDown.heightProperty().bind(wallRight.widthProperty());
        this.wallDown.widthProperty().bind(court.widthProperty());
        this.wallDown.translateYProperty().bind(court.heightProperty().divide(2));
        this.wallDown.setFill(Color.CHOCOLATE);

        controller = new GameController(court,racketLeft, racketRight,ball, wallLeft, wallRight, wallUp, wallDown);
        this.court.getChildren().addAll(wallRight,wallLeft,wallUp,wallDown,racketLeft,racketRight,ball);
        this.setCenter(court);
    }
}
