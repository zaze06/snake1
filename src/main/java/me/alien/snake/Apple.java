package me.alien.snake;

import java.awt.*;

import static me.alien.snake.Main.main;

public class Apple {
    Rectangle rect;

    public Apple(int x, int y){
        rect = new Rectangle(x*10,y*10,10,10);
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.fill(rect);
    }

    public void eaten(){
        int x = (int) (Math.random()*(Main.main.getWidth()/10)), y = (int) (Math.random()*(Main.main.getHeight()/10));
        while(Main.main.head.existsIn(x,y)) {
            x = (int) (Math.random()*(Main.main.getWidth()/10));
            y = (int) (Math.random()*(Main.main.getHeight()/10));
        }
        rect = new Rectangle(x*10,y*10,10,10);
    }
}
