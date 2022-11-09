package com.alfredo.juego;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameController {
    private StackPane court;
    private Rectangle racketLeft, racketRight;
    private Circle ball;
    private Rectangle wallLeft, wallRight, wallUp, wallDown;
    private Timeline animation;
    private Label scoreLeft, scoreRight;
    private int scoreLeftInt = 0, scoreLeftRightInt = 0;


    private double ballSpeed = 1;
    private double racketSpeed = 6;
    private boolean up = false;
    private boolean down = true;
    private boolean left = false;
    private boolean right = true;
    private boolean funcionando = true;


    public GameController(StackPane court, Rectangle racketLeft, Rectangle racketRight, Circle ball, Rectangle wallLeft, Rectangle wallRight, Rectangle wallUp, Rectangle wallDown) {
        this.court = court;
        this.racketLeft = racketLeft;
        this.racketRight = racketRight;
        this.ball = ball;
        this.wallLeft = wallLeft;
        this.wallRight = wallRight;
        this.wallUp = wallUp;
        this.wallDown = wallDown;


        initGame();
    }

    private void initGame() {
        initControls();
        animation = new Timeline(new KeyFrame(Duration.millis(17), t -> {
            try {
                detectarColision();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            moveBall();
        }));
        animation.setCycleCount(Animation.INDEFINITE);
    }

    private void initControls() {
        court.setFocusTraversable(true);

        court.setOnKeyPressed(v -> {
            if (v.getCode().equals(KeyCode.Q)) {
                racketLeft.setTranslateY(racketLeft.getTranslateY() - racketSpeed);
                animation.play();
            }
            if (v.getCode().equals(KeyCode.A)) {
                racketLeft.setTranslateY(racketLeft.getTranslateY() + racketSpeed);
            }
            if (v.getCode().equals(KeyCode.P)) {
                racketRight.setTranslateY(racketRight.getTranslateY() - racketSpeed);
                animation.play();
            }
            if (v.getCode().equals(KeyCode.L)) {
                racketRight.setTranslateY(racketRight.getTranslateY() + racketSpeed);
                animation.play();
            }
            if (v.getCode().equals(KeyCode.SPACE)) {
                //aquí meteré inicio de la música
                animation.play();
            }
        });
    }

    private void detectarColision() {
        if (racketLeft.getBoundsInParent().intersects(wallUp.getBoundsInParent())) {
            racketLeft.setTranslateY(racketLeft.getTranslateY() + 4);
        }
        if (racketLeft.getBoundsInParent().intersects(wallDown.getBoundsInParent())) {
            racketLeft.setTranslateY(racketLeft.getTranslateY() - 4);
        }
        if (racketRight.getBoundsInParent().intersects(wallUp.getBoundsInParent())) {
            racketRight.setTranslateY(racketRight.getTranslateY() + 4);
        }
        if (racketRight.getBoundsInParent().intersects(wallDown.getBoundsInParent())) {
            racketRight.setTranslateY(racketRight.getTranslateY() - 4);
        }
    }

    private void moveBall() {
        if (down && right) {
            ball.setTranslateX(ball.getTranslateX() + ballSpeed);
            ball.setTranslateY(ball.getTranslateY() + ballSpeed);

        } else if (down && right) {
            ball.setTranslateX(ball.getTranslateX() - ballSpeed);
            ball.setTranslateY(ball.getTranslateY() + ballSpeed);

        } else if (up && left) {
            ball.setTranslateX(ball.getTranslateX() + ballSpeed);
            ball.setTranslateY(ball.getTranslateY() + ballSpeed);

        } else if (up && left) {
            ball.setTranslateX(ball.getTranslateX() - ballSpeed);
            ball.setTranslateY(ball.getTranslateY() - ballSpeed);

        }
    }
}