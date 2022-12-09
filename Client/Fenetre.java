package graphique;
import javax.swing.*;
import java.util.*;
import java.awt.*;
public class Fenetre extends JFrame {

    Graphique panel;
  public  Fenetre() throws Exception{
        panel=new Graphique();
        this.add(panel);
        this.setTitle("Socket-Client");
        this.setSize(400,400);
        this.setResizable(false);
        this.setBackground(Color.white);
       // this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
      this.setLocationRelativeTo(null);
    }   
}