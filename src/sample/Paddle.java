package sample;

public class Paddle {

    //Configurable variables
    double width = 20;
    double height = 100;
    double wallOffset = 20;
    double speedMultiplier = 0.0000005;

    //Non configurable variables
    double x = 0;
    double y = 0;


    /*
    public Paddle(){
    }*/

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
    }
}
