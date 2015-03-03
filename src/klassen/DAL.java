/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Stippler
 */
public class DAL 
{
  private static DAL dal;
  
  public static DAL getInstance()
  {
    if(dal==null)
    {
      dal=new DAL();
    }
    return dal;
  }
  
  public String[] loadText(String path) throws FileNotFoundException
  {
    File f=new File(path);
    Scanner sc=new Scanner(f);
    LinkedList<String> str=new LinkedList<>();
    
    while(sc.hasNext())
    {
      str.add(sc.nextLine());
    }
    sc.close();
    
    String parts[]=new String[str.size()];
    
    for (int i = 0; i < parts.length; i++) 
    {
      parts[i]=str.get(i);
    }
    return parts;
  }
}
