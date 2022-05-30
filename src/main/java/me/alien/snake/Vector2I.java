package me.alien.snake;

public class Vector2I {
    int x;
    int y;

    public Vector2I(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void addX(int x){
        this.x += x;
    }

    public void addY(int y){
        this.y += y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2I{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2I)) return false;

        Vector2I vector2I = (Vector2I) o;

        if (x != vector2I.x) return false;
        return y == vector2I.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Vector2I minus(Vector2I vector2I) {
        //System.out.print("("+vector2I.getX()+"-"+x+";"+ vector2I.getY()+"-"+y+")");
        return new Vector2I(vector2I.getX() - x, vector2I.getY() - y);
    }
}
