package me.alien.snake;

import es.usc.citius.hipster.algorithm.Algorithm;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HipsterDirectedGraph;
import es.usc.citius.hipster.model.impl.WeightedNode;
import es.usc.citius.hipster.model.problem.SearchProblem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JPanel implements ActionListener {

    int speed = 50;

    JFrame frame = new JFrame();
    Timer timer = new Timer(100, this);
    SnakePart head;
    Apple apple;
    public static Main main = null;
    private ArrayList<Vector2I> path;
    private boolean workingOnIt;
    private int pathNum;
    Timer move = new Timer(speed, e -> {
        if(path.isEmpty() && !workingOnIt){
            workingOnIt = true;
            newPath();
            workingOnIt = false;
            System.out.println("head: "+new Vector2I(head.body.x, head.body.y)+", apple: "+new Vector2I(apple.rect.x, apple.rect.y));
            System.out.println(Arrays.toString(path.toArray()));
            pathNum++;
        }
        if(!path.isEmpty()){
            Vector2I step = path.get(0);
            head.moveTo(step);
            path.remove(0);
        }
    });

    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        frame.setSize(400, 300);
        frame.setMaximumSize(frame.getSize());
        frame.setMinimumSize(frame.getSize());
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main = this;
        head = new SnakePart(true);
        head.addLenght(3);
        apple = new Apple(1,1);
        newPath();

        timer.start();
        move.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (head == null || apple == null) {
            return;
        }
        head.draw(g2d);
        apple.draw(g2d);
        if(head.body.contains(apple.rect)){
            pathNum = 0;
            apple.eaten();
            head.addLenght(2);
            workingOnIt = true;
            newPath();
            workingOnIt = false;
        }
    }

    private void newPath() {
        long time = System.currentTimeMillis();
        GraphBuilder<Vector2I, Integer> graphBuilder = GraphBuilder.create();

        for(int x = 0; x < (getWidth()/10*10); x+=10){
            for(int y = 0; y < (getHeight()/10*10); y+=10){
                if(!(x-10 < 0)) {
                    if(!head.existsIn(x, y)) {
                        graphBuilder.connect(new Vector2I(x - 10, y)).to(new Vector2I(x, y)).withEdge((x==10?4:1));
                    }
                }
                if(!(x + 10 > getWidth()-10)) {
                    if(!head.existsIn(x, y)) {
                        graphBuilder.connect(new Vector2I(x + 10, y)).to(new Vector2I(x, y)).withEdge((x==getWidth()-10?4:1));
                    }
                }
                if(!(y+10 > getHeight()-10)) {
                    if(!head.existsIn(x, y)) {
                        graphBuilder.connect(new Vector2I(x, y + 10)).to(new Vector2I(x, y)).withEdge((x==getHeight()-20?4:1));
                    }
                }
                if(!(y-10 < 0)) {
                    if(!head.existsIn(x, y)) {
                        graphBuilder.connect(new Vector2I(x, y - 10)).to(new Vector2I(x, y)).withEdge((x==10?4:1));
                    }
                }
            }
        }
        HipsterDirectedGraph<Vector2I, Integer> graph = graphBuilder.createDirectedGraph();
        SearchProblem<Integer, Vector2I, WeightedNode<Integer, Vector2I, Double>> p = GraphSearchProblem.startingFrom(new Vector2I(head.body.x, head.body.y)).in(graph).takeCostsFromEdges().build();
        Algorithm<Integer, Vector2I, WeightedNode<Integer, Vector2I, Double>>.SearchResult search;
        if(pathNum > 7){
            move.stop();
            System.out.println("Stoping! generating path to (1,1)");
            search = Hipster.createMultiobjectiveLS(p).search(new Vector2I(1,1));
        }else {
            search = Hipster.createMultiobjectiveLS(p).search(new Vector2I(apple.rect.x, apple.rect.y));
        }
        ArrayList<Vector2I> path1 = null;
        try{
            path1 = new ArrayList<>(search.getOptimalPaths().get(0));
            path1.remove(0);
        }catch (Exception e){
            search = Hipster.createMultiobjectiveLS(p).search(new Vector2I(1,1));
            path1 = new ArrayList<>(search.getOptimalPaths().get(0));
            path1.remove(0);
        }
        System.out.println("Took: "+(System.currentTimeMillis()-time)+" ms");

        path = new ArrayList<>(path1);

        if(!move.isRunning()){
            System.out.println("Moving to (1,1) maybe");
            move.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setTitle("size: "+head.getSize());
        repaint();
    }
}
