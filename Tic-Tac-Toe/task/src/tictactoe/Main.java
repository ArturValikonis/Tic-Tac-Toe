package tictactoe;

import java.util.Scanner;

public class Main {
    private static String[][] grid = new String[3][3];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String result;
        int turn = 0;

        fillGrid();
        showGrid();
        do {
            getInput(scanner, turn);
            turn++;
            showGrid();
            result = analyzeGame();
        } while (result.equals("Game not finished"));
        System.out.println(result);
    }

    private static void fillGrid() {
        String input = "_________";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int pos = 3 * i + j;
                grid[i][j] = pos < input.length() ? input.substring(pos, pos + 1).replace('_', ' ') : " ";
            }
        }
    }

    private static void getInput(Scanner sc, int turn) {
        boolean isCorrect = false;
        boolean areInts = true;
        while (!isCorrect) {
            System.out.print("Enter the coordinates: ");
            int x = 0;
            int y = 0;
            try {
                x = sc.nextInt();
                y = sc.nextInt();
            } catch (Exception e) {
                areInts = false;
            }
            isCorrect = areInts && x >= 1 && x <= 3 && y >= 1 && y <= 3 && grid[3 - y][x - 1].equals(" ");
            if (isCorrect) {
                grid[3 - y][x - 1] = turn % 2 == 0 ? "X" : "O";
            } else if (areInts && !(x >= 1 && x <= 3 && y >= 1 && y <= 3)) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (areInts) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                System.out.println("You should enter numbers!");
            }
        }
    }

    private static void showGrid() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static String analyzeGame() {
        String answer = "";
        int empty1 = countChar(' ');
        int empty2 = countChar('_');
        boolean isImpossible = Math.abs(countChar('X') - countChar('O')) > 1 || isAWinner('X') && isAWinner('O');
        if (isImpossible) {
            answer = "Impossible";
        } else if (empty1 + empty2 > 0 && !(isAWinner('X') || isAWinner('O'))) {
            answer = "Game not finished";
        } else {
            answer = isAWinner('X') ? "X wins" : isAWinner('O') ? "O wins" : "Draw";
        }
        return answer;
    }

    private static int countChar(char c) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j].charAt(0) == c) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean checkRows(char c) {
        boolean winner = false;
        for (int i = 0; i < 3; i++) {
            winner = true;
            for (int j = 0; j < 3; j++) {
                winner = winner && grid[i][j].charAt(0) == c;
            }
            if (winner) {
                break;
            }
        }
        return winner;
    }

    private static boolean checkColumns(char c) {
        boolean winner = false;
        for (int j = 0; j < 3; j++) {
            winner = true;
            for (int i = 0; i < 3; i++) {
                winner = winner && grid[i][j].charAt(0) == c;
            }
            if (winner) {
                break;
            }
        }
        return winner;
    }

    private static boolean checkDiagonals(char c) {
        boolean winner1 = true;
        boolean winner2 = true;
        for (int i = 0; i < 3; i++) {
            winner1 = winner1 && grid[i][i].charAt(0) == c;
            winner2 = winner2 && grid[i][2 - i].charAt(0) == c;
        }
        return winner1 || winner2;
    }

    private static boolean isAWinner(char c) {
        return checkRows(c) || checkColumns(c) || checkDiagonals(c);
    }
}