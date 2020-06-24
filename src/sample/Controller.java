package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Controller {

    @FXML
    private GridPane gameGrid;

    @FXML
    private Group gameArea;

    @FXML
    private Group ballContainer;

    Rectangle[] paddleRects = new Rectangle[2];
    Paddle[] paddles = {new Paddle(), new Paddle()};

    ArrayList<Ball> balls = new ArrayList<>();


    double gameW = 800;
    double gameH = 500;


    long prevTime = 0;

    boolean keyLUp = false;
    boolean keyLDown = false;

    boolean keyRUp = false;
    boolean keyRDown = false;


    public void roundSetup() {

        //Setup game container
        gameGrid.setStyle("-fx-background-color: #C0C0C0;");
        gameGrid.setPrefSize(gameW, gameH);


        paddles[0].x = paddles[0].wallOffset;
        paddles[1].x = gameW - paddles[1].wallOffset - paddles[1].width;

        paddles[0].lPaddle = true;

        for (int i = 0; i < paddles.length; i++) {
            paddles[i].y = gameH / 2 - paddles[i].height / 2;
            paddleRects[i] = new Rectangle(paddles[i].x, paddles[i].y, paddles[i].width, paddles[i].height);
        }
        gameArea.getChildren().addAll(paddleRects);

        for (int i = 0; i < 200; i++) {
            newBall();
        }

        //newBall();


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

    private void newBall() {
        Ball ball = new Ball(gameW, gameH);
        balls.add(ball);

        ballContainer.getChildren().addAll(ball.rect);
    }

    /**
     * To be ran every frame
     *
     * @param dT
     */
    public void onNewFrame(long dT) {

        checkInputs();

        //Move paddles based on inputs
        paddles[0].move(keyLUp, keyLDown, dT, gameH);
        paddles[1].move(keyRUp, keyRDown, dT, gameH);

        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).checkWallCollision(gameW, gameH);
            balls.get(i).checkPaddleCollision(paddles);

            balls.get(i).move(dT);
        }


        paddleRects[0].setY(paddles[0].y);
        paddleRects[1].setY(paddles[1].y);
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

    /**
     * On start button pressed
     *
     * @param actionEvent
     */
    public void nicebutton(ActionEvent actionEvent) {
        roundSetup();
    }
}
