/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tnm096_lab1;

import java.util.*;
import java.lang.Math.*;

/**
 *
 * @author Ludwig
 */

public class State {
    
    //Matrix for the final state, the "solution"
    private static final int[][] FINAL_STATE_MATRIX = {
           {1, 2, 3},
           {4, 5, 6},
           {7, 8, 0}
        };
    
    private int[][] stateMatrix;
    private int c, h, f;
    private String stateString;
    private ArrayList<State> historyList;

    public State() {
        
    }
    
    public State(int[][] stateMatrix, int c, ArrayList<State> historyList){
        this.stateMatrix = stateMatrix;
        this.c = c;
        this.historyList = historyList;
        calculateH();
        calculateF();
        generateStateString();
    }
    
    public int getC() {
        return c;
    }
    
    public int getH() {
        return h;
    }
    
    public int getF(){
        return f;
    }

    public String getStateString() {
        return stateString;
    }

    public int[][] getStateMatrix() {
        return stateMatrix;
    }

    public ArrayList<State> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(ArrayList<State> historyList) {
        this.historyList = historyList;
    }
    
    public void setC(int c){
        this.c = c;
    }
    
    public void setH(int h){
        this.h = h;
    }
    
    //Method for calculating h1 (or h2)
    private void calculateH(){
        h = 0;

        //CODE USED FOR H1
//        for(int i = 0; i < 3; i++) {
//            for(int j = 0; j < 3; j++)
//            {
//                //Adds 1 to h1 if tile is in the wrong spot
//                if((stateMatrix[i][j] != 0) && (stateMatrix[i][j] != FINAL_STATE_MATRIX[i][j] )){
//                    h++;
//                }
//            }
//        }

        //CODE USED FOR H2
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++)
            {
                int temp = stateMatrix[i][j];
                h2innerloop:
                 for(int i2 = 0; i2 < 3; i2++) {
                    for(int j2 = 0; j2 < 3; j2++)   {
                        if(FINAL_STATE_MATRIX[i2][j2] == temp) {
                            //Adds manhattan distance to correct position
                           h += (Math.abs(i - i2) + Math.abs(j - j2));
                           break h2innerloop;
                        }
                    }
                 }
            }
        }
        
    }
    
    //Calculates the value of f
    private void calculateF() {
        f = c + h;
    }
    
    //Creates a string for the current state of the matrix
    private void generateStateString() {
        stateString = "";
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                stateString += "" + stateMatrix[i][j];
            }
        }
    }
    
    //Returns a list with all states that can follow the current state
    public ArrayList<State> expandState(){
    
        //List to store the new states generated
        ArrayList<State> returnList = new ArrayList<State>();    

        //Variables for storing what row and column the 0 is located in matrix
        int zeroI = -1, zeroJ = -1;   

        //Find where the "zero" is located
        outerloop:
        for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    //Adds 1 to h1 if tile is in the wrong spot
                    if(stateMatrix[i][j] == 0){
                       zeroI = i;
                       zeroJ = j;
                       break outerloop;
                    }
                }
            }
        
        //ArrayList used to store the "history" of the state
        ArrayList<State> tempHistoryList;    
            
        //Checks in which of the four possible directions that the "zero" can be moved
        if(zeroI < 2){
            //Add state where zero is moved to down
            //Create the new matrix
            int[][] tempMatrix = new int[3][3];
            for(int i = 0 ;i < 3; i++){
                tempMatrix[i] = stateMatrix[i].clone();
            }
            
            tempMatrix[zeroI][zeroJ] = tempMatrix[zeroI+1][zeroJ];
            tempMatrix[zeroI+1][zeroJ] = 0;
            
            tempHistoryList = new ArrayList<State>(historyList);
            tempHistoryList.add(this);

            returnList.add(new State(tempMatrix, c+1, tempHistoryList));
        }
        if(zeroI > 0){
            //Add state where zero is moved up
            //Create the new matrix
            int[][] tempMatrix = new int[3][3];
            for(int i = 0 ;i < 3; i++){
                tempMatrix[i] = stateMatrix[i].clone();
            }
            
            tempMatrix[zeroI][zeroJ] = tempMatrix[zeroI-1][zeroJ];
            tempMatrix[zeroI-1][zeroJ] = 0;

            tempHistoryList = new ArrayList<State>(historyList);
            tempHistoryList.add(this);

            returnList.add(new State(tempMatrix, c+1, tempHistoryList));
        }
        if(zeroJ < 2){
            //Add state where zero is moved to the right
            //Create the new matrix
            int[][] tempMatrix = new int[3][3];
            for(int i = 0 ;i < 3; i++){
                tempMatrix[i] = stateMatrix[i].clone();
            }
            
            tempMatrix[zeroI][zeroJ] = tempMatrix[zeroI][zeroJ+1];
            tempMatrix[zeroI][zeroJ+1] = 0;

            tempHistoryList = new ArrayList<State>(historyList);
            tempHistoryList.add(this);

            returnList.add(new State(tempMatrix, c+1, tempHistoryList));
        }
        if(zeroJ > 0){
            //Add state where zero is moved to the left
            //Create the new matrix
            int[][] tempMatrix = new int[3][3];
            for(int i = 0 ;i < 3; i++){
                tempMatrix[i] = stateMatrix[i].clone();
            }
            
            tempMatrix[zeroI][zeroJ] = tempMatrix[zeroI][zeroJ-1];
            tempMatrix[zeroI][zeroJ-1] = 0;

            tempHistoryList = new ArrayList<State>(historyList);
            tempHistoryList.add(this);

            returnList.add(new State(tempMatrix, c+1, tempHistoryList));
        }
        return returnList;
    }
    
    public String toString(){
            String returnString = "\n";

            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    returnString += this.stateMatrix[i][j];
                }
                
                returnString += "\n";
            }
            //returnString += "\n f = " + this.f;
            //returnString += "\n c = " + this.c;
            //returnString += "\n h = " + this.h + "\n";

            return returnString;
    }
}


