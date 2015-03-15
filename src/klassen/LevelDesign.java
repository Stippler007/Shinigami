/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen;

import klassen.karte.haus.Haus;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static klassen.Background.x;
import static klassen.Background.y;
import klassen.karte.GameObjects;
import klassen.karte.*;
import klassen.karte.arrow.Arrow;
import klassen.karte.carpet.Carpet_Full;
import klassen.karte.carpet.FootCarpet;
import klassen.karte.flowers.BlueFlower;
import klassen.karte.flowers.YellowFlower;
import klassen.karte.haus.Door;
import klassen.minion.Minion;
import klassen.minion.hundeGhoul.Hund;
import klassen.npc.Guard;
import klassen.npc.NPC;
import klassen.npc.OldMan;
import klassen.npc.Sign;
import klassen.player.FireShot;
import klassen.player.Player;
import static klassen.player.Player.speedX;
import static klassen.player.Player.speedY;
import klassen.player.PlayerSpritzer;

/**
 *
 * @author Christian
 */
public class LevelDesign implements Runnable {

    private Player player;
    private LinkedList<Minion> minions;
    private LinkedList<NPC> npcs;

    private Background bg;

    private float backX = -847;
    private float backY = -1045;
    private LinkedList<PlayerSpritzer> playerSpritzers;

    private int brightness = -500;

    private boolean pause;

    public LevelDesign(Player player, Background bg, LinkedList<Minion> minions,
            LinkedList<NPC> npcs, LinkedList<PlayerSpritzer> playerSpritzers) {
        this.bg = bg;
        this.player = player;
        this.minions = minions;
        this.npcs = npcs;
        this.playerSpritzers = playerSpritzers;
    }

    public void update() throws Exception {
//    GameObjects[][] map=bg.getMap();
//    
//    for (PlayerSpritzer ps:playerSpritzers) 
//    {
//      if(ps instanceof FireShot)
//      {
////        float x=(Background.x*-1)-ps.getX();
////        float y=(Background.y*-1)-ps.getY();
////        System.out.println(Background.x/25+"   "+ps.getX()/25);
////        map[(int)(x/25*-1)][(int)(y/25*-1)].setBrightness(50);
//        for (int i =(int)(x/25*-1); i < (int)(x/25*-1)+34; i++) 
//        {
//          for (int j = (int)(y/25*-1); j < (int)(y/25*-1)+26; j++) 
//          {
//            if(ps.getBounding().intersects(map[i][j].getBounding()))
//            {
//              map[i][j].setCurrentBrightness(50);
//            }
//          }
//        }
//      }
//    }

    }

    public boolean isPause() {
        return pause;
    }

    @Override
    public void run() {

    }

    public void loadLevel(int i, float startX, float startY) {
        pause = true;
        switch (i) {
            case 0:
                test(startX, startY);
                break;
            case 1:
                tutorial(startX, startY, new GameObjects[100][100]);
                break;
            case 2:
                haus(startX, startY);
                break;
            case 3:
                route101(startX, startY);
                break;
            case 4:
                route202(startX, startY);
                break;
        }
        pause = false;
    }

    public void tutorial(float startX, float startY, GameObjects map[][]) {
        clear();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < map.length - 1; k += 2) {
                    for (int l = 0; l < map.length - 2; l += 3) {
                        map[i + k][j + l] = new Tree(brightness, i, j);
                    }
                }
            }
        }
    //links oben 20/19
        //rechts unten 71/69

        for (int i = 20; i < 72; i++) {
            for (int j = 21; j < 69; j++) {
                map[i][j] = new Gras(brightness);
            }
        }
        // Baum füllen oben links
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                for (int t = 0; t < 18; t += 3) {
                    for (int k = 0; k < map.length / 2 - 8; k += 2) {
                        map[i + 0 + k][j + 0 + t] = new Tree(brightness, i, j);
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 21; j++) {
                map[i + 42][j] = new Arrow(brightness, 3, -750, -2075, this);
            }
        }

        //Haus 1
        for (int i = 48; i < 59; i++) {
            for (int j = 30; j < 41; j++) {
                map[i][j] = new Haus(brightness, i - 48, j - 30);
            }
        }
