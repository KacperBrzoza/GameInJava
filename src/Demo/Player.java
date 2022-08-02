package Demo;

public class Player {
    private Equipment eq;
    private int money;
    private int shields;
    private Rage_Cards rage;
    private int id;

    //każdy gracz zaczyna z 0 gotówki i 3 tarczami
    public Player(int id){
        eq = new Equipment();
        money = 0;
        shields = 3;
        rage = new Rage_Cards();
        this.id = id;
    }
}
