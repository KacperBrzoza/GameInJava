package Demo;

public class Player {
    public Equipment eq;        //ekwipunek
    public int money;           //suma pieniędzy w posiadaniu gracza
    private int shields;        //suma tarcz
    public Rage_Cards rage;     //posiadane karty Rage
    public int id;              //id dla rozróżnienia graczy (tylko 1 lub 2)
    public float score;         //punkty zdobyte przez gracza
    public int select;          //pole wykorzystywane do fazy dobierania
    public int counter;         //licznik wystawionych postaci


    public Player(int id){
        eq = new Equipment();
        money = 0;
        shields = 3;
        rage = new Rage_Cards();
        this.id = id;
        score = 0;
        select = 0;
        counter = 0;
    }

    public void loseShield(){
        shields--;
    }

    public int showShields(){
       return shields;
    }
}
