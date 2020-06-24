package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ball {

    //Configurable variables
    double width = 20;
    double height = 20;
    double ballSpdMultiplier = 0.000001;
    Color color = Color.PALEVIOLETRED;

    //Non configurable variables
    double x = 0;
    double y = 0;
    double xSpeed = 0;
    double ySpeed = 0;
    Rectangle rect;
    
    public Ball(double gameW, double gameH){

        //Center ball
        x = gameW / 2 - width / 2;
        y = gameH / 2 - height / 2;

        //Set initial ball speed
        xSpeed = Math.random();
        if (xSpeed < .5) {
            xSpeed = .5;
        }
        xSpeed *= ballSpdMultiplier;
        if (Math.random() < .5) xSpeed *= -1; //50% chance at inverting direction
        ySpeed = Math.random() * ballSpdMultiplier;
        if (Math.random() < .5) ySpeed *= -1; //50% chance at inverting direction

        System.out.println("xSpd: " + xSpeed + " ySpd: " + ySpeed);

        rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
    }

    public void move(double dT){
        x += xSpeed * dT;
        y += ySpeed * dT;

        rect.setX(x);
        rect.setY(y);
    }

    public void checkWallCollision(double gameW, double gameH){
        //Collision checking between ball and wall
        if (x + width >= gameW) {
            x = gameW - width;
            xSpeed *= -1;
            System.out.println("L win point");
        }
        if (x <= 0) {
            x = 0;
            xSpeed *= -1;
            System.out.println("R win point");
        }

        if (y + height >= gameH) {
            y = gameH - height;
            ySpeed *= -1;
        }
        if (y <= 0) {
            y = 0;
            ySpeed *= -1;
        }
    }

    public void checkPaddleCollision(Paddle[] paddles) {
        for(Paddle paddle : paddles) {
            if (colliding(x, width, paddle.x, paddle.width)) {
                if (colliding(y, height, paddle.y, paddle.height)) {
                    if(paddle.lPaddle && xSpeed < 0) xSpeed *= -1;
                    if(!paddle.lPaddle && xSpeed > 0) xSpeed *= -1;
                }
            }
        }

    }

    private boolean colliding(double obj1Pos, double obj1Size, double obj2Pos, double obj2Size) {
        return (obj1Pos >= obj2Pos && obj1Pos <= obj2Pos + obj2Size) ||
                (obj1Pos + obj1Size >= obj2Pos && obj1Pos + obj1Size <= obj2Pos + obj2Size) ||
                (obj1Pos + obj1Size >= obj2Pos && obj1Pos <= obj2Pos + obj2Size) ||
                (obj1Pos >= obj2Pos && obj1Pos + obj1Size <= obj2Pos + obj2Size);
    }
}
