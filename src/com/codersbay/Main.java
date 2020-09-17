package com.codersbay;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int habitatSize = 0;
        Scanner s = new Scanner(System.in);
        System.out.println("----------------------------------------------");
        do {
            System.out.print("Size of the Habitat:");
            habitatSize = s.nextInt();
        } while (habitatSize <= 1);

        boolean[][] habitat = new boolean[habitatSize][habitatSize];

        Random furtuna = new Random();
        for (int i = 0; i < habitatSize; i++) {
            for (int j = 0; j < habitatSize; j++) {
                habitat[j][i] = furtunasChoise(furtuna);
            }
        }

        //Test
        /*final int habitatSize = 10;
        boolean[][] habitat = new boolean[habitatSize][habitatSize];
        for (int i = 0; i < habitatSize; i++) {
            for (int j = 0; j < habitatSize; j++) {
                habitat[j][i] = false;
            }
        }
        habitat[2][2] = true;
        habitat[2][3] = true;
        habitat[2][4] = true;*/

        printHabitat(habitat);
        System.out.println("----------------------------------------------");
        int gens = 0;
        do {
            System.out.print("Number of Life Cycles:");
            gens = s.nextInt();
        } while (gens <= 0);

        for (int i = 0; i < gens; i++) {
            habitat = cycleOfLife(habitat);
            //printHabitat(habitat);
        }
        System.out.println("----------------------------------------------");
        System.out.println("Life after " + gens + " Generations:");
        printHabitat(habitat);
    }

    public static boolean furtunasChoise(Random furtuna) {
        return furtuna.nextBoolean();
    }

    public static void printHabitat(boolean[][] habitat) {
        for (int i = 0; i < habitat.length; i++) {
            for (int j = 0; j < habitat[0].length; j++) {

                if (j == habitat[0].length - 1) {
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
    public static int neighbors(boolean[][] habitat, int posX, int posY) {
        int neighbor = 0;
        int xMax = habitat.length - 1;
        int yMax = habitat[0].length - 1;

        if (habitat[posX][posY] == true) {
            neighbor--;
        }

        //Case 1 - Normal
        // X and Y not at the border
        //X-1 Y+1 | X Y+1 | X+1 Y+1
        //X-1 Y   | X Y   | X+1 Y
        //X-1 Y-1 | X Y-1 | X+1 Y-1
        if (posX != 0
                && posX != habitat.length - 1
                && posY != 0
                && posY != habitat[0].length - 1) {

            for (int i = posX - 1; i <= posX + 1; i++) {
                for (int j = posY - 1; j <= posY + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            return neighbor;
        }

        //Case 2 - top edge
        // Y == yMax and X NOT at Border
        //X-1 Ymin | X Ymin | X+1 Ymin
        //X-1 Y   | X Y   | X+1 Y
        //X-1 Y-1| X Y-1| X+1 Y-1
        if (posY == yMax
                && posX != 0
                && posX != habitat[0].length - 1) {

            for (int i = posX - 1; i <= posX + 1; i++) {
                for (int j = posY - 1; j <= posY; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }

            neighbor += habitat[posX - 1][0] ? 1 : 0;
            neighbor += habitat[posX][0] ? 1 : 0;
            neighbor += habitat[posX + 1][0] ? 1 : 0;

            return neighbor;
        }

        // Case 3 - bottom edge
        // Y == 0 and X NOT at Border
        //X-1 Y+1 | X Y+1 | X+1 Y+1
        //X-1 Y   | X Y   | X+1 Y
        //X-1 Ymax| X Ymax| X+1 Ymax
        if (posY == 0
                && posX != 0
                && posX != habitat[0].length - 1) {

            for (int i = posX - 1; i <= posX + 1; i++) {
                for (int j = posY; j <= posY + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }

            neighbor += habitat[posX - 1][yMax] ? 1 : 0;
            neighbor += habitat[posX][yMax] ? 1 : 0;
            neighbor += habitat[posX + 1][yMax] ? 1 : 0;

            return neighbor;
        }

        // Case 4 - left edge
        // X == 0 and Y NOT at Border
        //Xmax Y+1 | X Y+1 | X+1 Y+1
        //Xmax Y   | X Y   | X+1 Y
        //Xmax Y-1 | X Y-1 | X+1 Y-1
        if (posX == 0
                && posY != 0
                && posY != habitat[0].length - 1) {

            for (int i = posX; i <= posX + 1; i++) {
                for (int j = posY - 1; j <= posY + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }

            neighbor += habitat[xMax][posY + 1] ? 1 : 0;
            neighbor += habitat[xMax][posY] ? 1 : 0;
            neighbor += habitat[xMax][posY - 1] ? 1 : 0;

            return neighbor;
        }

        // Case 5 - rigth edge
        // X == Max and Y NOT at Border
        //X-1 Y+1 | X Y+1 | Xmin Y+1
        //X-1 Y   | X Y   | Xmin Y
        //X-1 Y-1 | X Y-1 | Xmin Y-1
        if (posX == xMax
                && posY != 0
                && posY != habitat[0].length - 1) {

            for (int i = posX - 1; i <= posX; i++) {
                for (int j = posY - 1; j <= posY + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }

            neighbor += habitat[0][posY + 1] ? 1 : 0;
            neighbor += habitat[0][posY] ? 1 : 0;
            neighbor += habitat[0][posY - 1] ? 1 : 0;

            return neighbor;
        }

        // Case 6 - left bottom corner
        // X == 0 and Y == 0
        //Xmax Y+1  | X Y+1 | X-1 Y+1
        //Xmax Y    | X Y   | X+1 Y
        //Xmax Ymax | X Ymax | X+1 Ymax
        if (posX == 0 && posY == 0) {

            for (int i = posX; i <= posX + 1; i++) {
                for (int j = posY; j <= posY + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            neighbor += habitat[xMax][posY + 1] ? 1 : 0;
            neighbor += habitat[xMax][posY] ? 1 : 0;
            neighbor += habitat[xMax][yMax] ? 1 : 0;

            neighbor += habitat[posX][yMax] ? 1 : 0;
            neighbor += habitat[posX + 1][yMax] ? 1 : 0;

            return neighbor;
        }

        // Case 7 - rigth bottom corner
        // X == max and Y == 0
        //X-1 Y+1  | X Y+1  | Xmin Y+1
        //X-1 Y    | X Y    | Xmin Y
        //X-1 Ymax | X Ymax | Xmin Ymax
        if (posX == yMax && posY == 0) {

            for (int i = posX - 1; i <= posX; i++) {
                for (int j = posY; j <= posY + 1; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            neighbor += habitat[0][posY + 1] ? 1 : 0;
            neighbor += habitat[0][posY] ? 1 : 0;
            neighbor += habitat[0][yMax] ? 1 : 0;

            neighbor += habitat[posX][yMax] ? 1 : 0;
            neighbor += habitat[posX - 1][yMax] ? 1 : 0;

            return neighbor;
        }

        // Case 8 - left top edge
        // X == 0 and Y == max
        //Xmax Ymin | X Ymin | X+1 Ymin
        //Xmax Y    | X Y    | X+1 Y
        //Xmax Y-1  | X Y-1  | X+1 Y-1
        if (posX == 0 && posY == yMax) {

            for (int i = posX; i <= posX + 1; i++) {
                for (int j = posY - 1; j <= posY; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            neighbor += habitat[xMax][0] ? 1 : 0;
            neighbor += habitat[xMax][posY] ? 1 : 0;
            neighbor += habitat[xMax][posY - 1] ? 1 : 0;

            neighbor += habitat[posX][0] ? 1 : 0;
            neighbor += habitat[posX + 1][0] ? 1 : 0;

            return neighbor;
        }

        // Case 9 - rigth top corner
        // X == max and Y == max
        //X-1 Ymin | X Ymin | Xmax Ymin
        //X-1 Y    | X Y    | Xmax Y
        //X-1 Y-1  | X Y-1  | Xmax Y-1
        if (posX == xMax && posY == yMax) {

            for (int i = posX - 1; i <= posX; i++) {
                for (int j = posY - 1; j <= posY; j++) {
                    if (habitat[i][j]) {
                        neighbor++;
                    }
                }
            }
            neighbor += habitat[posX - 1][0] ? 1 : 0;
            neighbor += habitat[posX][0] ? 1 : 0;
            neighbor += habitat[xMax][0] ? 1 : 0;
            neighbor += habitat[xMax][posY] ? 1 : 0;
            neighbor += habitat[xMax][posY - 1] ? 1 : 0;

            return neighbor;
        }

        System.out.println("Error: neighbor [ " + posX + " ][ " + posY + "]");
        return -1; //woot?!
    }

    public static boolean[][] cycleOfLife(boolean[][] habitat) {
        boolean[][] newHabitat = new boolean[habitat.length][habitat[0].length];

        for (int i = 0; i < habitat.length; i++) {
            for (int j = 0; j < habitat[0].length; j++) {
                int countNeighbors = neighbors(habitat, i, j);
                if (habitat[i][j] == true) {
                    switch (countNeighbors) {
                        case 2 -> newHabitat[i][j] = true;
                        case 3 -> newHabitat[i][j] = true;
                        default -> newHabitat[i][j] = false;
                    }
                } else {
                    if (countNeighbors == 3) {
                        newHabitat[i][j] = true;
                    }
                }
            }
        }
        return newHabitat;
    }
}