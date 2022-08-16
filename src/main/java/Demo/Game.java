package Demo;

import Creatures.Creature;

import java.util.Scanner;

public class Game {
    public Player you;                 //gracz 1
    public Player opponent;            //gracz 2
    public Cards_Stack cards;          //zakryty "stos" kart stworów
    public Discardeds_Stack discarded; //odkryty stos kart odrzuconych stworów
    public Money money;                //zakryty "stos" żetonów waluty
    public Board board;                //plansza
    public Rage_Cards rage_cards;      //zakryty "stos" kart Rage

    public Game(){
        you = new Player(1);
        opponent = new Player(2);

        cards = new Cards_Stack();
        discarded = new Discardeds_Stack();
        money = new Money();
        board = new Board();

        rage_cards = new Rage_Cards();
        rage_cards.initialization();

        startGame(you, opponent);

        while (true) {
            turn(you, opponent);
            turn(opponent, you);
        }
    }

    //początkowe zasoby dla każdego gracza (3 żetony waluty i 3 karty stworów)
    private void startGame(Player you, Player opponent){
        for(int i = 0; i < 3; i++){
            you.eq.addCreature(cards.giveCard());
            opponent.eq.addCreature(cards.giveCard());
            you.money += money.giveMoney(you, opponent);
            opponent.money += money.giveMoney(you, opponent);
        }
    }

    //tura składa się z:
    private void turn(Player you, Player opponent){

        System.out.println("\nTURA GRACZA " + you.id);

        //1. przejścia stworów w stronę bazy przeciwnika
        board.move(you, opponent, discarded, cards,  rage_cards, money);

        System.out.println(this);
        System.out.println(board);
        you.showMoney();

        //2. dobrania kart stworów lub żetonów waluty. Gracz ma dwa dobrania
        draw(you);
        clear();

        //3.Jeżeli gracz ma coś w ekwipunku...
        if(you.eq.size() >= 1) {
            Scanner scan = new Scanner(System.in);
            int number = 1;
            //3. ... i ma kartę Rage "Black Market" może...
            if (you.BlackMarket == 1) {
                number = -1;
                System.out.println("Mozesz sprzedac JEDNEGO stwora i zakonczyc ture lub wystawiac karty:");
                System.out.println("(0) sprzedaj");
                System.out.println("(1) wystaw");
                while (number < 0 || number > 1) {
                    System.out.print("wybierz: ");
                    number = scan.nextInt();
                }
                //... sprzedawać lub...
                if (number == 0)
                    sell(you);
            }
            //... może wystawiać tyle stworów na ile stać gracza, o ile jakieś ma, przestrzegając limitu 4 swoich stworów na planszy
            if (number == 1)
                display(you, opponent);
        }

        //4. Jeśli gracz posiada kartę Rage "Secret Assets" dostaje 1 żeton waluty na koniec tury
        if(you.SecretAssets == 1){
            you.money += money.giveMoney(you, opponent);
            System.out.println("Dobrano zeton waluty");
        }

        //4. Jeśli gracz posiada kartę Rage "RatCatcher" dostaje 1 kartę stwora na koniec tury
        if(you.RatCatcher == 1){
            you.eq.addCreature(cards.giveCard());
            System.out.println("Dobrano stwora");
        }

        //wyczyszczenie ekranu na koniec tury
        clear();
    }

    //pozwala graczowi dobrać 2x żeton waluty  lub  2x kartę stwora  lub  1x to i 1x to
    private void draw(Player p){
        p.select = 2;   //liczba dobrań

        while(p.select > 0){
            Scanner scan =  new Scanner(System.in);
            System.out.println("1 - dobierz karte (lub) 2 - dobierz zeton waluty");
            System.out.print("wybierz: ");
            int number = scan.nextInt();

            //dobranie karty
            if(number == 1){
                Creature one = cards.giveCard();
                //jeżeli gracz posiada kartę Rage "Selection" dobiera dwie karty. Jedną zatrzymuje, drugą odrzuca
                if(p.Selection == 1){
                    Creature two = cards.giveCard();
                    System.out.println("(0) " + one);
                    System.out.println("(1) " + two);
                    number = -1;
                    while (number < 0 || number > 1){
                        System.out.print("wybierz: ");
                        number = scan.nextInt();
                    }
                    if(number == 0)
                        p.eq.addCreature(one);
                    else
                        p.eq.addCreature(two);
                }
                else{
                    System.out.println(one);
                    p.eq.addCreature(one);
                }
                p.select--;
            }

            //dobranie żetonu waluty
            else if(number == 2){
                p.select--;
                int coin_one = money.giveMoney(you, opponent);
                //jeżeli gracz posiada kartę Rage "Second Chance" dobiera dwa żetony waluty. Jedną zatrzymuje, drugą odrzuca
                if(p.SecondChance == 1){
                    int coin_two = money.giveMoney(you, opponent);
                    System.out.println("(0) " + coin_one);
                    System.out.println("(1) " + coin_two);
                    number = -1;
                    while (number < 0 || number > 1){
                        System.out.print("wybierz: ");
                        number = scan.nextInt();
                    }
                    if(number == 0)
                        p.money += coin_one;
                    else
                        p.money += coin_two;
                }
                else {
                    System.out.println(coin_one);
                    p.money += coin_one;
                }
            }
            System.out.println("Pozostale ruchy: " + p.select);
        }
    }

