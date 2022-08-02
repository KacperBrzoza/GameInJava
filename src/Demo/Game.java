package Demo;

public class Game {
    Player p1;
    Player p2;
    Cards_Stack cards;
    Discardeds_Stack discarded;
    Money money;
    Board board;
    Rage_Cards rage_cards;



    public Game(){
        p1 = new Player(1);
        p2 = new Player(2);
        cards = new Cards_Stack();
        discarded = new Discardeds_Stack();
        money = new Money();
        board = new Board();

        rage_cards = new Rage_Cards();
        rage_cards.initialization();

        while(true){
            turn(p1);
            turn(p2);
        }
    }

    public void turn(Player p){

        //board.move_and_attack()

        //draw_cards_or_money()

        //board.put_your_creatures_on_the_board()
    }
}
