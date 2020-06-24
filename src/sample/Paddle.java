package sample;


import javafx.scene.shape.Rectangle;

public class Paddle {

    //Configurable variables
    double width = 20;
    double height = 100;
    double wallOffset = 20;
    double speedMultiplier = 0.0000005;

    //Non configurable variables
    double x = 0;
    double y = 0;
    boolean lPaddle = false;
    Rectangle rect;




    public Paddle(boolean inLPaddle, double gameW, double gameH){
        lPaddle = inLPaddle;

        if(lPaddle){
            x = wallOffset;
        }
        else {
            x = gameW - wallOffset - width;
        }
        y = gameH / 2 - height / 2;
        
        rect = new Rectangle(x, y, width, height);
    }

    public void move(boolean up, boolean down, double dT, double gameH){
        if (up) {
            y -= speedMultiplier * dT;
            if(y <= 0){
                y = 0;
            }
        }

        if (down){
            y += speedMultiplier * dT;
            if(y + height >= gameH){
                y = gameH - height;
            }
        }

        rect.setY(y);
    }
}
