package com.alfredo.juego;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class GameController {
    private Rectangle racketLeft, racketRight;
    private Circle ball;
    private Rectangle wallLeft, wallRight, wallUp, wallDown;

    public GameController(Rectangle racketLeft, Rectangle racketRight, Circle ball, Rectangle wallLeft, Rectangle wallRight, Rectangle wallUp, Rectangle wallDown) {
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
    }

    private void initControls() {
    }
}