//    map[3+48][9+30]=new Door(0, 0,this,-380,-575,2);
//    map[4+48][9+30]=new Door(1, 0,this,-380,-575,2);
//    map[3+48][10+30]=new Door(0, 1,this,-380,-575,2);
//    map[4+48][10+30]=new Door(1, 1,this,-380,-575,2);

        // h2
        for (int i = 30; i < 41; i++) {
            for (int j = 30; j < 41; j++) {
                map[i][j] = new Haus(brightness, i - 30, j - 30);
            }
        }
        map[3 + 30][9 + 30] = new Door(brightness, 0, 0, this, -825, -652, 2);
        map[4 + 30][9 + 30] = new Door(brightness, 1, 0, this, -825, -652, 2);
        map[3 + 30][10 + 30] = new Door(brightness, 0, 1, this, -825, -652, 2);
        map[4 + 30][10 + 30] = new Door(brightness, 1, 1, this, -825, -652, 2);

        //h3
        for (int i = 30; i < 41; i++) {
            for (int j = 53; j < 64; j++) {
                map[i][j] = new Haus(brightness, i - 30, j - 53);
            }
        }
//    map[3+30][9+53]=new Door(0, 0,this,-380,-575,2);
//    map[4+30][9+53]=new Door(1, 0,this,-380,-575,2);
//    map[3+30][10+53]=new Door(0, 1,this,-380,-575,2);
//    map[4+30][10+53]=new Door(1, 1,this,-380,-575,2);

        map[50][50] = new Weg(brightness);

        for (int k = 0; k < 10; k += 1) {
            map[51][41 + k] = new Weg(brightness);
        }
        for (int k = 0; k < 10; k += 1) {
            map[52][41 + k] = new Weg(brightness);
        }

        for (int k = 0; k < 20; k += 1) {
            map[33 + k][50] = new Weg(brightness);
        }
        for (int k = 0; k < 20; k += 1) {
            map[33 + k][49] = new Weg(brightness);
        }

        for (int k = 0; k < 10; k += 1) {
            map[33][41 + k] = new Weg(brightness);
        }

        for (int k = 0; k < 10; k += 1) {
            map[34][41 + k] = new Weg(brightness);
        }

        for (int k = 0; k < 15; k += 1) {
            map[41][51 + k] = new Weg(brightness);
        }

        for (int k = 0; k < 15; k += 1) {
            map[42][51 + k] = new Weg(brightness);
        }

        for (int k = 0; k < 10; k += 1) {
            map[33 + k][65] = new Weg(brightness);
        }
        for (int k = 0; k < 10; k += 1) {
            map[33 + k][66] = new Weg(brightness);
        }
        map[33][64] = new Weg(brightness);
        map[34][64] = new Weg(brightness);

    // Baum Deko
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                map[i + 40][j + 45] = new Tree(brightness, i, j);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                map[i + 43][j + 55] = new Tree(brightness, i, j);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                map[i + 31][j + 43] = new Tree(brightness, i, j);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                map[i + 31][j + 64] = new Tree(brightness, i, j);
            }
        }
    // Deko Blumen

        map[32][49] = new BlueFlower(brightness);
        map[32][50] = new BlueFlower(brightness);

        map[53][49] = new BlueFlower(brightness);
        map[53][50] = new BlueFlower(brightness);

        map[41][67] = new BlueFlower(brightness);
        map[42][67] = new BlueFlower(brightness);

        Background.x = startX;
        Background.y = startY;

        npcs.add(new Sign(Background.x + 1175, Background.y + 550, map, player, "Beyond this gateway formed out of trees there is a wild and uncharted plane called\n"
                + "route 101. Stories of magical creatures, relentless enemies and mysteries are told\n"
                + "round this place. If, you are in search of honor and glory earnd by deafting\n"
                + "powerfull enemies, this is the way to go for you. BUT be warned, you could be \n"
                + "forced to pay the ultimate prize during your adventure on this mystical plane."));
        npcs.add(new Guard(Background.x + 1175, Background.y + 600, 0, map, player, "Riesencock"));

        for (int i = 0; i < map.length - 1; i++) {
            for (int j = 0; j < map.length - 1; j++) {
                map[i][j].setBrightness(-100);
            }
        }

        player.setMap(map);
        bg.setMap(map);
        for (NPC npc : npcs) {
            npc.setMap(map);
        }
    }

    public void route101(float startX, float startY) {
        clear();
        GameObjects map[][] = new GameObjects[100][100];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = new Wand(brightness);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < map.length - 1; k += 2) {
                    for (int l = 0; l < map.length - 2; l += 3) {
                        map[i + k][j + l] = new Tree(brightness, i, j);
                    }
                }
            }
        }
        for (int i = 20; i < map.length - 20; i++) {
            for (int j = 15; j < map[0].length - 16; j++) {
                map[i][j] = new Gras(brightness);
            }
        }

        Background.x = startX + 400;
        Background.y = startY + 300;

        for (int i = 0; i < map.length - 1; i++) {
            for (int j = 0; j < map.length - 1; j++) {
                map[i][j].setBrightness(-100);
            }
        }

        for (int i = 84; i <= 95; i++) {
            for (int j = 28; j <= 31; j++) {
                map[j][i] = new Arrow(brightness, 1, -700, -250, this);
            }
        }
        
        for (int i = 0; i <= 15; i++)  {
            for (int j = 73; j < 79; j++) {
                map[j][i] = new Arrow(brightness, 4, -1000, -300, this);
            }
        }

        player.setMap(map);
        bg.setMap(map);
        for (NPC npc : npcs) {
            npc.setMap(map);
        }
    }
    
    public void route202(float startX, float startY) {
        clear();
        GameObjects map[][] = new GameObjects[100][100];
        
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                map[i][j] = new Gras(brightness);
            }
        }
        
        Background.x = startX + 400;
        Background.y = startY + 300;

        for (int i = 0; i < map.length - 1; i++) {
            for (int j = 0; j < map.length - 1; j++) {
                map[i][j].setBrightness(-100);
            }
        }
        
        player.setMap(map);
        bg.setMap(map);
        for (NPC npc : npcs) {
            npc.setMap(map);
        }
    }

    public void haus(float startX, float startY) {
        clear();

        backX = Background.x;
        backY = Background.y - 25;

        GameObjects map[][] = new GameObjects[60][60];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Wand(brightness);
            }
        }
        for (int i = 20; i < 37; i++) {
            for (int j = 15; j < 27; j++) {
                map[i][j] = new Boden(brightness);
            }
        }

        map[32][27] = new FootCarpet(brightness, 0, 0, backX, backY, 1, this);
        map[33][27] = new FootCarpet(brightness, 1, 0, backX, backY, 1, this);

    // 13 14
        for (int i = 1; i <= 5; i++) {
            for (int j = 4; j <= 7; j++) {
                map[i + 20][j + 15] = new Carpet_Full(brightness, 1, 1);
            }
        }
        map[0 + 20][3 + 15] = new Carpet_Full(brightness, 0, 0);
        map[6 + 20][3 + 15] = new Carpet_Full(brightness, 2, 0);
        map[0 + 20][8 + 15] = new Carpet_Full(brightness, 0, 2);
        map[6 + 20][8 + 15] = new Carpet_Full(brightness, 2, 2);
        for (int i = 1; i <= 5; i++) {
            map[i + 20][3 + 15] = new Carpet_Full(brightness, 1, 0);
        }
        for (int i = 1; i <= 5; i++) {
            map[i + 20][8 + 15] = new Carpet_Full(brightness, 1, 2);
        }

        Background.x = startX + 400;
        Background.y = startY + 300;

        npcs.add(new OldMan(1000 - 400, 550 - 300, 0, map, player, "You wanna suuu my donga?"));

        player.setMap(map);
        bg.setMap(map);

        for (NPC npc : npcs) {
            npc.setMap(map);
        }

    }

    public void test(float startX, float startY) {
        clear();

        GameObjects map[][] = new GameObjects[100][100];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = new Wand(brightness);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < map.length - 1; k += 2) {
                    for (int l = 0; l < map.length - 2; l += 3) {
                        map[i + k][j + l] = new Tree(brightness, i, j);
                    }
                }
            }
        }
        for (int i = 20; i < map.length - 20; i++) {
            for (int j = 15; j < map[0].length - 16; j++) {
                map[i][j] = new Gras(brightness);
            }
        }

        Background.x = startX + 400;
        Background.y = startY + 300;

        for (int i = 0; i < 60; i++) {
            minions.add(new Hund(Background.x + 1476 + 400, Background.y + 400 + i * 30, 200, map, player, playerSpritzers, minions));
        }

//    for (int i = 0; i < map.length-1; i++) 
//    {
//      for (int j = 0; j < map.length-1; j++) 
//      {
//        map[i][j].setBrightness(-100);
//      }
//    }
        player.setMap(map);
        bg.setMap(map);
        for (NPC npc : npcs) {
            npc.setMap(map);
        }
    }

    private void clear() {
        minions.clear();
        npcs.clear();
    }
}
