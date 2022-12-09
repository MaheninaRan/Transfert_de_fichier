package listener;
import javax.swing.JFileChooser.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.*;
import graphique.*;
public class Listener implements ActionListener{
JFileChooser fileChooser;
Graphique grp;
FileInputStream fileInputStream;
DataOutputStream dataOutputStream;
String host;
String port;
public String getHost(){return this.host;}
public String getPort(){return this.port;}
public Listener(Graphique gg){
    this.grp=gg;
}
public Listener(){}

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==grp.getButConnect()){
            System.out.println("Connect");
            host=grp.getJTextFieldHost().getText();
            port=grp.getJTextFieldPort().getText();
            grp.getFileName().setBounds(45, 250, 400, 40);
            grp.getFileName().setText("You are Connected with port : " + port);
        }
        if (e.getSource()==grp.getButChoseFile()) {     
            System.out.println("FileChoss");
            fileChooser=new JFileChooser();
            fileChooser.setDialogTitle("Chose a file");
            fileChooser.setCurrentDirectory(new File("D:\\"));
            if(fileChooser.showOpenDialog(null)==fileChooser.APPROVE_OPTION){
                grp.getFileToSend()[0]=fileChooser.getSelectedFile();
                grp.getFileName().setText("File : " + grp.getFileToSend()[0].getName());
                System.out.println("File : " + grp.getFileToSend()[0].getName());
            }
        }
        if (e.getSource()==grp.getButSend()){                  ///SEND
            if (grp.getFileToSend()[0]==null){
                grp.getFileName().setFont(new java.awt.Font("Arial", Font.BOLD, 30));  
                grp.getFileName().setText("<html><color=red><b>Chose a file !!!</b></font></html>");
               
            } else {
                try {
                    if (host==null & port==null) {
                        grp.getFileName().setText("<html><color=red><b>Be sure to login !!!</b></font></html>");
                    } else {
                       int porti= Integer.valueOf(port);
                        fileInputStream=new FileInputStream(grp.getFileToSend()[0].getPath());
                        Socket socket=new Socket(host,porti);
                        dataOutputStream=new DataOutputStream(socket.getOutputStream());
                        String filename=grp.getFileToSend()[0].getName();
                        byte [] fileNameBytes=filename.getBytes();
                        byte [] fileContentBytes=new byte[(int)grp.getFileToSend()[0].length()];
                        fileInputStream.read(fileContentBytes);
                        dataOutputStream.writeInt(fileNameBytes.length);
                        dataOutputStream.write(fileNameBytes);
                        dataOutputStream.writeInt(fileContentBytes.length);
                        dataOutputStream.write(fileContentBytes);
                    }
                   
                   
                } catch (IOException e1){
                    e1.printStackTrace();
                }
                
            }
        } 
        
    }   
}