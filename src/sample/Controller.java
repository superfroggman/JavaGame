package sample;

import javafx.animation.AnimationTimer;

public class Controller {


    static double spdMultiplier = 1;

    static int ballX = 0;
    static int ballY = 0;
    static double ballXspd = 0;
    static double ballYspd = 0;

    static int paddleLY = 0;
    static int paddleRY = 0;

    static long prevTime = 0;

    public static void roundSetup() {
        ballX = 0;
        ballY = 0;
        paddleLY = 0;
        paddleRY = 0;

        //Set initial ball speed
        ballXspd = Math.random() * spdMultiplier;
        if (Math.random() < .5) ballXspd *= -1; //50% chance at inverting direction
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

    public static void onNewFrame(long dT) {

        System.out.println("new frame, dT: " + dT);

    }
}