    //metoda odpowiadająca za wystawianie kart i dodatkowe akcje
    private void display(Player you, Player opponent){
        if(you.counter < 4)
        while (you.counter < 4 ){
            System.out.println("Pieniadze: " + you.money);
            System.out.println("1 - wybierz karte \n 2 - podejrzyj plansze \n 3 - spasuj \n 4 - obejrzyj swoje karty Rage \n 5 - legenda mocy stworow");

            Scanner scan = new Scanner(System.in);
            int number = scan.nextInt();
            //wystawienie karty
            if(number == 1){
                System.out.println("\nTwoj ekwpiunek:");
                System.out.println(you.eq);
                System.out.println("(" + you.eq.size() + ") cofnij");
                you.showMoney();

                number = -1;
                while (number < 0 || number > you.eq.size()){
                    System.out.print("wybierz: ");
                    number = scan.nextInt();
                    if(number >= 0 && number < you.eq.size()){
                        if(you.eq.checkCost(number) <= you.money) {
                            you.money -= you.eq.checkCost(number);
                            Creature creature = you.eq.pickCreature(number);
                            //zwiększenie ataku stwora w przypadku posiadania karty Rage "Swarm"
                            if(you.Swarm == 1){
                                if(creature.getAttack() == 2) {
                                    creature.increaseAttack();
                                    creature.setSwarm(1);
                                }
                            }
                            //zwiększenie hp stwora w przypadku posiadania karty Rage "Unbroaken"
                            if(you.Unbroaken == 1){
                                if(creature.getHp() == 2) {
                                    creature.increaseHp();
                                    creature.setUnbroaken(1);
                                }
                            }
                            board.put(creature, you, opponent, discarded);
                            you.counter++;
                            creature.effect(you, opponent, cards, discarded, money, board);
                            System.out.println("\n" + board);
                        }
                        else {
                            System.out.println("Ta jednostka jest za droga!");
                            number = -1;
                        }
                    }
                    else if(number == you.eq.size())
                        break;
                }
            }
            //podejrzenie planszy
            else if(number == 2){
                System.out.println(this);
                System.out.println(board);
                System.out.println("Aktualny GRACZ: " + you.id);
            }
            //pass
            else if(number == 3){
                break;
            }
            //podejrzenie swoich kart Rage
            else if(number == 4){
                System.out.println(you.rage);
            }
            //legenda mocy
            else if(number == 5){
                System.out.println();
                System.out.println("D - przy wystawieniu dobierasz karte stwora");
                System.out.println("E - przy wystawieniu pozwala natychmiast wystawic kolejnego stwora z moca E za darmo");
                System.out.println("F - jesli na przeciwnym polu nie ma wroga, probuje walczyc z przeciwnikiem o 1 pole dalszym");
                System.out.println("G - odsyla pierwszego napotkanego przeciwnika spowrotem do ekwipunku oponenta");
                System.out.println("H - przy wystawieniu uzdrawia jednostke, tzn dobierasz karte stwora ze stosu kart odrzuconych");
                System.out.println("J - gdy ta jednostka ginie, zmienia hp swojego zabojcy na 1");
                System.out.println("M - przy wystawieniu dobierasz zeton waluty");
                System.out.println("N - gdy ta jednostka ma zostac zabita, jednorazowo unika smierci");
                System.out.println("O - pozwala natychmiast wystawic kolejnego stwora za darmo");
                System.out.println("R - gdy sojusznik ma zginac, ta jednostka zginie zamiast tamtej");
                System.out.println("U - sprawia, ze jednostki na dwoch polach za nia sa niezniszczalne, dopoki sama zyje");
                System.out.println("X - zabija pierwszego napotkanego przeciwnika bez wzgledu na statystyki");
                System.out.println("Z - przy wystawieniu pozwala zamienic miejscami ta jednostke z inna sojusznicza");
                System.out.println();
            }
        }
        else
            System.out.println("Osiagnieto limit 4 kart na planszy!");
    }

    //służy do sprzedawania stworów jeżeli gracz posiada kartę "Black Market"
    private void sell(Player p){
        System.out.println("Wybierz jednostke do sprzedania:");
        System.out.println(p.eq);
        Scanner scan = new Scanner(System.in);
        int number = -1;
        while (number < 0 || number >= p.eq.size()){
            System.out.print("Wybierz: ");
            number = scan.nextInt();
            if(number >= 0 && number < p.eq.size()){
                Creature creature = p.eq.pickCreature(number);
                p.money += creature.getCost();
                discarded.putCard(creature);
                break;
            }
        }
    }

    @Override
    public String toString(){
        String stan_gry = "Gracz 1 | tarcze: " + you.showShields() + " | punkty: " + you.score + "\n";
        stan_gry += "Gracz 2 | tarcze: " + opponent.showShields() + " | punkty: " + opponent.score + "\n";
        return stan_gry;
    }

    public void clear(){
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n");
    }
}
