package com.alfredo.juego;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ViewGame extends StackPane {
    //private StackPane pista;
    private GameController controller;
    private Rectangle racketLeft, racketRight;
    private Circle ball;

    private Rectangle wallLeft, wallRight, wallUp, wallDown;

    public ViewGame() {
        //pista = new StackPane();
        this.racketLeft = new Rectangle(10,10);
        this.racketLeft.setFill(Color.BLACK);

        this.racketLeft.heightProperty().bind(this.heightProperty().divide(5));

        this.racketLeft.widthProperty().bind(this.heightProperty().divide(20));
        this.racketLeft.translateXProperty().bind(this.widthProperty().divide(-2.1));

        this.getChildren().add(racketLeft);

        this.racketRight = new Rectangle(10, 10);

        this.racketRight.heightProperty().bind(this.heightProperty().divide(5));

        this.racketRight.widthProperty().bind(this.heightProperty().divide(20));
        this.racketRight.translateXProperty().bind(this.widthProperty().divide(2.1));
        /*this.racketRight.heightProperty().bind(this.heightProperty().divide(10));
        this.racketRight.widthProperty().bind(this.heightProperty().divide(40));*/
        this.racketRight.setFill(Color.RED);

        this.getChildren().add(racketRight);

        this.ball = new Circle(5, 5,5);

        this.getChildren().add(ball);

        this.wallLeft = new Rectangle(1, 10);
        this.wallLeft.heightProperty().bind(this.heightProperty().divide(1));
        this.wallLeft.widthProperty().bind(this.heightProperty().divide(20));
        this.wallLeft.translateXProperty().bind(this.widthProperty().divide(-1.95));
        this.wallLeft.setFill(Color.BLUE);
        this.getChildren().add(wallLeft);

        this.wallRight = new Rectangle(10, 10);
        this.wallRight.heightProperty().bind(this.heightProperty().divide(1));
        this.wallRight.widthProperty().bind(this.heightProperty().divide(20));
        this.wallRight.translateXProperty().bind(this.widthProperty().divide(1.95));
        this.wallRight.setFill(Color.YELLOW);
        this.getChildren().add(wallRight);

        this.wallUp = new Rectangle(15,10);
        this.wallUp.heightProperty().bind(this.heightProperty().divide(30));
        this.wallUp.widthProperty().bind(this.heightProperty().divide(0.1));
        this.wallUp.translateYProperty().bind(this.heightProperty().divide(-1.95));
        this.wallUp.setFill(Color.GREEN);
        this.getChildren().add(wallUp);

        this.wallDown = new Rectangle(15,10);
        this.wallDown.heightProperty().bind(this.heightProperty().divide(30));
        this.wallDown.widthProperty().bind(this.heightProperty().divide(0.1));
        this.wallDown.translateYProperty().bind(this.heightProperty().divide(1.95));
        this.wallDown.setFill(Color.CHOCOLATE);
        this.getChildren().add(wallDown);

        controller = new GameController(racketLeft, racketRight,ball, wallLeft, wallRight, wallUp, wallDown);



    }
}
