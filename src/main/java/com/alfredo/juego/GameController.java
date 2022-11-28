package com.alfredo.juego;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameController {
    private StackPane court;
    private Rectangle racketLeft, racketRight;
    private Circle ball;
    private Rectangle wallLeft, wallRight, wallUp, wallDown;
    private Timeline animation;
    private Text scoreLeft, scoreRight;
    private int scoreLeftInt = 0, scoreRightInt = 0;


    private double ballSpeed = 1;
    private double racketSpeed = 6;
    private boolean up = false;
    private boolean down = true;
    private boolean left = false;
    private boolean right = true;
    private boolean work = true;


    private String pointSound = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "audio" + File.separator + "point.wav";
    private String collisionSound = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "audio" + File.separator + "colision.wav";
    private String bgm = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "audio" + File.separator + "bgm2.wav";
    private String pause = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "audio" + File.separator + "pause.wav";
    private String unpause = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "audio" + File.separator + "unpause.wav";
    private String restart = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "audio" + File.separator + "restart.wav";

    private AudioInputStream bgMusic;
    private Clip music;

    private double positionY;
    private double positionY2;

    public GameController(StackPane court, Rectangle racketLeft, Rectangle racketRight, Circle ball, Rectangle wallLeft, Rectangle wallRight, Rectangle wallUp, Rectangle wallDown, Text scoreLeft, Text scoreRight) {
        this.court = court;
        this.racketLeft = racketLeft;
        this.racketRight = racketRight;
        this.ball = ball;
        this.wallLeft = wallLeft;
        this.wallRight = wallRight;
        this.wallUp = wallUp;
        this.wallDown = wallDown;
        this.scoreLeft = scoreLeft;
        this.scoreRight = scoreRight;


        initGame();
    }

    /**
     * Method to start the movement of the game
     */
    private void initGame() {
        initControls();
        animation = new Timeline(new KeyFrame(Duration.millis(17), t -> {
            try {
                detectCollision();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            moveBall();
        }));
        animation.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Method to init game with keyboard
     */
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
                        try {
                            bgMusic = AudioSystem.getAudioInputStream(new File(bgm).getAbsoluteFile());
                            music = AudioSystem.getClip();
                            music.open(bgMusic);
                            music.start();
                            animation.play();
                        } catch (UnsupportedAudioFileException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (LineUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                        animation.play();
                    }
                    if (v.getCode().equals(KeyCode.M)) {
                        if (work) {
                            try {
                                pauseSound();
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                e.printStackTrace();
                            }
                            music.stop();
                            animation.stop();
                            work = false;
                        } else {
                            try {
                                unpauseSound();
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                e.printStackTrace();
                            }
                            music.start();
                            animation.play();
                            work = true;
                        }
                    }
                    if (v.getCode().equals(KeyCode.R)) {
                        try {
                            restartSound();
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                            e.printStackTrace();
                        }
                        scoreLeft.setText("0 points");
                        scoreRight.setText("0 points");
                        scoreLeftInt = 0;
                        scoreRightInt = 0;
                        ball.setTranslateX(0);
                        ball.setTranslateY(0);
                        music.close();
                        animation.stop();
                    }

                }
        );
        racketLeft.setOnMouseDragged(v -> {
            racketLeft.setTranslateY(v.getScreenY() - positionY);
        });
        racketLeft.setOnMousePressed(v -> {
            positionY = v.getScreenY();
        });
        racketRight.setOnMouseDragged(v -> {
            racketRight.setTranslateY(v.getScreenY() - positionY2);
        });
        racketRight.setOnMousePressed(v -> {
            positionY2 = v.getScreenY();
        });
    }

    /**
     * Method to detect the collision the ball and rackets
     *
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     * @throws IOException
     */
    private void detectCollision() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
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
        if (ball.getBoundsInParent().intersects(wallDown.getBoundsInParent())) {
            collisionSound();
            up = true;
            down = false;
        }
        if (ball.getBoundsInParent().intersects(wallUp.getBoundsInParent())) {
            collisionSound();
            up = false;
            down = true;
        }
        if (ball.getBoundsInParent().intersects(racketLeft.getBoundsInParent())) {
            collisionSound();
            right = true;
            left = false;
            ballSpeed += 0.5;
        }
        if (ball.getBoundsInParent().intersects(racketRight.getBoundsInParent())) {
            collisionSound();
            right = false;
            left = true;
            ballSpeed += 0.5;
        }
        if (ball.getBoundsInParent().intersects(wallLeft.getBoundsInParent())) {
            collisionSound();
            scorePoints('L');
            pointSound();
            ballSpeed = 1;
            ball.setTranslateX(0);
            ball.setTranslateY(0);
            animation.stop();
        }
        if (ball.getBoundsInParent().intersects(wallRight.getBoundsInParent())) {
            collisionSound();
            scorePoints('R');
            pointSound();
            ballSpeed = 1;
            ball.setTranslateX(0);
            ball.setTranslateY(0);
            animation.stop();
        }
    }

    /**
     * Method to move the ball
     */
    private void moveBall() {
        if (down && right) {
            ball.setTranslateX(ball.getTranslateX() + ballSpeed);
            ball.setTranslateY(ball.getTranslateY() + ballSpeed);

        } else if (down && left) {
            ball.setTranslateX(ball.getTranslateX() - ballSpeed);
            ball.setTranslateY(ball.getTranslateY() + ballSpeed);

        } else if (up && right) {
            ball.setTranslateX(ball.getTranslateX() + ballSpeed);
            ball.setTranslateY(ball.getTranslateY() - ballSpeed);

        } else if (up && left) {
            ball.setTranslateX(ball.getTranslateX() - ballSpeed);
            ball.setTranslateY(ball.getTranslateY() - ballSpeed);

        }
    }

    /**
     * Method to score points
     *
     * @param jugador
     */
    private void scorePoints(char jugador) {
        if (jugador == 'R') {
            scoreLeftInt++;
            scoreLeft.setText(scoreLeftInt + " points");
        } else {
            scoreRightInt++;
            scoreRight.setText(scoreRightInt + " points");
        }
    }

    /**
     * Method to get the sound of the point
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void pointSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(pointSound).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    /**
     * Method to get the collision sound
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void collisionSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(collisionSound).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    /**
     * Method to get the sound of pause
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void pauseSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(pause).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    /**
     * Method to get the sound of unpauseSound
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void unpauseSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(unpause).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    /**
     * Method to get the restart sound
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void restartSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(restart).getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
}