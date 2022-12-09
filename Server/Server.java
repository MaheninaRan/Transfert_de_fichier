package Server;

import java.awt.Component;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.awt.*;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

import file.MyFile;

public class Server {
    static ArrayList<MyFile> myFiles = new ArrayList<>();

    public static void main(String[] args) {
        int fileId = 0;

        JFrame jFrame = new JFrame("Server");
        jFrame.setSize(400, 400);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        JTextField textFieldPort=new JTextField("1766");
        textFieldPort.setBounds(200,0,70,20);
        
        JLabel port=new JLabel("Port : ");
        port.setBounds(100, 20, 50, 50);

        JButton connect=new JButton("Connect");
        connect.setBounds(280,40,85,25);
        
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setVerticalScrollBarPolicy(jScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel jlTitle = new JLabel("File Receiver");
        jlTitle.setFont(new Font("Arial", Font.BOLD, 20));

      
        jFrame.add(connect);
        jFrame.setFocusable(false);
        jFrame.add(jlTitle);
        jFrame.add(jScrollPane);
        jScrollPane.add(port);
        jScrollPane.add( textFieldPort);
        jFrame.setVisible(true);
        

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println(textFieldPort.getText());
            }
        });


        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(1766);
                Socket socket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                int fileNameLength = dataInputStream.readInt();

                if (fileNameLength > 0) {
                    byte[] filenameBytes = new byte[fileNameLength];
                    dataInputStream.readFully(filenameBytes, 0, filenameBytes.length);
                    String fileName = new String(filenameBytes);

                    int fileContentLength = dataInputStream.readInt();

                    if (fileContentLength > 0) {
                        byte[] fileContentBytes = new byte[fileContentLength];
                        dataInputStream.readFully(fileContentBytes, 0, fileContentLength);

                        JPanel jpFileRow = new JPanel();
                        jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.Y_AXIS));

                        JLabel jlFileName = new JLabel(fileName);
                        jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
                        jlFileName.setBorder(new EmptyBorder(10, 0, 10, 0));

                        if (getFileExtension(fileName).equalsIgnoreCase("txt")) {
                            jpFileRow.setName(String.valueOf(fileId));
                            jpFileRow.addMouseListener(getMyMouseListener());

                            jpFileRow.add(jlFileName);
                            jPanel.add(jpFileRow);
                            jFrame.validate();
                         } else {
                            jpFileRow.setName(String.valueOf(fileId));
                            jpFileRow.addMouseListener(getMyMouseListener());

                            jpFileRow.add(jlFileName);
                            jPanel.add(jpFileRow);

                            jFrame.validate();
                        }

                        myFiles.add(new MyFile(fileId, fileName, fileContentBytes, getFileExtension(fileName)));
                    }


                }
            }catch (Exception error) {
                error.printStackTrace();
            }


        }
    }

    public static JFrame createFrame(String fileName, byte[] fileData, String fileExtension) {
        JFrame jFrame = new JFrame("Download");
        jFrame.setSize(500,400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        JLabel jlTitle = new JLabel(fileName);
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));

        JLabel jlPrompt = new JLabel("");
        jlPrompt.setFont(new Font("Arial", Font.BOLD, 25));
        jlPrompt.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton jbYes = new JButton();
        jbYes.setBackground(Color.green);
        jbYes.setFont(new java.awt.Font("Arial", Font.BOLD, 30));  
        jbYes.setText("<html><color=white><b>Download</b></font></html>");
        jbYes.setBounds(150, 50, 150, 75);
        

        JButton jbNo = new JButton();
        jbNo.setBackground(Color.red);

        jbNo.setFont(new java.awt.Font("Arial", Font.BOLD, 30));  
        jbNo.setText("<html><color=white><b>Cancel</b></font></html>");
        jbNo.setBounds(150, 120, 150, 75);

        JLabel jlFileContent = new JLabel();
        jlFileContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel jpButtons = new JPanel();
        jpButtons.setBorder(new EmptyBorder(20, 0, 10, 0));
        jpButtons.add(jbYes);
        jpButtons.add(jbNo);

        if (fileExtension.equalsIgnoreCase("txt")) {
            jlFileContent.setText("<html>" + new String(fileData)+"</html>");
        } else {
            jlFileContent.setIcon(new ImageIcon(fileData));
        }

        jbYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File fileToDownloand = new File(fileName);

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileToDownloand);

                    fileOutputStream.write(fileData);
                    fileOutputStream.close();

                    jFrame.dispose();
                } catch (Exception erorr) {
                    erorr.printStackTrace();
                }
            }
        });

        jbNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });

        jPanel.add(jlTitle);
        jPanel.add(jlPrompt);
        jPanel.add(jlFileContent);
        jPanel.add(jpButtons);

        jFrame.add(jPanel);
        jFrame.setResizable(false);
        return jFrame;
    }

    public static MouseListener getMyMouseListener() {
        return new MouseListener() {
            @Override 
            public void mouseClicked(MouseEvent e) {

                JPanel jPanel = (JPanel) e.getSource();

                int fileId = Integer.parseInt(jPanel.getName());

                for (MyFile myFile: myFiles) {
                    if (myFile.getId() == fileId) {
                        JFrame jfPreview = createFrame(myFile.getName(), myFile.getData(), myFile.getFileextension());
                        jfPreview.setVisible(true);
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        };
    }

    public static String getFileExtension(String filename) {
        int i = filename.lastIndexOf('.');
        
        if (i>0) {
            return filename.substring(i + 1);
        } else {
            return "No extension found";
        }
    }
}