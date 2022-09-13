package com.example.Main.Game;

import com.example.Main.Login.Memory;
import com.example.Main.Menu.MenuController;
import com.example.Main.Service.UserService;
import com.example.NetTools.Client;
import com.example.NetTools.Server;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.Main.Menu.WaitingEnemyController.SWITCHER;

public class GameController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Pane AllScreen;
    @FXML
    public Button EndTurnButton;
    @FXML
    public Label MoneyPlayerValue;
    @FXML
    public HBox ChoiceHBox;
    @FXML
    ImageView MyTower,EnemyTower;
    @FXML
    public Label InfoLabel, CardCounter, EQLabel;
    @FXML
    public Pane InventoryPane;
    @FXML
    public ImageView BattleGrid,PlayerPicture,MyCharacter,EnemyCharacter;
    @FXML
    public Label EndGameLabel, PointsLabel;
    @FXML
    public Button BackToMenuButton;

    @FXML
    public ImageView mygrid0,mygrid1,mygrid2,mygrid3,mygrid4;
    @FXML
    public ImageView enemygrid4,enemygrid3,enemygrid2,enemygrid1,enemygrid0;
    @FXML
    public ImageView rage1,rage2,rage3;
    @FXML
    public ImageView rage1_enemy,rage2_enemy,rage3_enemy;
    @FXML
    public Button ExitButton;

    @FXML
    public ImageView EQ1,EQ2,EQ3,EQ4;

    @FXML
    public Pane SelectField1, SelectField2, SelectField3, SelectField4;

    @FXML
    public ImageView lostcardgrid;

    @FXML
    public Button TakeCardDeck, RageCardDeck, MoneyStack, LostCardDeck;

    @FXML
    public ImageView TakeCardDeckSelect, RageCardDeckSelect, MoneyStackSelect, LostCardDeckSelect;

    @FXML
    public Button LeftShowBut, RightShowBut;

    @FXML
    public ImageView choiceOne, choiceTwo;



    private String my_user;
    private String opponent;
    String path = "src/main/resources/music/the_witcher.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer_battle_music = new MediaPlayer(media);

    String path_sound_click = "src/main/resources/sound/button_release_sound.mp3";
    Media media_click = new Media(new File(path_sound_click).toURI().toString());
    MediaPlayer mediaPlayer_click = new MediaPlayer(media_click);

    String path_sound_page = "src/main/resources/sound/page_button_click.mp3";
    Media media_page = new Media(new File(path_sound_page).toURI().toString());
    MediaPlayer mediaPlayer_page = new MediaPlayer(media_page);

    String path_sound_hover = "src/main/resources/sound/hover_entered_sound.mp3";
    Media media_hover = new Media(new File(path_sound_hover).toURI().toString());
    MediaPlayer mediaPlayer_hover = new MediaPlayer(media_hover);

    String path_sound_move = "src/main/resources/sound/button_click_sound.mp3";
    Media media_move = new Media(new File(path_sound_move).toURI().toString());
    MediaPlayer mediaPlayer_move = new MediaPlayer(media_move);

    String path_sound_disable = "src/main/resources/sound/block_input_sound.mp3";
    Media media_disable = new Media(new File(path_sound_disable).toURI().toString());
    MediaPlayer mediaPlayer_disable = new MediaPlayer(media_disable);

    static String path_sound_card_put = "src/main/resources/sound/card_release_sound.mp3";
    static Media media_card_put = new Media(new File(path_sound_card_put).toURI().toString());
    static MediaPlayer mediaPlayer_card_put = new MediaPlayer(media_card_put);

    String path_sound_coinpick = "src/main/resources/sound/coindeck_pick_sound.mp3";
    Media media_coinpick = new Media(new File(path_sound_coinpick).toURI().toString());
    MediaPlayer mediaPlayer_coinpick = new MediaPlayer(media_coinpick);

    String path_sound_enemy_heart_broken = "src/main/resources/sound/enemy_heart_broken_sound.mp3";
    Media media_enemy_heart_broken = new Media(new File(path_sound_enemy_heart_broken).toURI().toString());
    MediaPlayer mediaPlayer_enemy_heart_broken = new MediaPlayer(media_enemy_heart_broken);

    String path_sound_enemy_kill_creature = "src/main/resources/sound/enemy_kill_creature_sound.mp3";
    Media media_enemy_kill_creature = new Media(new File(path_sound_enemy_kill_creature).toURI().toString());
    MediaPlayer mediaPlayer_enemy_kill_creature = new MediaPlayer(media_enemy_kill_creature);

    String path_sound_my_character_kill_creature = "src/main/resources/sound/my_character_kill_creature_sound.mp3";
    Media media_my_character_kill_creature = new Media(new File(path_sound_my_character_kill_creature).toURI().toString());
    MediaPlayer mediaPlayer_my_character_kill_creature = new MediaPlayer(media_my_character_kill_creature);

    String path_sound_my_character_heart_broken = "src/main/resources/sound/my_character_heart_broken_sound.mp3";
    Media media_my_character_heart_broken = new Media(new File(path_sound_my_character_heart_broken).toURI().toString());
    MediaPlayer mediaPlayer_my_character_heart_broken = new MediaPlayer(media_my_character_heart_broken);


    public static Server server;
    /*
    PHASE VALUES:
    0 - domyślna
    1 - faza przesuwania sie postaci
    2 - faza dobierania
    3 - faza wystawiania
    4 - faza konca tury
     */
    public static int phase = 0;
    public static int choice;
    public static boolean selectingPhase;
    public static Client client;

    public static String opponentNick;

    public static Boolean myTurn = true;

    public ArrayList<Image> eqImages;
    public ArrayList<Field> fields;

    public ArrayList<Image> discardedsImages;

    public int eq_it = 0;

    public static int my_hp, opp_hp;



    //awaryjne pola do przechwycenia wyniku w razie rozłączenia się, któregoś z graczy
    public static double PLAYER_ONE_POINTS;
    public static double PLAYER_TWO_POINTS;

    @FXML
    void FadeOut() throws IOException {
        FXMLLoader loader_menu = new FXMLLoader(getClass().getResource("/com/example/Main/Menu/Menu-view.fxml"));
        Parent root = loader_menu.load();
        MenuController controller_menu = loader_menu.getController();
        controller_menu.Music_menu_on_off(false);
        MenuController.MenuMusicAllow=true;
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2),AllScreen);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PLAYER_ONE_POINTS = 0;
        PLAYER_TWO_POINTS = 0;

        my_hp = 3;
        opp_hp = 3;

        selectingPhase = false;
        eqImages = new ArrayList<>();
        fields = new ArrayList<>();
        discardedsImages = new ArrayList<>();
        for(int i = 0; i < 10; i++)
            fields.add(new Field(i));
        AllScreen.setOpacity(0);
        //LeftShowBut.setDisable(true);
        
        TakeCardDeckSelect.setVisible(false);
        RageCardDeckSelect.setVisible(false);
        MoneyStackSelect.setVisible(false);
        LostCardDeckSelect.setVisible(false);

        try {
            FadeOut();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer_battle_music.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer_battle_music.setVolume(0.1);
        mediaPlayer_battle_music.play();
        if(SWITCHER == 1){

            server.startGame(this);
            changeTurn(EndTurnButton, TakeCardDeck, RageCardDeck, MoneyStack, LostCardDeck, CardCounter);
            server.turns(this);
        }
        else {
            String buf = Memory.memory.getUsername();
            Memory.memory.setUsername(opponentNick);
            opponentNick = buf;
            ChangeTextureForClient();
            changeTurn(EndTurnButton, TakeCardDeck, RageCardDeck, MoneyStack, LostCardDeck, CardCounter);
            client.turns(this);
        }

        EndTurnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                choice = -2;
                if(SWITCHER == 1){
                    server.sendMessageToClient("YOUR_TURN");
                    changeTurn(EndTurnButton, TakeCardDeck, RageCardDeck, MoneyStack, LostCardDeck, CardCounter);
                }
                else{
                    client.sendMessageToServer("10000");
                    changeTurn(EndTurnButton, TakeCardDeck, RageCardDeck, MoneyStack, LostCardDeck, CardCounter);
                }
            }
        });

        LeftShowBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(eq_it - 1 >= 0){
                    eq_it--;
                    showEQ(eq_it, eqImages, EQ1, EQ2, EQ3, EQ4);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (eq_it - 1 < 0)
                                LeftShowBut.setDisable(true);
                            if(eq_it + 4 < eqImages.size())
                                RightShowBut.setDisable(false);
                        }
                    });
                }
            }
        });

        RightShowBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(eq_it + 4 < eqImages.size()){
                    eq_it++;
                    showEQ(eq_it, eqImages, EQ1, EQ2, EQ3, EQ4);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (eq_it + 4 >=  eqImages.size())
                                RightShowBut.setDisable(true);
                            LeftShowBut.setDisable(false);
                        }
                    });
                }
            }
        });

        TakeCardDeck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(phase == 2){
                    choice = 1;
                    click_sound();
                    if(SWITCHER == 2) {
                        client.sendMessageToServer("" + choice);
                    }
                }
            }
        });

        MoneyStack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(phase == 2){
                    money_pick_sound();
                    choice = 2;
                    if(SWITCHER == 2) {
                        client.sendMessageToServer("" + choice);
                    }
                }
            }
        });
    }

    public static void changeTurn(Button EndTurnButton, Button TakeCardDeck, Button RageCardDeck, Button MoneyStack, Button LostCardDeck, Label CardCounter){
        if(myTurn){
            myTurn = false;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    EndTurnButton.setText("Tura Przeciwnika");
                    EndTurnButton.setStyle("-fx-font-size: 22pt;");
                    EndTurnButton.setDisable(true);
                    TakeCardDeck.setDisable(true);
                    RageCardDeck.setDisable(true);
                    MoneyStack.setDisable(true);
                    LostCardDeck.setDisable(true);
                    CardCounter.setDisable(true);
                }
            });
        }
        else{
            myTurn = true;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    EndTurnButton.setText("Zakoncz ture");
                    EndTurnButton.setStyle("-fx-font-size: 28pt;");
                    EndTurnButton.setDisable(false);
                    TakeCardDeck.setDisable(false);
                    RageCardDeck.setDisable(false);
                    MoneyStack.setDisable(false);
                    LostCardDeck.setDisable(false);
                    CardCounter.setDisable(false);
                }
            });
        }
    }

    public static void newLabelValue(Label label, String val){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(val);
            }
        });
    }

    public static void addImageToEQ(ArrayList<Image> eqImages, String val){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                File file = new File(val);
                Image image = new Image(file.toURI().toString());
                eqImages.add(image);
            }
        });
    }

    public static void removeImageFromEQ(ArrayList<Image> eqImages, int position, int eq_it, Button RightShowBut){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //if(eq_it+4 >= eqImages.size())
                    //RightShowBut.setDisable(true);
                eqImages.remove(position);
                mediaPlayer_card_put.setVolume(1);
                mediaPlayer_card_put.stop();
                mediaPlayer_card_put.seek(Duration.seconds(0));
                mediaPlayer_card_put.play();
            }
        });
    }

    public static void showEQ(int eq_it, ArrayList<Image> eqImages, ImageView EQ1, ImageView EQ2, ImageView EQ3, ImageView EQ4){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int i = eq_it;
                if(i >= eqImages.size()){
                    EQ1.setImage(null);
                }
                else{
                    EQ1.setImage(eqImages.get(i));
                }

                i++;
                if(i >= eqImages.size()){
                    EQ2.setImage(null);
                }
                else{
                    EQ2.setImage(eqImages.get(i));
                }

                i++;
                if(i >= eqImages.size()){
                    EQ3.setImage(null);
                }
                else{
                    EQ3.setImage(eqImages.get(i));
                }

                i++;
                if(i >= eqImages.size()){
                    EQ4.setImage(null);
                }
                else{
                    EQ4.setImage(eqImages.get(i));
                }
            }
        });
    }

    //"src/main/resources/img/Creatures/432F.png"
    public static void loseHp(ImageView character, int player_id, GameController gameController){
        String path = "src/main/resources/img/Game_imgs/players/";
        if(player_id == 1){
            if(SWITCHER == 1){
                gameController.my_character_heart_sound();
                my_hp--;
                if(my_hp == 2)
                    path += "two_hp_";
                else if(my_hp == 1)
                    path += "one_hp_";
                else if(my_hp == 0)
                    path += "none_hp_";
                else
                    path += "dead_";
                path += "blue.gif";
                server.sendMessageToClient("LOSE_HP_" + path);
            }
            else{
                gameController.enemy_heart_sound();
                opp_hp--;
                if(opp_hp == 2)
                    path += "two_hp_";
                else if(opp_hp == 1)
                    path += "one_hp_";
                else if(opp_hp == 0)
                    path += "none_hp_";
                else
                    path += "dead_";
                path += "blue.gif";
            }
        }
        else {
            if(SWITCHER == 1){
                gameController.enemy_heart_sound();
                opp_hp--;
                if(opp_hp == 2)
                    path += "two_hp_";
                else if(opp_hp == 1)
                    path += "one_hp_";
                else if(opp_hp == 0)
                    path += "none_hp_";
                else
                    path += "dead_";
                path += "red.gif";
                server.sendMessageToClient("LOSE_HP_" + path);
            }
            else{
                gameController.my_character_heart_sound();
                my_hp--;
                if(my_hp == 2)
                    path += "two_hp_";
                else if(my_hp == 1)
                    path += "one_hp_";
                else if(my_hp == 0)
                    path += "none_hp_";
                else
                    path += "dead_";
                path += "red.gif";
            }
        }
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                character.setImage(image);
            }
        });
    }

    public static void selectingPhase(ImageView TakeCardDeckSelect, ImageView MoneyStackSelect,Button EndTurnButton){
        if(selectingPhase){
            selectingPhase = false;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    TakeCardDeckSelect.setVisible(selectingPhase);
                    MoneyStackSelect.setVisible(selectingPhase);
                    EndTurnButton.setDisable(selectingPhase);
                }
            });
        }
        else {

            selectingPhase = true;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    TakeCardDeckSelect.setVisible(selectingPhase);
                    MoneyStackSelect.setVisible(selectingPhase);
                    EndTurnButton.setDisable(selectingPhase);
                }
            });
        }
    }

    public static void setChoiceHBox(HBox ChoiceHBox, Pane InventoryPane, Button EndTurnButton, ImageView choiceOne, ImageView choiceTwo, Image one, Image two)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(ChoiceHBox.isVisible())
                {
                    ChoiceHBox.setVisible(false);
                    ChoiceHBox.setDisable(true);
                    InventoryPane.setDisable(false);
                    EndTurnButton.setDisable(false);
                }
                else
                {
                    choiceOne.setImage(one);
                    choiceTwo.setImage(two);
                    ChoiceHBox.setVisible(true);
                    ChoiceHBox.setDisable(false);
                    InventoryPane.setDisable(true);
                    EndTurnButton.setDisable(true);
                }
            }
        });
    }

    public static void showBattleField(ArrayList<Field> fields, ImageView mygrid0, ImageView mygrid1, ImageView mygrid2, ImageView mygrid3, ImageView mygrid4, ImageView enemygrid0, ImageView enemygrid1, ImageView enemygrid2, ImageView enemygrid3, ImageView enemygrid4){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mygrid0.setImage(fields.get(0).getImage());
                mygrid1.setImage(fields.get(1).getImage());
                mygrid2.setImage(fields.get(2).getImage());
                mygrid3.setImage(fields.get(3).getImage());
                mygrid4.setImage(fields.get(4).getImage());

                enemygrid0.setImage(fields.get(5).getImage());
                enemygrid1.setImage(fields.get(6).getImage());
                enemygrid2.setImage(fields.get(7).getImage());
                enemygrid3.setImage(fields.get(8).getImage());
                enemygrid4.setImage(fields.get(9).getImage());
            }
        });
    }

    public static void setSingleField(ArrayList<Field> fields, Image image, int position){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fields.get(position).setImage(image);
            }
        });
    }

    public static void discardCard(ArrayList<Image> discardedsImages, Image image, ImageView lostcardgrid){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lostcardgrid.setImage(image);
                discardedsImages.add(image);
            }
        });
    }

    public static void takeDiscardedCard(ArrayList<Image> discardedsImages, ImageView lostcardgrid){
        if(discardedsImages.size() > 0){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lostcardgrid.setImage(discardedsImages.get(discardedsImages.size()-1));
                    discardedsImages.remove(discardedsImages.size()-1);
                }
            });
        }
    }

    public static void connectionClose(HBox ChoiceHBox, Label EndGameLabel, Label PointsLabel, Button ExitButton){

        UserService userService = new UserService();
        if(SWITCHER == 2){
            PLAYER_ONE_POINTS -= 0.5;
            PLAYER_TWO_POINTS += 2.0;
            userService.setScoreOne(Memory.memory.getUsername(), PLAYER_TWO_POINTS);
            userService.setScoreOne(opponentNick, PLAYER_ONE_POINTS);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChoiceHBox.setDisable(false);
                    ChoiceHBox.setVisible(true);
                    EndGameLabel.setStyle("-fx-font-size: 30pt;");
                    EndGameLabel.setText("Przeciwnik rozlaczyl sie");
                    PointsLabel.setStyle("-fx-font-size: 25pt;");
                    PointsLabel.setText("Zdobyte punkty\n" + Memory.memory.getUsername() + ": " + PLAYER_TWO_POINTS+
                            "\n" + opponentNick + ": " + PLAYER_ONE_POINTS);
                    ExitButton.setDisable(true);
                }
            });
        }
        else {
            PLAYER_ONE_POINTS += 2.0;
            PLAYER_TWO_POINTS -= 0.5;
            userService.setScoreOne(Memory.memory.getUsername(), PLAYER_ONE_POINTS);
            userService.setScoreOne(opponentNick, PLAYER_TWO_POINTS);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChoiceHBox.setDisable(false);
                    ChoiceHBox.setVisible(true);
                    EndGameLabel.setStyle("-fx-font-size: 30pt;");
                    EndGameLabel.setText("Przeciwnik rozlaczyl sie");
                    PointsLabel.setStyle("-fx-font-size: 25pt;");
                    PointsLabel.setText("Zdobyte punkty\n" + Memory.memory.getUsername() + ": " + PLAYER_ONE_POINTS+
                            "\n" + opponentNick + ": " + PLAYER_TWO_POINTS);
                    ExitButton.setDisable(true);
                }
            });
        }
    }

    public static void endGame(HBox ChoiceHBox, Label EndGameLabel, Label PointsLabel, Button ExitButton){

        if(SWITCHER == 2){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChoiceHBox.setDisable(false);
                    ChoiceHBox.setVisible(true);
                    EndGameLabel.setStyle("-fx-font-size: 30pt;");
                    EndGameLabel.setText("Gra skonczona!");
                    PointsLabel.setStyle("-fx-font-size: 25pt;");
                    PointsLabel.setText("Zdobyte punkty\n" + Memory.memory.getUsername() + ": " + PLAYER_TWO_POINTS+
                            "\n" + opponentNick + ": " + PLAYER_ONE_POINTS);
                    ExitButton.setDisable(true);
                }
            });
        }
        else {
            server.sendMessageToClient("END_GAME");
            UserService userService = new UserService();
            userService.setScoreOne(Memory.memory.getUsername(), PLAYER_ONE_POINTS);
            userService.setScoreOne(opponentNick, PLAYER_TWO_POINTS);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ChoiceHBox.setDisable(false);
                    ChoiceHBox.setVisible(true);
                    EndGameLabel.setStyle("-fx-font-size: 30pt;");
                    EndGameLabel.setText("Gra skonczona!");
                    PointsLabel.setStyle("-fx-font-size: 25pt;");
                    PointsLabel.setText("Zdobyte punkty\n" + Memory.memory.getUsername() + ": " + PLAYER_ONE_POINTS+
                            "\n" + opponentNick + ": " + PLAYER_TWO_POINTS);
                    ExitButton.setDisable(true);
                }
            });
        }
    }

    public static void setBattleField(ArrayList<Field> fields, Image image0, Image image1, Image image2, Image image3, Image image4, Image image5, Image image6, Image image7, Image image8, Image image9){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fields.get(0).setImage(image0);
                fields.get(1).setImage(image1);
                fields.get(2).setImage(image2);
                fields.get(3).setImage(image3);
                fields.get(4).setImage(image4);
                fields.get(5).setImage(image5);
                fields.get(6).setImage(image6);
                fields.get(7).setImage(image7);
                fields.get(8).setImage(image8);
                fields.get(9).setImage(image9);
            }
        });
    }

    @FXML
    protected void BackToMenu(ActionEvent event) throws IOException
    {
        stopMusic();
        URL url = new File("src/main/resources/com/example/Main/Menu/Menu-view.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/style-class.css").toExternalForm());
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void ChangeTextureForClient()
    {
        /*
        File file = new File("src/main/resources/img/Game_imgs/players/full_hp_red.gif");
        Image myheroimg = new Image(file.toURI().toString());
        MyCharacter.setImage(myheroimg);
        MyCharacter.setRotate(180);
        File file2 = new File("src/main/resources/img/Game_imgs/players/full_hp_blue.gif");
        Image enemyheroimg = new Image(file2.toURI().toString());
        EnemyCharacter.setImage(enemyheroimg);
        EnemyCharacter.setRotate(180);
        File file3 = new File("src/main/resources/img/Game_imgs/battle_grid/plate_battle_grid_red.png");
        Image gridplateimg = new Image(file3.toURI().toString());
        BattleGrid.setImage(gridplateimg);
        File file4 = new File("src/main/resources/img/Game_imgs/player_towers/plate_player_left_red.png");
        Image mytowerimg = new Image(file4.toURI().toString());
        MyTower.setImage(mytowerimg);
        File file5 = new File("src/main/resources/img/Game_imgs/player_towers/plate_player_right_blue.png");
        Image enemytowerimg = new Image(file5.toURI().toString());
        EnemyTower.setImage(enemytowerimg);
         */
        File file6 = new File("src/main/resources/img/Game_imgs/players/my_profile_king2.gif");
        Image statuscharacterimg = new Image(file6.toURI().toString());
        PlayerPicture.setImage(statuscharacterimg);

    }
    public void card_put_sound()
    {
        mediaPlayer_card_put.setVolume(0.5);
        mediaPlayer_card_put.stop();
        mediaPlayer_card_put.seek(Duration.seconds(0));
        mediaPlayer_card_put.play();
    }
    public void money_pick_sound()
    {
        mediaPlayer_coinpick.setVolume(0.5);
        mediaPlayer_coinpick.stop();
        mediaPlayer_coinpick.seek(Duration.seconds(0));
        mediaPlayer_coinpick.play();
    }
    public void enemy_heart_sound()
    {
        mediaPlayer_enemy_heart_broken.setVolume(0.5);
        mediaPlayer_enemy_heart_broken.stop();
        mediaPlayer_enemy_heart_broken.seek(Duration.seconds(0));
        mediaPlayer_enemy_heart_broken.play();
    }
    public void my_character_heart_sound()
    {
        mediaPlayer_my_character_heart_broken.setVolume(0.5);
        mediaPlayer_my_character_heart_broken.stop();
        mediaPlayer_my_character_heart_broken.seek(Duration.seconds(0));
        mediaPlayer_my_character_heart_broken.play();
    }
    public void enemy_kill_creature_sound()
    {
        mediaPlayer_enemy_kill_creature.setVolume(0.5);
        mediaPlayer_enemy_kill_creature.stop();
        mediaPlayer_enemy_kill_creature.seek(Duration.seconds(0));
        mediaPlayer_enemy_kill_creature.play();
    }
    public void my_character_kill_creature_sound()
    {
        mediaPlayer_my_character_kill_creature.setVolume(0.5);
        mediaPlayer_my_character_kill_creature.stop();
        mediaPlayer_my_character_kill_creature.seek(Duration.seconds(0));
        mediaPlayer_my_character_kill_creature.play();
    }
    public void disable_button_sound()
    {
        mediaPlayer_disable.setVolume(0.5);
        mediaPlayer_disable.stop();
        mediaPlayer_disable.seek(Duration.seconds(0));
        mediaPlayer_disable.play();
    }
    public void page_sound()
    {
        mediaPlayer_page.setVolume(0.2);
        mediaPlayer_page.stop();
        mediaPlayer_page.seek(Duration.seconds(0));
        mediaPlayer_page.play();
    }
    public void hover_sound()
    {
        mediaPlayer_hover.setVolume(0.5);
        mediaPlayer_hover.stop();
        mediaPlayer_hover.seek(Duration.seconds(0));
        mediaPlayer_hover.play();
    }
    public void click_sound()
    {
        mediaPlayer_click.setVolume(0.5);
        mediaPlayer_click.stop();
        mediaPlayer_click.seek(Duration.seconds(0));
        mediaPlayer_click.play();
    }
    @FXML
    protected void onEnteredButton()
    {
        mediaPlayer_move.setVolume(0.5);
        mediaPlayer_move.stop();
        mediaPlayer_move.seek(Duration.seconds(0));
        mediaPlayer_move.play();
    }

    public void stopMusic()
    {
        mediaPlayer_battle_music.stop();
    }

    @FXML
    protected void onExitButtonClicked(ActionEvent event) throws IOException
    {
        click_sound();
        UserService userService = new UserService();
        userService.set_Usage_false(Memory.memory.getUsername());
        if(SWITCHER == 1){
            server.closeConnection();
        }
        else {
            client.closeConnection();
        }
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
        System.exit(1);
    }

    @FXML
    protected void hiderInformation()
    {
        rage1.setX(0);
        rage2.setX(0);
        rage3.setX(0);
        rage1_enemy.setX(0);
        rage2_enemy.setX(0);
        rage3_enemy.setX(0);
        InfoLabel.setText("");
        InfoLabel.setStyle("-fx-font-size: 14pt;");
        if(phase == 2){
            InfoLabel.setText("Dobierz karty stworow lub monety");
            InfoLabel.setStyle("-fx-font-size: 30pt;");
        }
        else if(phase == 3){
            InfoLabel.setText("Mozesz wystawiac karty lub zakonczyc ture");
            InfoLabel.setStyle("-fx-font-size: 30pt;");
        }
        else if(phase == 4){
            InfoLabel.setText("Zakoncz ture");
            InfoLabel.setStyle("-fx-font-size: 30pt;");
        }
    }
    @FXML
    protected void onMoneyScreenEntered()
    {
        InfoLabel.setText("Ilość monet: " + MoneyPlayerValue.getText());
        InfoLabel.setStyle("-fx-font-size: 35pt;");
    }

    @FXML
    protected void onTakeCardClicked()
    {
        //click_sound();
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(phase == 2){
                    choice = 1;
                    if(SWITCHER == 2) {
                        client.sendMessageToServer("" + choice);
                    }
                }
            }
        }).start();*/
    }
    @FXML
    protected void onTakeCardEntered()
    {
        hover_sound();
        InfoLabel.setText("Stos kart stworow");

    }
    @FXML
    protected void onRageCardClicked()
    {
        //click_sound();
    }
    @FXML
    protected void onRageCardEntered()
    {
        hover_sound();
        InfoLabel.setText("Stos kart RAGE");
    }
    @FXML
    protected void onMoneyStackClicked()
    {
        //click_sound();
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(phase == 2){
                    choice = 2;
                    if(SWITCHER == 2) {
                        client.sendMessageToServer("" + choice);
                    }
                }
            }
        }).start();*/
    }
    @FXML
    protected void onMoneyStackEntered()
    {
        hover_sound();
        InfoLabel.setText("Stos monet");
    }
    @FXML
    protected void onLostCardClicked()
    {
        //click_sound();
    }
    @FXML
    protected void onLostCardEntered()
    {
        hover_sound();
        onGridEntered(lostcardgrid);
    }

    private void onRageEntered(ImageView rageCard){
        new Thread(new Runnable() {
            @Override
            public void run() {
                char[] code = rageCard.getImage().getUrl().toCharArray();
                int i = 5;
                String name = "";
                String description = "";
                /*
                rection lection
                ar sher cher
                ts ss
                et it nt ot
                 */
                if ((int) code[code.length - i] == 97){
                    name = "Paranoia";
                    description = "wszystkie wrogie jednostki natychmiast wracaja do wlasciciela (jednorazowe)";
                }
                else if((int) code[code.length - i] == 100){
                    name = "Final Word";
                    description = "natychmiast wystawiasz jednostke bez wzgledu na koszt (jednorazowe)";
                }
                else if((int) code[code.length - i] == 101){
                    i++;
                    if((int) code[code.length - i] == 99){
                        name = "Second Chance";
                        description = "dobierajac zeton waluty dobierasz dwa, jeden zatrzymujesz, jeden odrzucasz (stale)";
                    }
                    //przypadek widomo dla domyslnego obrazka pola karty Rage
                }
                else if((int) code[code.length - i] == 102){
                    name = "Thief";
                    description = "po kazdym przetasowaniu zetonow waluty dobierasz 3 zetony (stale)";
                }
                else if((int) code[code.length - i] == 103){
                    name = "Recruiting";
                    description = "natychmiast dobierasz 5 kart stworow (jednorazowe)";
                }
                else if((int) code[code.length - i] == 107){
                    name = "Power Pack";
                    description = "natychmiast dobierasz 2 karty stworow, 2 zetony waluty i 2 stwory ze stosu kart odrzuconych";
                }
                else if((int) code[code.length - i] == 108){
                    name = "Betrayal";
                    description = "natychmiast przeciagasz stwora przeciwnika na swoja strone (jednorazowe)";
                }
                else if((int) code[code.length - i] == 110){
                    i += 4;
                    if((int) code[code.length - i] == 97){
                        name = "Extermination";
                        description = "natychmiast zabijasz przeciwnikowi 3 jednostki (jednorazowe)";
                    }
                    else {
                        i += 2;
                        if((int) code[code.length - i] == 108){
                            name = "Selection";
                            description = "dobierajac karte stwora dobierasz dwie, jedna zatrzymujesz, jedna odrzucasz (stale)";
                        }
                        else {
                            name = "Resurection";
                            description = "natychmiast dobierasz 5 kart stworow ze stosu kart odrzuconych (jednorazowe)";
                        }
                    }
                }
                else if((int) code[code.length - i] == 114){
                    i += 3;
                    if((int) code[code.length - i] == 99){
                        name = "Rat Catcher";
                        description = "na koniec kazdej swojej tury dobierasz karte stwora (stale)";
                    }
                    else{
                        name = "Crusher";
                        description = "twoje jednostki moga niszczyc stwory wroga nawet gdy remisuja atakiem z ich hp (stale)";
                    }
                }
                else if((int) code[code.length - i] == 115){
                    i++;
                    if((int) code[code.length - i] == 115){
                        name = "Weakness";
                        description = "rywal natychmiast musi odrzucic 3 karty z ekwipunku (jednorazowe)";
                    }
                    else if((int) code[code.length - i] == 116){
                        name = "Secret Assets";
                        description = "na koniec kazdej swojej tury dobierasz zeton waluty (stale)";
                    }
                }
                else if((int) code[code.length - i] == 116){
                    i++;
                    if((int) code[code.length - i] == 101){
                        name = "Black Market";
                        description = "zamiast wystawiania stworow mozesz jednego sprzedac (stale)";
                    }
                    else if((int) code[code.length - i] == 105){
                        name = "Profit";
                        description = "natychmiast dobierasz 5 zetonow waluty (jednorazowe)";
                    }
                    else if((int) code[code.length - i] == 110){
                        name = "Redeployment";
                        description = "mozesz cofnac swojego stwora do ekwipunku, jesli to zrobisz - dobierz 3 zetony waluty (jednorazowe)";
                    }
                    else if((int) code[code.length - i] == 111){
                        name = "Countershot";
                        description = "przeciwnik natychmiast traci serduszko i nie dobiera karty Rage (jednorazowe)";
                    }
                }

                final String trueName = name;
                final String trueDescription = description;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(!trueName.equals(""))
                            InfoLabel.setText(trueName + " - " + trueDescription);
                    }
                });
            }
        }).start();
    }

    @FXML
    protected void onMyRage1Entered()
    {
        rage1.setX(2);
        //InfoLabel.setText("Obecnie nie masz żadnej karty RAGE");
        onRageEntered(rage1);
    }
    @FXML
    protected void onMyRage2Entered()
    {
        rage2.setX(2);
        onRageEntered(rage2);
    }
    @FXML
    protected void onMyRage3Entered()
    {
        rage3.setX(2);
        onRageEntered(rage3);
    }
    @FXML
    protected void onEnemyRage1Entered()
    {
        rage1_enemy.setX(2);
        onRageEntered(rage1_enemy);
    }
    @FXML
    protected void onEnemyRage2Entered()
    {
        rage2_enemy.setX(2);
        onRageEntered(rage2_enemy);
    }
    @FXML
    protected void onEnemyRage3Entered()
    {
        rage3_enemy.setX(2);
        onRageEntered(rage3_enemy);
    }
    @FXML
    protected void onMyCharacterEntered()
    {
        //InfoLabel.setText("Gracz: d4krzyk \n Życia: 3");//Trzeba dodać nicki na wyswietlanie
        InfoLabel.setText("Gracz: " + Memory.memory.getUsername() + "\n Życia: 3");
        InfoLabel.setStyle("-fx-font-size: 24pt;");
    }
    @FXML
    protected void onEnemyCharacterEntered()
    {
        //InfoLabel.setText("Przeciwnik: Brewek \n Życia: 3");//Trzeba dodać nicki na wyswietlanie
        InfoLabel.setText("Gracz: " + opponentNick + "\n Życia: 3");
        //System.out.println(Memory.memory.getUsername());
        InfoLabel.setStyle("-fx-font-size: 24pt;");
    }

    @FXML
    protected void onEndTurnButtonEntered()
    {
        InfoLabel.setText("Chcesz zakończyć turę?");
        InfoLabel.setStyle("-fx-font-size: 25pt;");
    }

    @FXML
    protected void onSelectField1Pressed()
    {
        if(!myTurn)
            disable_button_sound();
        EQ1.setFitHeight(160);
        EQ1.setFitWidth(160);
        EQ1.setX(10);
        EQ1.setY(15);
    }
    @FXML
    protected void onSelectField2Pressed()
    {
        if(!myTurn)
            disable_button_sound();
        EQ2.setFitHeight(160);
        EQ2.setFitWidth(160);
        EQ2.setX(20);
        EQ2.setY(25);
    }
    @FXML
    protected void onSelectField3Pressed()
    {
        if(!myTurn)
            disable_button_sound();
        EQ3.setFitHeight(160);
        EQ3.setFitWidth(160);
        EQ3.setX(20);
        EQ3.setY(25);
    }
    @FXML
    protected void onSelectField4Pressed()
    {
        if(!myTurn)
            disable_button_sound();
        EQ4.setFitHeight(160);
        EQ4.setFitWidth(160);
        EQ4.setX(20);
        EQ4.setY(25);
    }

    @FXML
    protected void onSelectField1Released()
    {
        //Image postac = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Creatures/Brewek_arts/023.png")));
        //EQ1.setImage(postac);
        EQ1.setFitHeight(180);
        EQ1.setFitWidth(180);
        EQ1.setX(0);
        EQ1.setY(0);
        if(phase == 3) {
            choice = eq_it;
            if(SWITCHER == 2){
                client.sendMessageToServer("" + choice);
            }
        } else {
            disable_button_sound();
        }

    }
    @FXML
    protected void onSelectField2Released()
    {
        EQ2.setFitHeight(180);
        EQ2.setFitWidth(180);
        EQ2.setX(10);
        EQ2.setY(15);
        if(phase == 3) {
            choice = eq_it + 1;
            if(SWITCHER == 2){
                client.sendMessageToServer("" + choice);
            }
        }else {
            disable_button_sound();
        }
    }
    @FXML
    protected void onSelectField3Released()
    {
        //EQ3.setImage(null);
        EQ3.setFitHeight(180);
        EQ3.setFitWidth(180);
        EQ3.setX(10);
        EQ3.setY(15);
        if(phase == 3) {
            choice = eq_it + 2;
            if(SWITCHER == 2){
                client.sendMessageToServer("" + choice);
            }
        }else {
            disable_button_sound();
        }
    }
    @FXML
    protected void onSelectField4Released()
    {
        EQ4.setFitHeight(180);
        EQ4.setFitWidth(180);
        EQ4.setX(10);
        EQ4.setY(15);
        if(phase == 3) {
            choice = eq_it + 3;
            if(SWITCHER == 2){
                client.sendMessageToServer("" + choice);
            }
        }else {
            disable_button_sound();
        }
    }
    @FXML
    protected void onLeftPageButtonClicked()
    {
        page_sound();
    }
    @FXML
    protected void onRightPageButtonClicked()
    {
        page_sound();
    }

    private void onGridEntered(ImageView grid){
        if(grid.getImage() != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    char[] code = grid.getImage().getUrl().toCharArray();
                    String power = "";
                    char hp;
                    char atack;
                    char c1, c2;
                    String cost = "";

                    //[48-57]   -   cyfry w ASCI

                    //  Ex.1  2079M
                    //  Ex.2  /2079
                    //  Ex.3  /209M
                    //  Ex.4  */789
                    char current_char;  //pomocnicza zmienna do aktualnie przetwarzanego znaku
                    int i = 5;          //pomocnicza zmienna do ustalania znaku z kodu, ktory bedzie do przetworzenia

                    current_char = code[code.length-i];
                    if((int) current_char >= 48 && (int) current_char <= 57) {  //jesli ostatni znak w kodzie to cyfra - postac jest bez mocy Ex.2 lub Ex.4
                        power = "-";
                        hp = current_char;
                        i++;
                        current_char = code[code.length-i];     //nastepnym znakiem musi byc atak
                        atack = current_char;
                        i++;
                    }
                    else{                                                       //jesli ostatni znak w kodzie to NIE cyfra - postac ma moc Ex.1 lub Ex.3
                        power += current_char;
                        i++;
                        current_char = code[code.length-i];     //nastepnym znakiem musi byc hp
                        hp = current_char;
                        i++;
                        current_char = code[code.length-i];     //nastepnym znakiem musi byc atak
                        atack = current_char;
                        i++;
                    }

                    current_char = code[code.length-i];
                    c2 = current_char;       //legalnie pobieram czesc kosztu (liczba jednostek)
                    i++;

                    current_char = code[code.length-i];
                    if(!((int) current_char == 47)) {     //jezeli ostatni sprawdzany znak NIE jest slashem, to pobieram go jako kolejna czesc kosztu (liczba dziesiatek)
                        c1 = current_char;
                        cost += c1;     //doklejam dziesiatki do kosztu
                    }
                    cost += c2; //doklejam jednostki do kosztu

                    //przypisanie wyluskanych wartosci do zmiennych ostatecznych XD, ktorych dopiero mozna uzyc w runLater()
                    final String trueCost = cost;
                    final char trueAtack = atack;
                    final char trueHp = hp;
                    final String truePower = power;
                    final String trueDescription = powersDescription(power);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            InfoLabel.setText("Koszt: " + trueCost +
                                    "       Atak: " + trueAtack +
                                    "       Hp: " + trueHp +
                                    "\nMoc: " + truePower + trueDescription);
                            InfoLabel.setStyle("-fx-font-size: 18pt;");
                        }
                    });

                }
            }).start();
        }
    }


    @FXML
    public void onMyGrid0Entered() {
        onGridEntered(mygrid0);
    }
    @FXML
    public void onMyGrid1Entered() {
        onGridEntered(mygrid1);
    }
    @FXML
    public void onMyGrid2Entered() {
        onGridEntered(mygrid2);
    }
    @FXML
    public void onMyGrid3Entered() {
        onGridEntered(mygrid3);
    }
    @FXML
    public void onMyGrid4Entered() {
        onGridEntered(mygrid4);
    }
    @FXML
    public void onEnemyGrid0Entered() {
        onGridEntered(enemygrid0);
    }
    @FXML
    public void onEnemyGrid1Entered() {
        onGridEntered(enemygrid1);
    }
    @FXML
    public void onEnemyGrid2Entered() {
        onGridEntered(enemygrid2);
    }
    @FXML
    public void onEnemyGrid3Entered() {
        onGridEntered(enemygrid3);
    }
    @FXML
    public void onEnemyGrid4Entered() {
        onGridEntered(enemygrid4);
    }

    @FXML
    protected void onSelectField1Entered()
    {
        hover_sound();
        onGridEntered(EQ1);
    }
    @FXML
    protected void onSelectField2Entered()
    {
        hover_sound();
        onGridEntered(EQ2);
    }
    @FXML
    protected void onSelectField3Entered()
    {
        hover_sound();
        onGridEntered(EQ3);
    }
    @FXML
    protected void onSelectField4Entered()
    {
        hover_sound();
        onGridEntered(EQ4);
    }


    private String powersDescription(String power){
        String description = "";
        if(power.equals("-"))
            return description;
        switch (power) {
            case "D" -> description = "gdy wystawisz tego stwora - dobierasz karte stwora";
            case "E" -> description = "gdy wystawisz tego stwora mozesz natychmiast wystawic za darmo kolejnego z moca E";
            case "F" -> description = "jesli ten stwor nie ma przeciwnika, to atakuje wroga na polu dalej";
            case "G" -> description = "cofa pierwszego napotkanego przeciwnika do ekwipunku oponenta";
            case "H" -> description = "gdy wystawisz tego stwora - dobierasz ostatnio odrzucona karte stwora";
            case "M" -> description = "gdy wystawisz tego stwora - dobierasz zeton waluty";
            case "N" -> description = "jednorazowo unika swojej smierci";
            case "O" -> description = "gdy wystawisz tego stwora mozesz natychmiast wystawic za darmo kolejnego stwora";
            case "R" -> description = "gdy sojusznicza jednostka ma zginac, ta poswieci sie zamiast tamtej";
            case "U" -> description = "dopoki ta jednostka zyje, stwory na dwoch polach za nia sa niesmiertelne";
            case "X" -> description = "zabija pierwsza napotkana jednostke bez wzgledu na statystyki";
            case "Z" -> description = "gdy wystawisz tego stwora mozesz natychmiast zamienic go miejscami z innym swoim stworem";
        }
        return " - " + description;
    }

    public class Field{
        public Image image;
        //0-4 server
        //5-9 client
        public int id;

        Field(int id){
            this.id = id;
            this.image = null;
        }

        public Image getImage(){
            return this.image;
        }

        public void setImage(Image image){
            this.image = image;
        }
    }

}


