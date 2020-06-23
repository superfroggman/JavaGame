package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML
    private Rectangle ballRect;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Rectangle paddleLRect;
    @FXML
    private Rectangle paddleRRect;


    double ballSpdMultiplier = 0.000001;

    double gameW = 800;
    double gameH = 500;

    Ball ball = new Ball();

    Paddle paddleL = new Paddle();

    Paddle paddleR = new Paddle();

    long prevTime = 0;

    boolean keyLUp = false;
    boolean keyLDown = false;

    boolean keyRUp = false;
    boolean keyRDown = false;

    public void roundSetup() {
        //Setup game container
        gameGrid.setStyle("-fx-background-color: #C0C0C0;");
        gameGrid.setPrefSize(gameW, gameH);

        //Set ball size
        ballRect.setWidth(ball.width);
        ballRect.setHeight(ball.height);

        //Center ball
        ball.x = gameW / 2 - ball.width / 2;
        ball.y = gameH / 2 - ball.height / 2;


        //Setup paddles
        paddleLRect.setWidth(paddleL.width);
        paddleLRect.setHeight(paddleL.height);

        paddleRRect.setWidth(paddleR.width);
        paddleRRect.setHeight(paddleR.height);

        paddleLRect.setX(paddleL.wallOffset);
        paddleRRect.setX(gameW - paddleR.wallOffset - paddleR.width);

        paddleL.x = paddleLRect.getX();
        paddleR.x = paddleRRect.getX();

        paddleL.y = gameH / 2 - paddleL.height / 2;
        paddleR.y = gameH / 2 - paddleR.height / 2;


        //Set initial ball speed
        ball.xSpeed = Math.random();
        if (ball.xSpeed < .5) {
            ball.xSpeed = .5;
        }
        ball.xSpeed *= ballSpdMultiplier;
        if (Math.random() < .5) ball.xSpeed *= -1; //50% chance at inverting direction
        ball.ySpeed = Math.random() * ballSpdMultiplier;
        if (Math.random() < .5) ball.ySpeed *= -1; //50% chance at inverting direction

        System.out.println("xSpd: " + ball.xSpeed + " ySpd: " + ball.ySpeed);


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
        checkWallCollision();
        checkPaddleCollision();
        checkInputs();

        //Move paddles based on inputs
        paddleL.move(keyLUp, keyLDown, dT, gameH);
        paddleR.move(keyRUp, keyRDown, dT, gameH);

        ball.x += ball.xSpeed * dT;
        ball.y += ball.ySpeed * dT;

        ballRect.setX(ball.x);
        ballRect.setY(ball.y);


        paddleLRect.setY(paddleL.y);
        paddleRRect.setY(paddleR.y);
    }

    private void checkWallCollision() {
        //Collision checking between ball and wall
        if (ball.x + ball.width >= gameW) {
            ball.x = gameW - ball.width;
            ball.xSpeed *= -1;
            System.out.println("L win point");
        }
        if (ball.x <= 0) {
            ball.x = 0;
            ball.xSpeed *= -1;
            System.out.println("R win point");
        }
        if (ball.y + ball.height >= gameH) {
            ball.y = gameH - ball.height;
            ball.ySpeed *= -1;
        }
        if (ball.y <= 0) {
            ball.y = 0;
            ball.ySpeed *= -1;
        }
    }

    private void checkPaddleCollision() {
        paddleLRect.setFill(Color.CHOCOLATE);
        paddleRRect.setFill(Color.CHOCOLATE);

        //ball.checkPaddleCollision(paddleL, paddleR);

        //check collision with left paddle
        if (colliding(ball.x, ball.width, paddleLRect.getX(), paddleL.width)) {
            if (colliding(ball.y, ball.height, paddleL.y, paddleL.height)) {
                if (ball.xSpeed < 0) ball.xSpeed *= -1;
                paddleLRect.setFill(Color.CRIMSON);
            }
        }

        //check collision with right paddle
        if (colliding(ball.x, ball.width, paddleRRect.getX(), paddleR.width)) {
            if (colliding(ball.y, ball.height, paddleR.y, paddleR.height)) {
                if (ball.xSpeed > 0) ball.xSpeed *= -1;
                paddleRRect.setFill(Color.CRIMSON);
            }
        }
    }

    private void checkInputs() {
        Main.mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        keyLUp = true;
                        break;
                    case S:
                        keyLDown = true;
                        break;
                    case O:
                        keyRUp = true;
                        break;
                    case L:
                        keyRDown = true;
                        break;
                }
            }
        });

        Main.mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                        keyLUp = false;
                        break;
                    case S:
                        keyLDown = false;
                        break;
                    case O:
                        keyRUp = false;
                        break;
                    case L:
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
