package sample;

import javafx.scene.paint.Color;

public class Ball {

    double width = 20;
    double height = 20;

    double x = 0;
    double y = 0;
    double xSpeed = 0;
    double ySpeed = 0;

    public void checkPaddleCollision(Paddle paddleL, Paddle paddleR) {
        //check collision with left paddle
        if (colliding(x, width, paddleL.x, paddleL.width)) {
            if (colliding(ySpeed, height, paddleL.y, paddleL.height)) {
                if (xSpeed < 0) xSpeed *= -1;

            }
        }

        //check collision with right paddle
        if (colliding(x, width, paddleR.x, paddleR.width)) {
            if (colliding(ySpeed, height, paddleR.y, paddleR.height)) {
                if (xSpeed > 0) xSpeed *= -1;
            }
        }
    }

    private boolean colliding(double obj1Pos, double obj1Size, double obj2Pos, double obj2Size) {
        return (obj1Pos >= obj2Pos && obj1Pos <= obj2Pos + obj2Size) || (obj1Pos + obj1Size >= obj2Pos && obj1Pos + obj1Size <= obj2Pos + obj2Size);
    }
}
