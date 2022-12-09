package graphique;
import listener.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Component;
import java.awt.Font;
import java.awt.*;
import java.util.*;
import java.io.*;
public class Graphique extends JPanel{
File[] fileToSend =new File[1];
JButton buttonPan;
JButton buttonSend;
JButton ButChoseFile;
JLabel titre;
JLabel Filename;
JTextField textFieldPort;
JTextField textFieldHost;
JButton connect;
Listener lis=new Listener(this);
public File[] getFileToSend(){return this.fileToSend;}
public JButton getButPan(){return this.buttonPan;}
public JButton getButSend(){return this.buttonSend;}
public JButton getButChoseFile(){return this.ButChoseFile;}
public JButton getButConnect(){return this.connect;}
public JLabel getFileName(){return this.Filename;}
public JLabel getTitre(){return this.titre;}
public JTextField getJTextFieldPort(){return this.textFieldPort;}
public JTextField getJTextFieldHost(){return this.textFieldHost;}



    public Graphique() {
       this.AllGrp();
    }

    public void LabelGrp(){
        titre= new JLabel();
        titre.setText("Titre");
        titre.setFont(new Font("Arial" ,Font.BOLD,20));
        titre.setBorder( new EmptyBorder(20,0,10,0));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(titre);
        Filename=new JLabel();
        Filename.setText("Chose a file");
        Filename.setFont(new Font("Arial" ,Font.BOLD,20));
        Filename.setBounds(140, 250, 400, 40);
        this.add(Filename);
    }

    public void ButtonPan(){
        this.buttonPan=new JButton();                               
        this.buttonPan.setText("ButonPan");
    }

    public void ButtonSend(){
        this.buttonSend=new JButton();     
        this.buttonSend.setFont((new Font("Arial" ,Font.BOLD,16)));
        this.buttonSend.setText("SendFile");
        
        this.buttonSend.setBounds(125, 170, 150, 50);
        this.buttonSend.addActionListener(lis);
        this.add(this.buttonSend);
    }
    public void ButtonChoseFile(){
        Icon icon = new ImageIcon("Explorateur.png");
        this.ButChoseFile=new JButton();      
        this.ButChoseFile.setFont((new Font("Arial" ,Font.BOLD,16)));                         
        this.ButChoseFile.setText("ChoseFile");
        this.ButChoseFile.setBounds(125, 100, 150, 50);
        this.ButChoseFile.addActionListener(lis);
        this.add(this.ButChoseFile);
    }
    public void Port_Host(){
        this.textFieldPort=new JTextField();
        this.textFieldHost=new JTextField();
        this.textFieldHost.setText("localhost");
        this.textFieldPort.setText("1766");
        JLabel port=new JLabel("Port : ");
        JLabel host=new JLabel("Host : ");
        port.setBounds(60, 27, 50, 50);
        host.setBounds(60, 2, 50, 50);
        this.textFieldPort.setBounds(155,45,90,20);
        this.textFieldHost.setBounds(130,20,130,20);
        this.add(textFieldPort);
        this.add(textFieldHost);
        this.add(port);
        this.add(host);
    }
    public void Connect(){
        

        
        this.connect=new JButton();
        this.connect.setBackground(Color.GREEN);
        this.connect.setFont(new java.awt.Font("Arial", Font.BOLD, 15));  
        this.connect.setText("<html><color=white><b>Connect</b></font></html>");
        this.connect.setBounds(270,30,85,25);
        this.connect.addActionListener(lis);
        this.add(connect);
    }
    public void AllGrp(){
        this.setLayout(null);
        this.ButtonChoseFile();
        this.ButtonPan();
        this.ButtonSend();
        this.LabelGrp();
        this.Port_Host();
        this.Connect();
    }
}