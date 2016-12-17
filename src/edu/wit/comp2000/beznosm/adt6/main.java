package edu.wit.comp2000.beznosm.adt6;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Mark on 12/16/2016.
 */
public class main {
    public static void main(String[] args){
//        String m = "████████████E█\n" +
//                "█          █ █\n" +
//                "█ ██████████ █\n" +
//                "█ █          █\n" +
//                "█ █ ████████ █\n" +
//                "█          ███\n" +
//                "█ ████████ █ █\n" +
//                "███      █ █ █\n" +
//                "███ ██ ███   █\n" +
//                "█          █ █\n" +
//                "████████████X█";
        File m = new File("./maze.txt");
        try{
            Maze maze = new Maze(m);
            System.out.println(Arrays.toString(maze.findPath().toArray()));

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
