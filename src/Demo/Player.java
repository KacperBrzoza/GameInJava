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

    //specjalne cechy, które mogą być aktywowane odpowiednimi kartami Rage
    public short SecretAssets;
    public short Selection;
    public short Thief;
    public short BlackMarket;
    public short Swarm;
    public short SecondChance;
    public short Crusher;
    public short Unbroaken;
    public short Hypnosis;


    public Player(int id){
        eq = new Equipment();
        money = 0;
        shields = 3;
        rage = new Rage_Cards();
        this.id = id;
        score = 0;
        select = 0;
        counter = 0;

        /*
        UWAGA W RAMACH NAPRAWDĘ CIEKAWEGO TESTU POLECAM GORĄCO ZMIENIĆ PONIŻSZE ZERA NA JEDYNKI I ZAGRAĆ :DDD
         */
        SecretAssets = 0;
        Selection = 0;
        Thief = 0;
        BlackMarket = 0;
        Swarm = 0;
        SecondChance = 0;
        Crusher = 0;
        Unbroaken = 0;
        Hypnosis = 0;
    }

    public void loseShield(){
        shields--;
    }

    public int showShields(){
       return shields;
    }

    public void showMoney(){System.out.println("Pieniądze: " + money);}
}
