package com.codersbay;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        // Enable or disable TEST here!!
        boolean TEST = true;
        // there is life or no life --> boolean: false == nolife  true == life
        boolean[][] habitat;

        if (TEST) {//Test cases
            final int habitatSize = 10;
            habitat = new boolean[habitatSize][habitatSize];
            for (int i = 0; i < habitatSize; i++) {
                for (int j = 0; j < habitatSize; j++) {
                    habitat[j][i] = false;
                }
            }
            // case normal
            set3Cells(habitat,2,3);
            // case edge
            set3Cells(habitat,habitatSize - 1,6);
            // case corner
            set3Cells(habitat, habitatSize - 1, 0);

        } else {
            // user Input --> size of the habitat
            int habitatSize = 0;
            // there is life or no life --> boolean: false == nolife  true == life
            habitat = new boolean[habitatSize][habitatSize];
            System.out.println("----------------------------------------------");
            do {
                System.out.print("Size of the Habitat:");
                habitatSize = s.nextInt();
            } while (habitatSize <= 1);

            Random furtuna = new Random();
            for (int i = 0; i < habitatSize; i++) {
                for (int j = 0; j < habitatSize; j++) {
                    habitat[j][i] = furtuna.nextBoolean();
                }
            }
        }

        printHabitat(habitat);
        System.out.println("----------------------------------------------");
        // get the userinput for the numbers of generations
        int gens = 0;
        do {
            System.out.print("Number of Life Cycles:");
            gens = s.nextInt();
        } while (gens <= 0);

        // run the given numbers of generations
        for (int i = 0; i < gens; i++) {
            habitat = circleOfLife(habitat);
            //printHabitat(habitat);
        }
        System.out.println("----------------------------------------------");
        System.out.println("Life after " + gens + " Generations:");
        printHabitat(habitat);
    }
    // set 3 cells in a row needs the habitat and the position
    public static void set3Cells(boolean[][] habitat, int posX, int posY) {
        for (int i = posX; i < posX + 3; i++) {
            if (i < habitat.length) {
                habitat[posY][i] = true;
            } else {
                habitat[posY][i - habitat.length] = true;
            }
        }
    }

    // Prints the boolean Matrix == Habitat
    public static void printHabitat(boolean[][] habitat) {
        // # == Life == True
        // . == noLife == false
        // | is a parting line
        for (int i = 0; i < habitat.length; i++) {
            for (int j = 0; j < habitat[0].length; j++) {
                if (j == habitat[0].length - 1) {
                    // the edge have no parting line
                    if (habitat[i][j]) {
                        System.out.printf("#");
                    } else {
                        System.out.print(".");
                    }
                } else if (habitat[i][j] == false) {
                    System.out.print(".|");
                } else {
                    System.out.printf("#|");
                }
            }
            System.out.println();
        }
    }

    // how many neighbors have a Cell
    public static int neighbors(boolean[][] habitat, int posY, int posX) {
        int neighbor = 0;
        int Max = habitat.length - 1;

        // neighbors also count the own position. that is not correct so if the position is a life the counter starts with -1
        // i did this so i can use loops.
        if (habitat[posY][posX] == true) {
            neighbor--;
        }

        //Case 1 - Normal
        // X and Y not at the border
        //X-1 Y+1 | X Y+1 | X+1 Y+1
        //X-1 Y   | X Y   | X+1 Y
        //X-1 Y-1 | X Y-1 | X+1 Y-1
        if (       posY != 0
                && posY != habitat.length - 1
                && posX != 0
                && posX != habitat[0].length - 1) {

            for (int i = posY - 1; i <= posY + 1; i++) {
                for (int j = posX - 1; j <= posX + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            return neighbor;
        }

        //Case 2 - top edge
        // Y == Max and X NOT at Border
        //X-1 Ymin | X Ymin | X+1 Ymin
        //X-1 Y    | X Y    | X+1 Y
        //X-1 Y-1  | X Y-1  | X+1 Y-1
        if (       posY == Max
                && posX != 0
                && posX != habitat[0].length - 1) {

            for (int i = posY - 1; i <= posY; i++) {
                for (int j = posX - 1; j <= posX + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            //X-1 Ymin | X Ymin | X+1 Ymin
            neighbor += habitat[0][posX + 1] ? 1 : 0;
            neighbor += habitat[0][posX] ? 1 : 0;
            neighbor += habitat[0][posX - 1] ? 1 : 0;

            return neighbor;
        }

        // Case 3 - bottom edge
        // Y == 0 and X NOT at Border
        //X-1 Y+1 | X Y+1 | X+1 Y+1
        //X-1 Y   | X Y   | X+1 Y
        //X-1 Ymax| X Ymax| X+1 Ymax
        if (       posY == 0
                && posX != 0
                && posX != habitat[0].length - 1) {

            for (int i = posY; i <= posY + 1; i++) {
                for (int j = posX - 1; j <= posX + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            //X-1 Ymax | X Ymax | X+1 Ymax
            neighbor += habitat[Max][posX - 1] ? 1 : 0;
            neighbor += habitat[Max][posX] ? 1 : 0;
            neighbor += habitat[Max][posX + 1] ? 1 : 0;

            return neighbor;
        }

        // Case 4 - left edge
        // X == 0 and Y NOT at Border
        //Xmax Y+1 | X Y+1 | X+1 Y+1
        //Xmax Y   | X Y   | X+1 Y
        //Xmax Y-1 | X Y-1 | X+1 Y-1
        if (       posX == 0
                && posY != 0
                && posY != habitat[0].length - 1) {

            for (int i = posY - 1; i <= posY + 1; i++) {
                for (int j = posX; j <= posX + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            //Xmax Y+1
            //Xmax Y
            //Xmax Y-1
            neighbor += habitat[posY + 1][Max] ? 1 : 0;
            neighbor += habitat[posY][Max] ? 1 : 0;
            neighbor += habitat[posY - 1][Max] ? 1 : 0;

            return neighbor;
        }

        // Case 5 - rigth edge
        // X == Max and Y NOT at Border
        //X-1 Y+1 | X Y+1 | Xmin Y+1
        //X-1 Y   | X Y   | Xmin Y
        //X-1 Y-1 | X Y-1 | Xmin Y-1
        if (       posX == Max
                && posY != 0
                && posY != habitat[0].length - 1) {

            for (int i = posY - 1; i <= posY + 1; i++) {
                for (int j = posX - 1; j <= posX; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            //Xmin Y+1
            //Xmin Y
            //Xmin Y-1
            neighbor += habitat[posY + 1][0] ? 1 : 0;
            neighbor += habitat[posY][0] ? 1 : 0;
            neighbor += habitat[posY - 1][0] ? 1 : 0;

            return neighbor;
        }

        // Case 6 - left bottom corner
        // X == 0 and Y == 0
        //Xmax Y+1  | X Y+1 | X-1 Y+1
        //Xmax Y    | X Y   | X+1 Y
        //Xmax Ymax | X Ymax | X+1 Ymax
        if (posY == 0 && posX == 0) {

            for (int i = posY; i <= posY + 1; i++) {
                for (int j = posX; j <= posX + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            //Xmax Ymax | X Ymax | X+1 Ymax
            neighbor += habitat[Max][Max] ? 1 : 0;
            neighbor += habitat[Max][posX] ? 1 : 0;
            neighbor += habitat[Max][posX + 1] ? 1 : 0;

            //Xmax Y+1
            //Xmax Y
            neighbor += habitat[posY + 1][Max] ? 1 : 0;
            neighbor += habitat[posY][Max] ? 1 : 0;

            return neighbor;
        }

        // Case 7 - rigth bottom corner
        // X == max and Y == 0
        //X-1 Y+1  | X Y+1  | Xmin Y+1
        //X-1 Y    | X Y    | Xmin Y
        //X-1 Ymax | X Ymax | Xmin Ymax
        if (posY == 0 && posX == Max) {

            for (int i = posY; i <= posY + 1; i++) {
                for (int j = posX - 1; j <= posX; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            //X-1 Ymax | X Ymax | Xmin Ymax
            neighbor += habitat[Max][posX - 1] ? 1 : 0;
            neighbor += habitat[Max][posX] ? 1 : 0;
            neighbor += habitat[Max][0] ? 1 : 0;

            //Xmin Y+1
            //Xmin Y
            neighbor += habitat[posY + 1][0] ? 1 : 0;
            neighbor += habitat[posY][0] ? 1 : 0;

            return neighbor;
        }

        // Case 8 - left top edge
        // X == 0 and Y == max
        //Xmax Ymin | X Ymin | X+1 Ymin
        //Xmax Y    | X Y    | X+1 Y
        //Xmax Y-1  | X Y-1  | X+1 Y-1
        if (posY == Max && posX == 0) {

            for (int i = posY - 1; i <= posY; i++) {
                for (int j = posX; j <= posX + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            //Xmax Ymin | X Ymin | X+1 Ymin
            neighbor += habitat[0][Max] ? 1 : 0;
            neighbor += habitat[0][posX] ? 1 : 0;
            neighbor += habitat[0][posX + 1] ? 1 : 0;

            //Xmax Y
            //Xmax Y-1
            neighbor += habitat[posY][Max] ? 1 : 0;
            neighbor += habitat[posY - 1][Max] ? 1 : 0;

            return neighbor;
        }

        // Case 9 - rigth top corner
        // X == max and Y == max
        //X-1 Ymin | X Ymin | Xmax Ymin
        //X-1 Y    | X Y    | Xmax Y
        //X-1 Y-1  | X Y-1  | Xmax Y-1
        if (posY == Max && posX == Max) {

            for (int i = posY - 1; i <= posY; i++) {
                for (int j = posX - 1; j <= posX; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            //X-1 Ymin | X Ymin
            neighbor += habitat[0][posX - 1] ? 1 : 0;
            neighbor += habitat[0][posX] ? 1 : 0;

            //Xmax Ymin
            //Xmax Y
            //Xmax Y-1
            neighbor += habitat[0][Max] ? 1 : 0;
            neighbor += habitat[posY][Max] ? 1 : 0;
            neighbor += habitat[posY - 1][Max] ? 1 : 0;

            return neighbor;
        }

        System.out.println("Error: neighbor [ " + posY + " ][ " + posX + "]");
        return -1; // in case of bullshit
    }

    public static boolean[][] circleOfLife(boolean[][] habitat) {
        // the newHabitat is the result of a Life Cycle
        // default of boolean is false --> newHabitat is a wastland everything is dead
        boolean[][] newHabitat = new boolean[habitat.length][habitat[0].length];

        // go throw the Habitat and apply the rules.
        // the result of the rules get noted in the newHabitat
        for (int i = 0; i < habitat.length; i++) {
            for (int j = 0; j < habitat[0].length; j++) {
                int countNeighbors = neighbors(habitat, i, j);
                if (habitat[i][j] == true) {
                    switch (countNeighbors) {
                        //RULE: is a cell alive and has 2 or 3 neighbors, will it stay alive 🤝?
                        case 2, 3 -> newHabitat[i][j] = true;
                        //RULE: if a cell is alive and has fewer than 2 neighbors it dies of loneliness 😔
                        //Rule: if a cell is alive and has more than 3 neighbors, it will die of overpopulation 💀
                        default -> newHabitat[i][j] = false;
                    }
                } else {
                    //RULE: if a cell is dead and has exactly 3 living neighbors, it will be born in the next generation 👶
                    if (countNeighbors == 3) {
                        newHabitat[i][j] = true;
                    }
                }
            }
        }
        // return newHabitat as result of the application of the rules
        return newHabitat;
    }
}