package edu.wit.comp2000.beznosm.adt6;

import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Holds the maze
 */
public class Maze {
    //region statics
    private static final char CHAR_ENTRANCE = 'E';
    private static final char CHAR_EXIT = 'X';
    private static final char CHAR_WALL = '█';
    private static final char CHAR_SPACE = ' ';
    //endregion
    //region variables
    private Node entrance;
    private Node exit;
    private char[][] overlay;
    private ArrayList<Node> nodes;
    //endregion
    /**
     * Creates a new maze
     * @param maze the maze char[][] to solve
     */
    public Maze(char[][] maze){
        createMaze(maze);
        this.nodes = new ArrayList<>();
    }
    public Maze(File maze) throws Exception{
        StringBuilder sb = new StringBuilder();
        try{
            InputStream is = new FileInputStream(maze.getAbsolutePath());
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            String line = buf.readLine();

            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }

        }catch(Exception ex){
            throw ex;
        }
        this.nodes = new ArrayList<>();
        String m = sb.toString();
        m = m.replace("\r\n", "\n");
        ArrayList<char[]> chars = new ArrayList<>();
        for(String line: m.split("\n")){
            line = line.trim();
            chars.add(line.toCharArray());
        }
        createMaze(chars.toArray(new char[][]{}));
    }

    /**
     * Creates a new maze
     * @param maze the maze string
     */
    public Maze(String maze){
        this.nodes = new ArrayList<>();
        maze = maze.replace("\r\n", "\n");
        ArrayList<char[]> chars = new ArrayList<>();
        for(String line: maze.split("\n")){
            line = line.trim();
            chars.add(line.toCharArray());
        }
        createMaze(chars.toArray(new char[][]{}));
    }
    private void createMaze(char[][] maze){
        int rlength = maze.length;
        int clength = maze[0].length;
        this.overlay = maze;
        if (rlength == 0 || clength == 0){
            throw new IllegalArgumentException("Bad dimensions");
        }
        for (int r = 0; r < maze.length; r++){
            for (int c = 0; c < maze[r].length; c++){
                if (maze[r].length != clength){
                    throw new IllegalArgumentException("Bad dimensions");
                }
                Character pos = maze[r][c];
                if (pos == CHAR_WALL){
                    continue;
                } else if (pos == CHAR_ENTRANCE){
                    Node n = createNode(c, r);
                    this.nodes.add(n);
                    if(this.entrance != null){
                        throw new IllegalArgumentException("Cannot have multiple entrances");
                    }
                    this.entrance = n;
                } else if (pos == CHAR_EXIT){
                    Node n = createNode(c, r);
                    this.nodes.add(n);
                    if(this.exit != null){
                        throw new IllegalArgumentException("Cannot have multiple exits");
                    }
                    this.exit = n;
                } else if (pos == CHAR_SPACE){
                    Node n = createNode(c, r);
                    this.nodes.add(n);
                }
                else{
                    throw new IllegalArgumentException("Illegal character at (" + r + ", "+ c +"): "
                            + maze[r][c]);
                }

            }
        }
    }

    /**
     * A innefficient way to get a nodes neighbors
     * @param x the x coord
     * @param y the y coord
     * @return a intialized node
     */
    private Node createNode(int x, int y){
        Node n = new Node(x, y);
        ArrayList<Pair<Direction, Pair<Integer, Integer>>> possible = new ArrayList<>();
        possible.add(new Pair<>(Direction.Up, new Pair<>(x, y - 1)));
        possible.add(new Pair<>(Direction.Down, new Pair<>(x, y + 1)));
        possible.add(new Pair<>(Direction.Left, new Pair<>(x-1,y)));
        possible.add(new Pair<>(Direction.Right, new Pair<>(x+1,y)));
        for(Node cN: this.nodes){
            for(Pair<Direction, Pair<Integer, Integer>> dir : possible){
                if (cN.getX() == dir.getValue().getKey() && cN.getY() == dir.getValue().getValue()){
                    n.setNode(dir.getKey(), cN);
                }
            }
        }
        return n;
    }
    public List<Node> findPath(){
        ArrayList<Node> path = new ArrayList<>();
        HashSet<Node> hs = new HashSet<>();
        this.dfs(hs, this.entrance, path, this.exit);
        System.out.println(Arrays.toString(hs.toArray()));
        return path;
    }
    private void dfs(HashSet<Node> visisted, Node current, List<Node> path, Node target){
        Stack<Node> s = new Stack<>();
        //<child, parent>
        HashMap<Node, Node> hm = new HashMap<>();
        s.push(current);
        while(!s.isEmpty()){
            Node n = s.pop();
            System.out.println(Arrays.toString(s.toArray()));
            if(visisted.contains(n)){
                continue;
            }
            visisted.add(n);
            if(n == target){
                Node qn = n;
                while(qn != null && qn != this.entrance){
                    path.add(qn);
                    qn = hm.get(qn);
                }
                break;
            }
            List<Node> children = n.getNeighbors();
            for (Node cn: children){
                hm.put(cn, n);
                s.push(cn);
            }
        }
    }

}
