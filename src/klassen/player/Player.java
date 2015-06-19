//
//package klassen.player;
//
//import java.awt.Rectangle;
//import java.awt.event.KeyEvent;
//import java.awt.image.BufferedImage;
//import java.util.LinkedList;
//import java.util.List;
//import javax.swing.text.StyleConstants;
//import klassen.Background;
//import klassen.ImageFactory;
//import klassen.karte.GameObjects;
//import klassen.listener.KL;
//import klassen.listener.ML;
//import klassen.listener.MML;
//import klassen.minion.Minion;
//import klassen.minion.MinionSpritzer;
//import klassen.npc.NPC;
//
///**
// *
// * @author Christian
// */
//public class Player {
//  
//  private List<PlayerSpritzer> playerSpritzer;
//  private List<NPC> npcs;
//  private List<Minion> minions;
//  private Rectangle bounding;
//  
//  private float health=100;
//  private float maxHealth=100;
//  
//  private BufferedImage look[]=new BufferedImage[3];
//  private GameObjects[][] map;
//  
//  private float maxAnimationTime=0.3f;
//  private float animationTime=0f;
//
//  private float speed;
//
//  public static float speedX;
//  public static float speedY;
//
//  private float realodTime=0;
//  private float maxRealodTime=0.3f;
//  
//  private float damage=25;
//  
//  private boolean move;
//  private boolean fired=false;
//  private boolean poisened=false;
//  
//  
//  private Shots shot=Shots.FLAMESHOT;
//  
//  
//  
//  public enum Shots
//  {
//      BASICSHOT,
//      FLAMESHOT,
//      ICESHOT,
//      ENERGYSHOT;
//  }
//  public Player(int width,int height,float speed,List<PlayerSpritzer> playerSpritzer,List<NPC> npcs,GameObjects[][] map,List<Minion> minions)
//  {
//    for (int i = 0; i < look.length; i++) 
//    {
//      look[i]=ImageFactory.getIF().getLook("PlayerVorne"+i);
//    }
//    bounding=new Rectangle(width/2-look[0].getWidth()/2+7,(height/2-look[0].getHeight()/2)+1,36,47);
//    this.map=map;
//    this.speed=speed;
//    this.playerSpritzer=playerSpritzer;
//    this.npcs=npcs;
//    this.minions=minions;
//  }
//
//  public void setShot(Shots shot) 
//  {
//    this.shot = shot;
//  }
//  
//  public void setMap(GameObjects[][] map)
//  {
//    this.map = map;
//  }
//  
//  public void setKnockbackX(float knockbackX)
//  {
//    this.knockbackX = knockbackX;
//  }
//
//  public void setKnockbackY(float knockbackY)
//  {
//    this.knockbackY = knockbackY;
//  }
//  
//  public void damaged(float damage)
//  {
//    health-=damage;
//  }
//  
//  public void update(float tslf)
//  {
//    switch(shot)
//    {
//        case BASICSHOT: updateBasicShot(tslf);
//            break;
//        case FLAMESHOT: updateFlameShot(tslf);
//            break;
//        case ICESHOT:updateIceShot(tslf);
//            break;
//        case ENERGYSHOT:;
//            break;
//    }
//    if(move&&animationTime<maxAnimationTime)animationTime+=tslf;
//    else if(move)
//    {
//      animationTime-=maxAnimationTime;
//    }
//    speedX=(int)speedX;
//    speedY=(int)speedY;
//    collide(tslf);
//  }
//  
//  private void updateBasicShot(float tslf){
//    move=false;
//    speedX=0;
//    speedY=0;
//    
//    move(tslf);
//    moveCockBack(tslf);
//    
//    
//    
//    if(ML.leftMousePressed&&realodTime>=maxRealodTime&&BasicShot.canShoot)
//    {
//        playerSpritzer.add(new BasicShot((float)(400-look[0].getWidth()/2),(float)(300-look[0].getHeight()/2),(float)(speed*5),maxRealodTime,damage,this));
//        realodTime-=maxRealodTime;
//    }
//    if(!move&&ML.rightMousePressed&&!ML.rightMouseRealeased)
//    {
//      speedX = (bounding.x+bounding.width/2)-(MML.x);
//      speedY = (bounding.y+bounding.height/2)-(MML.y);
//      
//      float help = (float)Math.sqrt(speedX*speedX+speedY*speedY);
//      
//      speedX/=help;
//      speedY/=help;
//      
//      speedX*=(speed*2)*tslf;
//      speedY*=(speed*2)*tslf;
//      
//      move=true;
//    }
//    if(realodTime<maxRealodTime){
//      realodTime+=tslf;
//    }
//  }
//  int x=0;
//  private void updateFlameShot(float tslf)
//  {
//    move=false;
//    speedX=0;
//    speedY=0;
//    move(tslf);
//    moveCockBack(tslf);
//    if(ML.leftMousePressed&&realodTime>maxRealodTime)
//    {
//      playerSpritzer.add(new FireShot((float)(400-8),(float)(300-8),
//                                      (float)(speed)*2,damage,this,playerSpritzer,minions));
//      realodTime-=maxRealodTime;
//    }
//    if(realodTime<=maxRealodTime)
//    {
//      realodTime+=tslf;
//    }
//  }
//  private void updateIceShot(float tslf)
//  {
//    move=false;
//    speedX=0;
//    speedY=0;
//    move(tslf);
//    moveCockBack(tslf);
//    if(ML.leftMousePressed&&realodTime>maxRealodTime)
//    {
//      playerSpritzer.add(new IceShot((float)(400-8),(float)(300-8),
//                                      (float)(speed)*2,damage,this,playerSpritzer,minions));
//      realodTime-=maxRealodTime;
//    }
//    if(realodTime<=maxRealodTime)
//    {
//      realodTime+=tslf;
//    }
//  }
//  private void collide(float tslf)
//  {
//    for (int i =(int)(Background.x/25*-1)+14; i < (int)(Background.x/25*-1)+18; i++) 
//    {
//      for (int j = (int)(Background.y/25*-1)+10; j < (int)(Background.y/25*-1)+14; j++) 
//      {
//        if(!(i<0||j<0)&&!(i>map.length-1||j>map[0].length-1))
//        {
//          Rectangle help1=new Rectangle(bounding.x-(int)(speedX),bounding.y-(int)(speedY),bounding.width,bounding.height);
//          if(map[i][j].isSolid()&&help1.intersects(map[i][j].getBounding()))
//          {
//            Rectangle help2=map[i][j].getBounding();
//
//            moveCollision(help1, help2);
//          }
//          if(map[i][j].getBounding().intersects(bounding.x+bounding.width/2-1, bounding.y+bounding.height/2, 2, 1))
//          {
//            map[i][j].steppedOn(true);
//            map[i][j].playerSteppedOn(this);
//          }
//        }
//        else
//        {
//          System.out.println("Out of map");
//        }
//      }
//    }
//    for (NPC npc:npcs)
//    {
//      Rectangle help1=new Rectangle(bounding.x-(int)(speedX),bounding.y-(int)(speedY),bounding.width,bounding.height);
//      if(npc.getBounding().intersects(help1))moveCollision(help1, npc.getBounding());
//    }
//  }
//  private void moveCollision(Rectangle help1,Rectangle help2)
//  {
//    double vonlinks=help1.x+help1.width-help2.x;
//    double vonoben=help1.y+help1.height-help2.y;
//    double vonrechts=help2.x+help2.width-help1.x;
//    double vonunten=help2.y + help2.height - help1.y;
//
//    if(vonlinks<vonoben&&vonlinks<vonrechts&&vonlinks<vonunten)
//    {
//      speedX+=vonlinks;
//    }
//    else if(vonoben<vonrechts&&vonoben<vonunten)
//    {
//      speedY+=vonoben;
//    }
//    else if(vonrechts<vonunten)
//    {
//      speedX-=vonrechts;
//    }
//    else
//    {
//      speedY-=vonunten;
//    }
//  }
//  private void move(float tslf)
//  {
//    speedX=0;
//    speedY=0;
//    
//    if(KL.keys[KeyEvent.VK_A])
//    {
//      speedX=speed*tslf;
//      move=true;
//    }
//    if(KL.keys[KeyEvent.VK_D])
//    {
//      speedX=-speed*tslf;
//      move=true;
//    }
//    if(KL.keys[KeyEvent.VK_W])
//    {
//      speedY=speed*tslf;
//      move=true;
//    }
//    if(KL.keys[KeyEvent.VK_S])
//    {
//      speedY=-speed*tslf;
//      move=true;
//    }
//  }
//  private float knockbackX=0;
//  private float knockbackY=0;
//  private float backKnockback=1000f;
//  public void moveCockBack(float tslf)
//  {
//    if(knockbackX!=0)
//    {
//      if(knockbackX>0)
//      {
//        knockbackX-=backKnockback*tslf;
//        if(knockbackX<0)knockbackX=0;
//      }
//      else{
//        knockbackX+=backKnockback*tslf;
//        if(knockbackX>0)knockbackX=0;
//      }
//    }
//    
//    if(knockbackY!=0)
//    {
//      if(knockbackY<0)
//      {
//        knockbackY+=backKnockback*tslf;
//        if(knockbackY>0)knockbackY=0;
//      }
//      else{
//        knockbackY-=backKnockback*tslf;
//        if(knockbackY<0)knockbackY=0;
//      }
//    }
//    Player.speedX+=knockbackX*tslf;
//    Player.speedY+=knockbackY*tslf;
//  }
//  public BufferedImage getLook(){
//    double turn=getTurn();
//    if(turn>=-Math.PI*0.25&&turn<=Math.PI*0.25){
//        for (int i = 0; i < look.length; i++) {
//            look[i]=ImageFactory.getIF().getLook("PlayerRechts"+i);
//        }
//    }
//    else if(turn>=Math.PI*0.25&&turn<=Math.PI*0.5){
//        for (int i = 0; i < look.length; i++) {
//            look[i]=ImageFactory.getIF().getLook("PlayerVorne"+i);
//        }
//    }
//    else if(turn>=Math.PI*0.50&&turn<=Math.PI*1){
//        for (int i = 0; i < look.length; i++) {
//            look[i]=ImageFactory.getIF().getLook("PlayerLinks"+i);
//        }
//    }
//    else{
//        for (int i = 0; i < look.length; i++) {
//            look[i]=ImageFactory.getIF().getLook("PlayerHinten"+i);
//        }
//    }
//    if(move)
//    {
//      for (int i = 0; i < look.length-1; i++) {
//        if(animationTime<(float)maxAnimationTime/(look.length-1)*(i+1))return look[i+1];
//      }
//    }
//    return look[0];
//  }
//  public float getRealodTime() {
//    return 0;
//  }
//  public Rectangle getBounding() {
//    return bounding;
//  }
//  public static double getTurn()
//  {
//    double a=MML.x-400;
//    double b=MML.y-300;
//    
//    double turn=Math.atan(b/a);
//    if(a<0){
//      turn+=2.3561944901923;
//    }
//    return turn;
//  }
//
//  public boolean isMove()
//  {
//    return move;
//  }
//  public float getHealth()
//  {
//    return health;
//  }
//  public float getMaxHealth()
//  {
//    return maxHealth;
//  }
//=======
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package klassen.player;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import javax.swing.text.StyleConstants;
import klassen.Background;
import klassen.ImageFactory;
import klassen.karte.GameObjects;
import klassen.listener.KL;
import klassen.listener.ML;
import klassen.listener.MML;
import klassen.minion.Minion;
import klassen.minion.MinionSpritzer;
import klassen.npc.NPC;

