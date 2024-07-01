import javax.swing.JOptionPane;
import java.util.ArrayList;

public class game {
/////////////////////// private variables  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private final String title = "TIC-TAC-TOE.exe";

    private short status;
    private short playerTurn;
    private short choice;
    private char squareMove;

    private String playerMark;
    private String botMark;

    private final static byte size = 3;
    private final static char[][] matrix = new char[size][size];
    ArrayList<String> move;
    private boolean endGame = false;

    //////////////////// public variables \\\\\\\\\\\\\\\\\\\\\\\\\\
    public boolean running = true;

     public void update(){
         if(!getEndgame()){
             this.initBoard();
             this.initVars();
         }

         else
             this.running = false;
    }

    public void render(){
         if(!getEndgame()) {
             this.displayMenu((short) 1);
             switch (this.choice) {
                 case 0:
                     this.singlePlayer();
                     break;
                 case 1:
                     this.multiPLayer();
                     break;
                 case 2:
                     this.displayMenu((short) 5);
                     this.endGame = true;
                     break;
                 default:
                     this.displayMenu((short) 3);
                     break;
             }
         }
    }

    private void initVars(){
         this.playerTurn = 1;
         this.status = -1;

         this.move = new ArrayList<>();

         for(int i = 0; i < size; i++){
             for(int j = 0; j < size; j++){
                 move.add(String.valueOf(matrix[i][j]));
             }
         }
    }

    ///////////////////////////////////

    private void displayMenu(short type){
         String message = "TIC-TAC-TOE\n\n1. SINGLE PLAYER\n2. MULTIPLAYER\n3. EXIT";
         if(type == 1){
             String[] choices = {"1", "2", "3"};
        this.choice = (short) (JOptionPane.showOptionDialog(null, message, this.title,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]));

        if(this.choice == JOptionPane.CLOSED_OPTION)
            this.choice = 2;
         }

        if(type == 2){
            String[] options = {"X", "O"};
         this.choice = (short) JOptionPane.showOptionDialog(null, "SINGLE PLAYER\n\nCHOOSE : (X) or (O)",
                 this.title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

         this.playerMark = (this.choice == 0) ? "X" : "O";
         this.botMark = (this.playerMark.equals("X")) ?"O" :"X";
        }

         if(type == 3)
             JOptionPane.showMessageDialog(null, "INVALID INPUT!", this.title, JOptionPane.WARNING_MESSAGE);

         if(type == 4) {
             int response = JOptionPane.showConfirmDialog(null, "PLAY AGAIN?", this.title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

             if(response == JOptionPane.NO_OPTION)
                 this.endGame = true;
         }

         if(type == 5){
          JOptionPane.showMessageDialog(null, "EXITING PROGRAM", this.title, JOptionPane.INFORMATION_MESSAGE);
          sleepUtil.sleep(1500);
         }
    }

     private void displayStatus(String mode){
         this.printBoard(mode);
     }

    public void singlePlayer(){
        this.displayMenu((short) 2);

        if (!this.playerMark.equals("X")) {
            this.playerTurn = 2;
        }
        do{
            this.updateTurn("single");
            if(this.playerTurn == 1) {

                this.printBoard("single");
                this.checkPlayerMove();
            }

            else {
                botLogic.botMove(size, matrix, this.botMark, this.playerMark, this.move);
                sleepUtil.sleep(1300);
                JOptionPane.showMessageDialog(null, "BOT IS THINKING...", this.title, JOptionPane.INFORMATION_MESSAGE);
                sleepUtil.sleep(2000);
            }

            this.status = checkWin();
            this.playerTurn++;
        }while(this.status == -1);
        this.displayStatus("single");
        this.displayMenu((short) 4);
    }

    public void multiPLayer(){
         do{
             this.updateTurn("multi");

            this.printBoard("multi");

            this.checkPlayerMove();

             this.status = checkWin();
             this.playerTurn++;
         }while(this.status == -1);
         this.displayStatus("multi");
         this.displayMenu((short) 4);
    }

    private void initBoard(){
         char count = '1';

         for(short i = 0; i < size; i++){
            for(short j = 0; j < size; j++){
                matrix[i][j] = count++;
            }
         }
    }

     private String returnBoard(String mode){
         String vsAI = "PLAYER (" + this.playerMark + ")  ---  AI (" + this.botMark + ")\n\n\n";
         String vsPlayer = "PLAYER 1 (X)  ---   PLAYER 2 (O)\n\n\n";
         String playerIndicator = "\n\nPLAYER " + this.playerTurn + " TO MOVE!";
         String board = "|       |       |      |\n"+
                 "|  "+matrix[0][0]+"  |  "+matrix[0][1]+"   |  "+matrix[0][2]+"  |\n"+
                        "|-----|-----|-----|\n"+
                 "|  "+matrix[1][0]+"  |   "+matrix[1][1]+"  |  "+matrix[1][2]+"  |\n"+
                        "|-----|-----|-----|\n"+
                 "|  "+matrix[2][0]+"  |  "+matrix[2][1]+"  |  "+matrix[2][2]+"  |\n"+
                        "|       |      |      |\n";

         if(mode.equals("single")) {
             if (this.status == 0)
                 return (vsAI + board + "\n\nGAME DRAW!");

             else if (this.playerTurn == 2)
                 return (vsAI + board + "\n\nPLAYER WINS!");

             else if(this.playerTurn == 3)
                 return (vsAI + board + "\n\nBOT WINS!");

             else
                 return (vsAI + board);
         }

         else {
             if(this.status == 1)
                 return(vsPlayer + board + "\n\nPLAYER " + --this.playerTurn + " WINS!");

             else if(this.status == 0)
                 return(vsPlayer + board + "\n\nGAME DRAW!");

             else
             return (vsPlayer + board + playerIndicator);
         }
    }

    private void printBoard(String mode){
         if(this.status == -1) {
             String[] move = this.move.toArray(new String[0]);
             int response = JOptionPane.showOptionDialog(null, this.returnBoard(mode), this.title,
                     JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, move, move[0]);
             this.squareMove = move[response].charAt(0);
             this.move.remove(String.valueOf(this.squareMove));
         }
         else
             JOptionPane.showMessageDialog(null, this.returnBoard(mode), this.title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTurn(String mode){
         this.playerTurn = (this.playerTurn % 2 != 0) ? (short)1 : (short)2;

         if(!mode.equals("single"))
             this.playerMark = (this.playerTurn == 1) ?"X" :"O";
    }

    private void checkPlayerMove(){
        short row = (short) ((this.squareMove - '1' ) / size);
        short col = (short) ((this.squareMove - '1' ) % size);
        matrix[row][col] = this.playerMark.charAt(0);
    }

     public static short checkWin(){
         for(short i = 0; i < size; i++){
                    ////////////// check rows \\\\\\\\\\
             if(matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2])
                 return 1;

                   /////////////// check columns \\\\\\\\\\\\
             if(matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i])
                 return 1;
         }

                        ////////// check diagonals \\\\\\\\\\\\\\\\\
         if(matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2] || matrix[2][0] == matrix[1][1] && matrix[1][1] == matrix[0][2])
             return 1;

            /////// check if board is full \\\\\\\\\\\\\\
         for(short i = 0; i < size; i++){
             for(short j = 0; j < size; j++){
                 if(matrix[i][j] != 'X' && matrix[i][j] != 'O')
                     return -1;
             }
         }
         return 0;
    }

     public boolean getEndgame(){
         return this.endGame;
    }

    ///////////////////////////////////
     public void LOG (String input){
         System.out.print(input);
    }
}
