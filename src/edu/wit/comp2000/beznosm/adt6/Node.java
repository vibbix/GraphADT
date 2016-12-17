package edu.wit.comp2000.beznosm.adt6;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A single node
 */
public class Node {
    private HashMap<Direction, Node> directions;
    private int x;
    private int y;

    /**
     * Initializes a node at (0,0)
     */
    public Node(){
        this(0,0);
    }

    /**
     * Creates a node with preset coordinates
     * @param x The x coord
     * @param y The y coord
     */
    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.directions = new HashMap<>();
    }

    /**
     * Sets the nodes neighbors, with data propagation
     * @param d Direction to set
     * @param n Node to set
     */
    public void setNode(Direction d, Node n){
        this.directions.put(d, n);
        if (n.getNode(d.getOpposite()) != this)
            n.setNode(d.getOpposite(), this);
    }

    /**
     * Gets a node in the direction passed
     * @param d Direction
     * @return The node in direction d. Else, null.
     */
    public Node getNode(Direction d){
        return this.directions.get(d);
    }

    /**
     * Gets a nodes neighbors
     * @return An arraylist of the nodes neighbors
     */
    public ArrayList<Node> getNeighbors(){
        ArrayList<Node> al = new ArrayList<>();
        for(Direction d: Direction.values()){
            Node n = this.getNode(d);
            if(n != null){
                al.add(n);
            }
        }
        return al;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