/**
 *
 * @author Christian
 */
public class Player {
  
  private List<PlayerSpritzer> playerSpritzer;
  private List<NPC> npcs;
  private List<Minion> minions;
  private Rectangle bounding;
  
  private float health=100;
  private float maxHealth=100;
  
  private BufferedImage look[]=new BufferedImage[3];
  private GameObjects[][] map;
  
  private float maxAnimationTime=0.3f;
  private float animationTime=0f;

  private float speed;

  public static float speedX;
  public static float speedY;

  private float realodTime=0;
  private float maxRealodTime=0.3f;
  
  private float damage=25;
  
  private boolean move;
  private boolean fired=false;
  private boolean poisened=false;
  
  
  private Shots shot=Shots.FLAMESHOT;
  
  
  
  public enum Shots
  {
      BASICSHOT,
      FLAMESHOT,
      ICESHOT,
      ENERGYSHOT;
  }
  public Player(int width,int height,float speed,List<PlayerSpritzer> playerSpritzer,List<NPC> npcs,GameObjects[][] map,List<Minion> minions)
  {
    for (int i = 0; i < look.length; i++) 
    {
      look[i]=ImageFactory.getIF().getLook("PlayerVorne"+i);
    }
    bounding=new Rectangle(width/2-look[0].getWidth()/2+7,(height/2-look[0].getHeight()/2)+1,36,47);
    this.map=map;
    this.speed=speed;
    this.playerSpritzer=playerSpritzer;
    this.npcs=npcs;
    this.minions=minions;
  }

