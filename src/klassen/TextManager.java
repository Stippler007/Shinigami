/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen;

/**
 *
 * @author Stippler
 */
public class TextManager 
{
  private static TextManager textManager;
  private int position=0;
  private String[] text;
  private boolean texting=false;
  
  private TextManager() 
  {
    
  }
  
  public static TextManager getInstance()
  {
    if(textManager==null)
    {
      textManager=new TextManager();
    }
    return textManager;
  }
  public void setText(String[] text) 
  {
    this.text = text;
    this.texting=true;
    position=0;
  }
  public void nextText()
  {
    position+=4;
    if(position>=text.length)
    {
      text=null;
      texting=false;
    }
  }
  public String[] getText() 
  {
    return text;
  }

  public int getPosition()
  {
    return position;
  }
  
  public boolean isTexting() 
  {
    return texting;
  }
}
