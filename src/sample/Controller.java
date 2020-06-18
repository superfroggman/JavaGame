package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML
    private Rectangle ball;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Rectangle paddleL;
    @FXML
    private Rectangle paddleR;


    double spdMultiplier = 0.0000005;

    double gameW = 800;
    double gameH = 500;

    double ballW = 20;
    double ballH = 20;

    double ballX = 0;
    double ballY = 0;
    double ballXspd = 0;
    double ballYspd = 0;

    double paddleLY = 0;
    double paddleLW = 20;
    double paddleLH = 100;

    double paddleRY = 0;
    double paddleRW = 20;
    double paddleRH = 100;

    double paddleWallOffset = 20;

    long prevTime = 0;

    boolean keyLUP = false;
    boolean keyLDown = false;

    boolean keyRUP = false;
    boolean keyRDown = false;

    public void roundSetup() {
        //Setup game container
        gameGrid.setStyle("-fx-background-color: #C0C0C0;");
        gameGrid.setPrefSize(gameW, gameH);

        //Set ball size
        ball.setWidth(ballW);
        ball.setHeight(ballH);

        //Center ball
        ballX = gameW / 2 - ballW / 2;
        ballY = gameH / 2 - ballH / 2;

        //Setup paddles
        paddleL.setWidth(paddleLW);
        paddleL.setHeight(paddleLH);

        paddleR.setWidth(paddleRW);
        paddleR.setHeight(paddleRH);

        paddleL.setX(paddleWallOffset);
        paddleR.setX(gameW - paddleWallOffset - paddleRW);

        paddleLY = gameH / 2 - paddleLH / 2;
        paddleRY = gameH / 2 - paddleRH / 2;


        //Set initial ball speed
        ballXspd = Math.random() * spdMultiplier;
        if (Math.random() < .5) ballXspd *= -1; //50% chance at inverting direction
        ballYspd = Math.random() * spdMultiplier;
        if (Math.random() < .5) ballYspd *= -1; //50% chance at inverting direction

        System.out.println("xSpd: " + ballXspd + " ySpd: " + ballYspd);


        //Get initial time
        final long startNanoTime = System.nanoTime();
        prevTime = startNanoTime;

        //Continuously looping game loop
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                long dT = currentNanoTime - prevTime;

                onNewFrame(dT);

                prevTime = currentNanoTime;
            }
        }.start();
    }

    /**
     * To be ran every frame
     *
     * @param dT
     */
    public void onNewFrame(long dT) {




        ballX += ballXspd * dT;
        ballY += ballYspd * dT;

        ball.setX(ballX);
        ball.setY(ballY);


        paddleL.setY(paddleLY);
        paddleR.setY(paddleRY);


    }

    private void checkWallCollision(){
        //Collision checking between ball and wall
        if (ballX + ballW >= gameW) {
            ballX = gameW - ballW;
            ballXspd *= -1;
        }
        if (ballX <= 0) {
            ballX = 0;
            ballXspd *= -1;
        }
        if (ballY + ballH >= gameH) {
            ballY = gameH - ballH;
            ballYspd *= -1;
        }
        if (ballY <= 0) {
            ballY = 0;
            ballYspd *= -1;
        }
    }

    private void checkPaddleCollision(){
        paddleL.setFill(Color.CHOCOLATE);
        paddleR.setFill(Color.CHOCOLATE);

        //check collision with left paddle
        if (colliding(ballX, ballW, paddleL.getX(), paddleLW)) {
            if (colliding(ballY, ballH, paddleLY, paddleLH)) {
                if (ballXspd < 0) ballXspd *= -1;
                paddleL.setFill(Color.CRIMSON);
            }
        }

        //check collision with right paddle
        if (colliding(ballX, ballW, paddleR.getX(), paddleRW)) {
            if (colliding(ballY, ballH, paddleRY, paddleRH)) {
                if (ballXspd > 0) ballXspd *= -1;
                paddleR.setFill(Color.CRIMSON);
            }
        }
    }

    private void checkInputs(){
        Main.mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("pressed: " + event.getCode());
                switch (event.getCode()) {
                    case W:
                        keyLUP = true;
                        break;
                    case S:
                        keyLDown = true;
                        break;
                    case UP:
                        keyRUP = true;
                        break;
                    case DOWN:
                        keyRDown = true;
                        break;
                }
            }
        });

        Main.mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("released: " + event.getCode());
                switch (event.getCode()) {
                    case W:
                        keyLUP = false;
                        break;
                    case S:
                        keyLDown = false;
                        break;
                    case UP:
                        keyRUP = false;
                        break;
                    case DOWN:
                        keyRDown = false;
                        break;
                }
            }
        });
    }

    private boolean colliding(double obj1Pos, double obj1Size, double obj2Pos, double obj2Size) {
        return (obj1Pos >= obj2Pos && obj1Pos <= obj2Pos + obj2Size) || (obj1Pos + obj1Size >= obj2Pos && obj1Pos + obj1Size <= obj2Pos + obj2Size);
    }

    /**
     * On start button pressed
     *
     * @param actionEvent
     */
    public void nicebutton(ActionEvent actionEvent) {
        roundSetup();
    }
}