  public void setShot(Shots shot) 
  {
    this.shot = shot;
  }
  
  public void setMap(GameObjects[][] map)
  {
    this.map = map;
  }
  
  public void setKnockbackX(float knockbackX)
  {
    this.knockbackX = knockbackX;
  }

  public void setKnockbackY(float knockbackY)
  {
    this.knockbackY = knockbackY;
  }
  
  public void damaged(float damage)
  {
    health-=damage;
  }
  
  public void update(float tslf)
  {
    switch(shot)
    {
        case BASICSHOT: updateBasicShot(tslf);
            break;
        case FLAMESHOT: updateFlameShot(tslf);
            break;
        case ICESHOT:updateIceShot(tslf);
            break;
        case ENERGYSHOT:;
            break;
    }
    if(move&&animationTime<maxAnimationTime)animationTime+=tslf;
    else if(move)
    {
      animationTime-=maxAnimationTime;
    }
    speedX=(int)speedX;
    speedY=(int)speedY;
    collide(tslf);
  }
  
  private void updateBasicShot(float tslf){
    move=false;
    speedX=0;
    speedY=0;
    
    move(tslf);
    moveCockBack(tslf);
    
    
    
    if(ML.leftMousePressed&&realodTime>=maxRealodTime&&BasicShot.canShoot)
    {
        playerSpritzer.add(new BasicShot((float)(400-look[0].getWidth()/2),(float)(300-look[0].getHeight()/2),(float)(speed*5),maxRealodTime,damage,this));
        realodTime-=maxRealodTime;
    }
    if(!move&&ML.rightMousePressed&&!ML.rightMouseRealeased)
    {
      speedX = (bounding.x+bounding.width/2)-(MML.x);
      speedY = (bounding.y+bounding.height/2)-(MML.y);
      
      float help = (float)Math.sqrt(speedX*speedX+speedY*speedY);
      
      speedX/=help;
      speedY/=help;
      
      speedX*=(speed*2)*tslf;
      speedY*=(speed*2)*tslf;
      
      move=true;
    }
    if(realodTime<maxRealodTime){
      realodTime+=tslf;
    }
  }
  int x=0;
  private void updateFlameShot(float tslf)
  {
    move=false;
    speedX=0;
    speedY=0;
    move(tslf);
    moveCockBack(tslf);
    if(ML.leftMousePressed&&realodTime>maxRealodTime)
    {
      playerSpritzer.add(new FireShot((float)(400-8),(float)(300-8),
                                      (float)(speed)*2,damage,this,playerSpritzer,minions));
      realodTime-=maxRealodTime;
    }
    if(realodTime<=maxRealodTime)
    {
      realodTime+=tslf;
    }
  }
  private void updateIceShot(float tslf)
  {
    move=false;
    speedX=0;
    speedY=0;
    move(tslf);
    moveCockBack(tslf);
    if(ML.leftMousePressed&&realodTime>maxRealodTime)
    {
      playerSpritzer.add(new IceShot((float)(400-8),(float)(300-8),
                                      (float)(speed)*2,damage,this,playerSpritzer,minions));
      realodTime-=maxRealodTime;
    }
    if(realodTime<=maxRealodTime)
    {
      realodTime+=tslf;
    }
  }
  private void collide(float tslf)
  {
    for (int i =(int)(Background.x/25*-1)+14; i < (int)(Background.x/25*-1)+18; i++) 
    {
      for (int j = (int)(Background.y/25*-1)+10; j < (int)(Background.y/25*-1)+14; j++) 
      {
        if(!(i<0||j<0)&&!(i>map.length-1||j>map[0].length-1))
        {
          
          Rectangle help1=new Rectangle(bounding.x-(int)(speedX),bounding.y-(int)(speedY),bounding.width,bounding.height);
          System.out.println("HELP: "+help1);
          System.out.println("MAP : i:"+i+" j:"+j+"      "+map[i][j].getBounding());
          if(map[i][j].isSolid()&&help1.intersects(map[i][j].getBounding()))
          {
            Rectangle help2=map[i][j].getBounding();
            moveCollision(help1, help2);
          }
          if(map[i][j].getBounding().intersects(bounding.x+bounding.width/2-1, bounding.y+bounding.height/2, 2, 1))
          {
            map[i][j].steppedOn(true);
            map[i][j].playerSteppedOn(this);
          }
        }
        else
        {
          System.out.println("Out of map");
        }
      }
    }
    for (NPC npc:npcs)
    {
      Rectangle help1=new Rectangle(bounding.x-(int)(speedX),bounding.y-(int)(speedY),bounding.width,bounding.height);
      if(npc.getBounding().intersects(help1))moveCollision(help1, npc.getBounding());
    }
  }
  private void moveCollision(Rectangle help1,Rectangle help2)
  {
    double vonlinks=help1.x+help1.width-help2.x;
    double vonoben=help1.y+help1.height-help2.y;
    double vonrechts=help2.x+help2.width-help1.x;
    double vonunten=help2.y + help2.height - help1.y;

    if(vonlinks<vonoben&&vonlinks<vonrechts&&vonlinks<vonunten)
    {
      speedX+=vonlinks;
    }
    else if(vonoben<vonrechts&&vonoben<vonunten)
    {
      speedY+=vonoben;
    }
    else if(vonrechts<vonunten)
    {
      speedX-=vonrechts;
    }
    else
    {
      speedY-=vonunten;
    }
  }
  private void move(float tslf)
  {
    speedX=0;
    speedY=0;
    
    if(KL.keys[KeyEvent.VK_A])
    {
      speedX=speed*tslf;
      move=true;
    }
    if(KL.keys[KeyEvent.VK_D])
    {
      speedX=-speed*tslf;
      move=true;
    }
    if(KL.keys[KeyEvent.VK_W])
    {
      speedY=speed*tslf;
      move=true;
    }
    if(KL.keys[KeyEvent.VK_S])
    {
      speedY=-speed*tslf;
      move=true;
    }
  }
  private float knockbackX=0;
  private float knockbackY=0;
  private float backKnockback=1000f;
  public void moveCockBack(float tslf)
  {
    if(knockbackX!=0)
    {
      if(knockbackX>0)
      {
        knockbackX-=backKnockback*tslf;
        if(knockbackX<0)knockbackX=0;
      }
      else{
        knockbackX+=backKnockback*tslf;
        if(knockbackX>0)knockbackX=0;
      }
    }
    
    if(knockbackY!=0)
    {
      if(knockbackY<0)
      {
        knockbackY+=backKnockback*tslf;
        if(knockbackY>0)knockbackY=0;
      }
      else{
        knockbackY-=backKnockback*tslf;
        if(knockbackY<0)knockbackY=0;
      }
    }
    Player.speedX+=knockbackX*tslf;
    Player.speedY+=knockbackY*tslf;
  }
  public BufferedImage getLook(){
    double turn=getTurn();
    if(turn>=-Math.PI*0.25&&turn<=Math.PI*0.25){
        for (int i = 0; i < look.length; i++) {
            look[i]=ImageFactory.getIF().getLook("PlayerRechts"+i);
        }
    }
    else if(turn>=Math.PI*0.25&&turn<=Math.PI*0.5){
        for (int i = 0; i < look.length; i++) {
            look[i]=ImageFactory.getIF().getLook("PlayerVorne"+i);
        }
    }
    else if(turn>=Math.PI*0.50&&turn<=Math.PI*1){
        for (int i = 0; i < look.length; i++) {
            look[i]=ImageFactory.getIF().getLook("PlayerLinks"+i);
        }
    }
    else{
        for (int i = 0; i < look.length; i++) {
            look[i]=ImageFactory.getIF().getLook("PlayerHinten"+i);
        }
    }
    if(move)
    {
      for (int i = 0; i < look.length-1; i++) {
        if(animationTime<(float)maxAnimationTime/(look.length-1)*(i+1))return look[i+1];
      }
    }
    return look[0];
  }
  public float getRealodTime() {
    return 0;
  }
  public Rectangle getBounding() {
    return bounding;
  }
  public static double getTurn()
  {
    double a=MML.x-400;
    double b=MML.y-300;
    
    double turn=Math.atan(b/a);
    if(a<0){
      turn+=2.3561944901923;
    }
    return turn;
  }

  public boolean isMove()
  {
    return move;
  }
  public float getHealth()
  {
    return health;
  }
  public float getMaxHealth()
  {
    return maxHealth;
  }
}