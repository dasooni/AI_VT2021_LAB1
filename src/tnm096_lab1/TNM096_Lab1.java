/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tnm096_lab1;

import java.util.*;

/**
 *
 * @author Ludwig
 */
public class TNM096_Lab1 {

    /**
     * @param args the command line arguments
     */
    
    //HashSet for storing what states have already been visited
    //(Contains string representations of the "matrix")
    private static HashSet<String> closedSet = new HashSet();
    
    //PriorityQueue for storing unexplored states (in order)
    private static PriorityQueue<State> openQueue = new PriorityQueue(new Comparator<State>() { 
        
            //States sorted based on f value
            @Override
            public int compare(State o1, State o2) {
                return o1.getF() - o2.getF();
            }
        });
    
    
    public static void main(String[] args) {
        
        // MEDIUM MATRIX
        int[][] beginningMatrix = {
           {2, 5, 0},
           {1, 4, 8},
           {7, 3, 6}
        };
       
        //DIFFICULT MATRIX
//        int[][] beginningMatrix = {
//           {6, 4, 7},
//           {8, 5, 0},
//           {3, 2, 1}
//        };
        
        //MOST DIFFICULT MATRIX
//         int[][] beginningMatrix = {
//           {8, 6, 7},
//           {2, 5, 4},
//           {3, 0, 1}
//        };
       

        //The beginning state of the puzzle
        State beginningState = new State(beginningMatrix, 0, new ArrayList<State>());
        
        //List for temporarily storing the new states
        ArrayList<State> expandedStates;
        
        //Add beginning state to openQueue
        openQueue.add(beginningState);
        
        //Loop for finding the optimal solution:
        theBigLoop:
        while(true){
            
            //Expand the first state in queue
            expandedStates = openQueue.element().expandState();

            //Remove that same state from openQueue, and add (string hash) 
            //of it to closedSet
            closedSet.add(openQueue.poll().getStateString());

              //"Inspecting" the new states (Using HashSet)
              for(int i = 0; i < expandedStates.size(); i++){
                State tempState = expandedStates.get(i);
                //Checks that state isnt already visited (LOOP CHECKER)
                if(!(closedSet.contains(tempState.getStateString()))){
                    //Checks if state is final solution
                    if(tempState.getH() == 0){
                    //System.out.println("TEMP! SIZE OF openQueue = " + openQueue.size());
                    System.out.println("FINAL SOULTION FOUND!" + " \nNumber of steps to solution = " + tempState.getC());
                    System.out.println("The steps taken are:");
                    System.out.println("" + tempState.getHistoryList() + "\n" + tempState);
                    break theBigLoop;
                    } 
                    //Adds new state to the queue
                    openQueue.add(tempState);
                }
            }
     
        }
    }
}