package edu.wit.comp2000.beznosm.adt6;

/**
 * The node directions
 */
public enum Direction {
    Up("↑"),
    Down("→"),
    Left("←"),
    Right("→");
    private String dir;
    Direction(String dir){
        this.dir = dir;
    }
    /**
     * Returns the enums opposite direction
     * @return The opposite direction
     */
    public Direction getOpposite(){
        if (this == Up)
            return this.Down;
        else if (this == Down)
            return this.Up;
        else if (this == this.Left)
            return this.Right;
        return this.Left;
    }
    @Override
    public String toString(){
        return this.dir;
    }
}
