public class Main {
    public static void main(String[] args) {
       game game = new game();

       while(game.running){
           game.update();

           game.render();
       }
    }
}