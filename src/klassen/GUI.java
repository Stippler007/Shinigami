/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import klassen.boss.Boss;
import klassen.boss.BossSpritzer;
import klassen.listener.KL;
import klassen.listener.ML;
import klassen.listener.MML;
import klassen.minion.Minion;
import klassen.minion.MinionSpritzer;
import klassen.npc.NPC;
import klassen.player.Player;
import klassen.player.PlayerSpritzer;

/**
 *
 * @author Christian
 */
public class GUI extends JFrame implements Runnable{
    
  private LevelDesign ld;

  private Player player;
  private List<PlayerSpritzer> playerSpritzer;

  private List<Boss> boss;
  private List<BossSpritzer> bossSpritzer;

  private List<Minion> minions;
  private List<MinionSpritzer> minionSpritzer;

  private List<NPC> npcs;

  private Background bg;

  private BufferStrategy strat;

  private double haha=0;

  public GUI(Player player, List<PlayerSpritzer> playerSpritzer,
          List<Boss> boss, List<BossSpritzer> bossSpritzer,
          List<Minion> minion, List<MinionSpritzer> minionSpritzer,
          List<NPC> npcs,Background bg,LevelDesign ld){
      this.player = player;
      this.playerSpritzer = playerSpritzer;
      this.boss = boss;
      this.bossSpritzer = bossSpritzer;
      this.minions = minion;
      this.minionSpritzer = minionSpritzer;
      this.npcs=npcs;
      this.bg = bg;
      this.ld=ld;
      
      
      
      addKeyListener(new KL(player,npcs));
      addMouseListener(new ML(this));
      addMouseMotionListener(new MML(this));
  }


  public void makeStrat(){
      createBufferStrategy(2);
      strat = getBufferStrategy();
  }
  private float xScaling = 1;
  private float yScaling = 1;
  public void setFullscreen()
  {
    setExtendedState(Frame.MAXIMIZED_BOTH);
    xScaling = (float)getWidth()/800f;
    yScaling = (float)getHeight()/600f;
    setLocationRelativeTo(null);
  }
  public void repaintScreen()
  {
    Graphics2D g=(Graphics2D)strat.getDrawGraphics();
    g.scale(xScaling, yScaling);
    if(ld.isPause())drawLoadingScreen(g);
    else draw(g);
    g.dispose();
    strat.show();
  }
  private void draw(Graphics2D g){
    g.setColor(Color.black);
    g.fillRect(0, 0,800, 600);
    bg.drawBG(g);

    if(player.getHealth()>0)g.drawImage(player.getLook(), null,375,275);
    else g.drawString("Game Over!", 300, 300);

    for(PlayerSpritzer ps:playerSpritzer){
        g.drawImage(ps.getLook(), null, ps.getBounding().x, ps.getBounding().y);
    }
    for(Minion m:minions){
        m.draw(g);
    }
    for(MinionSpritzer ms:minionSpritzer){
        g.rotate( ms.getTurn(),  ms.getBounding().x+ms.getBounding().width/2,  ms.getBounding().y+ms.getBounding().height/2);
        g.drawImage(ms.getLook(), null, ms.getBounding().x, ms.getBounding().y);
        g.rotate( -ms.getTurn(),  ms.getBounding().x+ms.getBounding().width/2,  ms.getBounding().y+ms.getBounding().height/2);
    }
    for (Boss b : boss)
    {
      b.draw(g);
    }
      for (NPC npc : npcs) 
      {
        npc.draw(g);
      }
      // Lebensanzeige:
      g.setColor(Color.black);
      g.fillRect(50, 550, 110, 35);
      g.setColor(Color.red);
      g.fillRect(55, 555, 100, 25);
      g.setColor(Color.green);
      g.fillRect(55, 555, (int)(100*(player.getHealth()/player.getMaxHealth())), 25);
      g.setColor(Color.WHITE);
      g.drawString((int)player.getHealth()+"  /  "+(int)player.getMaxHealth(), 75, 571);
    
      if(TextManager.getInstance().isTexting())
      {
        Color c=new Color(192,192,192);
        g.setColor(c);
        g.fillRect(100, 450, 600, 100);
        g.setColor(Color.BLACK);
        g.drawRect(100, 450, 600, 100);
        Font f=new Font("Courier New", Font.BOLD, 12);
        g.setFont(f);
        String str[]=TextManager.getInstance().getText();
        for (int i = TextManager.getInstance().getPosition(); i < TextManager.getInstance().getPosition()+4 && i < TextManager.getInstance().getText().length; i++)
        {
          g.drawString(str[i], 110, 470+20*(i-TextManager.getInstance().getPosition()));
        }
      }
//      g.drawImage(ImageFactory.getIF().getLook("guard"), null, 0, 0);
      
//      g.drawImage(npcs.get(0).getLook(), 30, 30, null);
//        g.rotate(haha,400,300);
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("Arial",0,200));
//        g.drawString("DICK", 200, this.getHeight()-50);
//        haha+=0.1;
//        g.rotate(-3*haha,400,300);
//        g.drawString("Wert", 200, this.getHeight()-50);
//        
//        g.rotate(+4*haha,400,300);
//        
//        g.drawString("Gerd",200, this.getHeight()-50);
//      System.out.println(ImageFactory.getIF().getLook("Gras0Brightness"));
    g.drawImage(ImageFactory.getIF().getLook("GrasBrightness"), null,0, 30);
  }
  private void drawLoadingScreen(Graphics2D g)
  {
    g.setColor(Color.black);
    g.fillRect(0, 0, 800, 600);
  }
  public float getxScaling() 
  {
    return xScaling;
  }

  public float getyScaling() 
  {
    return yScaling;
  }
  
  @Override
  public void run() 
  {
    while(true)
    {
      try
      {
        repaintScreen();
      }
      catch(Exception ex)
      {
//        JOptionPane.showMessageDialog(null, ex, "es dickt nicht", WIDTH);
      }
    }
  }
  
}
