package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML
    private Rectangle ball;


    double spdMultiplier = 0.0000005;

    double gameW = 500;
    double gameH = 500;

    double ballW;
    double ballH;

    double ballX = 0;
    double ballY = 0;
    double ballXspd = 0;
    double ballYspd = 0;

    int paddleLY = 0;
    int paddleRY = 0;

    long prevTime = 0;

    public void roundSetup() {
        ballW = ball.getWidth();
        ballH = ball.getHeight();

        ballX = gameW /2 - ballW/2;
        ballY = gameH /2 - ballH/2;
        System.out.println(ballX);

        paddleLY = 0;
        paddleRY = 0;

        //Set initial ball speed
        ballXspd = Math.random() * spdMultiplier;
        if (Math.random() < .5) ballXspd *= 1; //50% chance at inverting direction
        ballYspd = Math.random() * spdMultiplier;
        if (Math.random() < .5) ballYspd *= -1; //50% chance at inverting direction

        System.out.println("xSpd: " + ballXspd + " ySpd: " + ballYspd);


        final long startNanoTime = System.nanoTime();
        prevTime = startNanoTime;

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                long dT = currentNanoTime - prevTime;

                onNewFrame(dT);

                prevTime = currentNanoTime;
            }
        }.start();
    }

    public void onNewFrame(long dT) {

        if(ballX + ballW >= gameW){
            ballX = gameW - ballW;
            ballXspd*=-1;
        }

        ballX += ballXspd*dT;

        System.out.println("ballX: " + ballX);
        System.out.println("new frame, dT: " + dT);

        ball.setX(ballX);
    }

    public void nicebutton(ActionEvent actionEvent) {
        roundSetup();
    }
}
