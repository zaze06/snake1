package me.alien.snake;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;

import static me.alien.snake.Main.main;

public class SnakePart {
    Rectangle body;
    SnakePart child = null;
    boolean head = false;

    public SnakePart(boolean head) {
        this.head = head;
        body = new Rectangle((Main.main.getWidth()/2/10*10), (Main.main.getHeight()/2/10*10), 10,10);
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(new ColorUIResource(3, 206, 0));
        if(head){
            g2d.setColor(new ColorUIResource(3, 108, 0));
        }
        g2d.fill(body);
        if(child != null){
            child.draw(g2d);
        }
    }

    public void addLenght(int i){
        if(child == null){
            child = new SnakePart(false);
            child.body.setLocation(body.getLocation());
            if(i-1 > 0){
                child.addLenght(i-1);
            }
        }else{
            child.addLenght(i);
        }
    }

    public boolean existsIn(int x, int y) {
        boolean contains = body.contains(x, y);
        if(!contains){
            if(child != null) {
                return child.existsIn(x, y);
            }
        }
        return contains;
    }

    public void moveTo(Vector2I step) {
        if(child != null){
            child.moveTo(new Vector2I(body.x, body.y));
        }
        body.setLocation(step.getX(), step.getY());
    }

    public int getSize(){
        if(child != null){
            return child.getSize()+1;
        }
        return 1;
    }
}
