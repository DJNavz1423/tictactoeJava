import java.util.ArrayList;
import java.util.Random;

public class botLogic {

    public static void botMove(byte size, char[][] matrix, String botMark, String playerMark, ArrayList<String> move){

        if(checkBotWin(size, matrix, botMark, move)) return;

        if(checkPlayerWin(size, matrix, botMark, playerMark, move)) return;

        if(checkCenter(matrix, move, botMark)) return;

        if(checkCorners(size, matrix, botMark, move)) return;

        randomMove(size, matrix, botMark, move);
    }

    private static boolean checkBotWin(byte size, char[][] matrix, String botMark, ArrayList<String> move){

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(matrix[i][j] != 'X' && matrix[i][j] != 'O'){
                    char originalElem = matrix[i][j];
                    matrix[i][j] = botMark.charAt(0);
                    if(game.checkWin() == 1){
                        move.remove(String.valueOf(originalElem));
                        return true;
                    }
                    matrix[i][j] = originalElem;
                }
            }
        }
        return false;
    }

    private static boolean checkPlayerWin(byte size, char[][] matrix, String botMark, String playerMark, ArrayList<String> move){

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(matrix[i][j] != 'X' && matrix[i][j] != 'O'){
                    char originalElem = matrix[i][j];
                    matrix[i][j] = playerMark.charAt(0);
                    if(game.checkWin() == 1){
                        move.remove(String.valueOf(originalElem));
                        matrix[i][j] = botMark.charAt(0);
                        return true;
                    }
                    matrix[i][j] = originalElem;
                }
            }
        }
        return false;
    }

    private static boolean checkCenter(char[][] matrix, ArrayList<String> move, String botMark){
        if(matrix[1][1] != 'X' && matrix[1][1] != 'O'){
            move.remove(String.valueOf(matrix[1][1]));
            matrix[1][1] = botMark.charAt(0);
            return true;
        }
        return false;
    }

    private static boolean checkCorners(byte size, char[][] matrix, String botMark, ArrayList<String> move){
        for(int i = 0; i < size; i = i+2){
            for(int j = 0; j < size; j = j+2){
                if(matrix[i][j] != 'X' && matrix[i][j] != 'O'){
                    move.remove(String.valueOf(matrix[i][j]));
                    matrix[i][j] = botMark.charAt(0);
                    return true;
                }
            }
        }
        return false;
    }

    private static void randomMove(byte size, char[][] matrix, String botMark, ArrayList<String> move){
        int botMove;
        boolean validMove = false;
        Random rand = new Random(System.currentTimeMillis());

        while(!validMove){
            botMove = rand.nextInt(9) + 1;

            int row = (botMove - 1) / size;
            int col = (botMove - 1) % size;

            if(matrix[row][col] != 'X' && matrix[row][col] != 'O'){
                move.remove(String.valueOf(matrix[row][col]));
                matrix[row][col] = botMark.charAt(0);
                validMove = true;
            }
        }
    }

}
