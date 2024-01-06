/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientOne extends JFrame {

    public static final int PORT_RECEIVE = 5555;
    public static final int PORT_SEND = 6666;

    public static void main(String[] args) {
        new ClientOne();
    }
    private DatagramSocket sockSend;
    private DatagramPacket packSend;

    public ClientOne() {
        initComponents();
        new clsReceive(txtA).start();

        try {
            sockSend = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(ClientOne.class.getName()).log(Level.SEVERE, null, ex);
        }

        buttSend.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                try {
                    InetAddress temp = InetAddress.getByName(txtIp.getText().trim());
                    byte[] buf = clsFunctions.strToByteArray(txtMsg.getText().trim());
                    packSend = new DatagramPacket(buf, buf.length, temp, PORT_SEND);
                    sockSend.send(packSend);
                    System.out.println("sended to client IP : "+temp+" on port "+PORT_SEND);
                    txtIp.setText("");
                    txtMsg.setText("");
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ClientOne.class.getName()).log(Level.SEVERE, null, ex);
                }catch (IOException ex) {
                    Logger.getLogger(ClientOne.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }

    public void initComponents() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbctxtA = new GridBagConstraints();
        gbctxtA.gridy = 0;
        gbctxtA.gridheight = 3;
        gbctxtA.gridwidth = 2;
        scpA.setPreferredSize(new Dimension(380, 100));
        scpA.setViewportView(txtA);
        txtA.setEnabled(false);
        this.add(scpA, gbctxtA);

        GridBagConstraints gbclblIp = new GridBagConstraints();
        gbclblIp.gridy = 3;
        gbclblIp.gridwidth = 1;
        gbclblIp.insets = new Insets(20, 0, 0, 0);
        lblIp.setPreferredSize(new Dimension(100, 30));
        this.add(lblIp, gbclblIp);

        GridBagConstraints gbctxtIp = new GridBagConstraints();
        gbctxtIp.gridy = 3;
        gbctxtIp.gridwidth = 2;
        gbctxtIp.insets = new Insets(20, 0, 0, 0);
        txtIp.setPreferredSize(new Dimension(280, 30));
        this.add(txtIp, gbctxtIp);

        GridBagConstraints gbclblMsg = new GridBagConstraints();
        gbclblMsg.gridy = 4;
        gbclblMsg.gridwidth = 1;
        gbclblMsg.gridheight = 2;
        gbclblMsg.insets = new Insets(20, 0, 0, 0);
        lblMsg.setPreferredSize(new Dimension(100, 30));
        this.add(lblMsg, gbclblMsg);

        GridBagConstraints gbctxtMsg = new GridBagConstraints();
        gbctxtMsg.gridy = 4;
        gbctxtMsg.gridwidth = 2;
        gbctxtMsg.gridheight = 2;
        gbctxtMsg.insets = new Insets(20, 0, 0, 0);
        scpMsg.setViewportView(txtMsg);
        scpMsg.setPreferredSize(new Dimension(280, 80));
        this.add(scpMsg, gbctxtMsg);

        GridBagConstraints gbcbuttSend = new GridBagConstraints();
        gbcbuttSend.gridy = 6;
        gbcbuttSend.gridwidth = 2;
        gbcbuttSend.gridx = 1;
        gbcbuttSend.insets = new Insets(20, 0, 0, 0);
        buttSend.setPreferredSize(new Dimension(200, 50));
        this.add(buttSend, gbcbuttSend);

        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    private JTextArea txtA = new JTextArea();
    private JScrollPane scpA = new JScrollPane();
    private JLabel lblIp = new JLabel("Ip Address : ");
    private JTextField txtIp = new JTextField();
    private JLabel lblMsg = new JLabel("Message : ");
    private JTextArea txtMsg = new JTextArea();
    private JScrollPane scpMsg = new JScrollPane();
    private JButton buttSend = new JButton("Send Message");

    class clsReceive extends Thread {

        private DatagramSocket sockRecv;
        private DatagramPacket packRecv;
        private JTextArea txt;

        public clsReceive(JTextArea txtA) {
            this.txt = txtA;
            packRecv = new DatagramPacket(new byte[8000], 8000);
            try {
                sockRecv = new DatagramSocket(PORT_RECEIVE);
            } catch (SocketException ex) {
                Logger.getLogger(clsReceive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void run() {
            System.out.println("receive thread started");
            while (true) {
                try {
                    System.out.println("waiting for packet");
                    sockRecv.receive(packRecv);
                    System.out.println("got the packet");
                    txt.setText(txt.getText() + "\n" + clsFunctions.byteArrayToString(packRecv.getData()));
                } catch (IOException ex) {
                    Logger.getLogger(clsReceive.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            System.out.println("receive thread finished");
        }
    }
}

class clsFunctions {

    public static byte[] strToByteArray(String message) {
        char c[] = message.toCharArray();
        byte b[] = new byte[8000];

        int i;
        for (i = 0; i < c.length; i++) {
            b[i] = (byte) c[i];
        }
        b[i] = (byte) '\0';
        return b;
    }

    public static String byteArrayToString(byte[] b) {
        int i = 0;
        while ((char) b[i] != '\0') {
            i++;
        }           //'\n'=10

        char data[] = new char[i];
        for (int x = 0; x < i; x++) {
            data[x] = (char) b[x];
            //System.out.println("data : "+ data[x]);
        }
        //System.out.println("loop over");
        String message = new String(data);
        return message;
    }
}
