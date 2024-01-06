/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NChat extends JFrame {

    public static final int PORT_RECEIVE =4444;
    public static final int PORT_SEND = 4444;
    private DatagramSocket sockSend;
    private DatagramPacket packSend;
    private Map<InetAddress,String> mapUsers=new HashMap<InetAddress,String>();

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new NChat();
    }

    public NChat() {
        super("NChat - A LAN chatting application");
        initComponents();
        addActionListeners();
        try {
            sockSend = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(NChat.class.getName()).log(Level.SEVERE, null, ex);
        }
        new clsReceive(txtAllMsgs).start();
    }

    private void addActionListeners() {
        buttDisplayName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String name = JOptionPane.showInputDialog("Enter your display name");
                if(name.contains(" ")){
                    JOptionPane.showMessageDialog(null, "Display Name cannot contain spaces");
                    return;
                }
                lblDisplayName.setText(name.trim());
            }
        });

        buttSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String msg = "M" + txtMsg.getText().trim();
                    System.out.println("Send : " + msg);
                    byte[] buf = clsFunctions.strToByteArray(msg);

                    String strIp = lstAllUsers.getSelectedItem().split(" ")[1];
                    InetAddress temp = InetAddress.getByName(strIp);
                    packSend = new DatagramPacket(buf, buf.length, temp, PORT_SEND);
                    sockSend.send(packSend);

                    txtAllMsgs.setText(txtAllMsgs.getText() + "\n" +lblDisplayName.getText()+" : "+txtMsg.getText().trim());
                    System.out.println("sended to client IP : " + temp + " on port " + PORT_SEND);
                    txtMsg.setText("");
                } catch (UnknownHostException ex) {
                    Logger.getLogger(NChat.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(NChat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        buttRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String msg = "R" + lblDisplayName.getText().trim() + " " + InetAddress.getLocalHost().getHostAddress();
                    System.out.println("Refresh : " + msg);
                    byte[] buf = clsFunctions.strToByteArray(msg);

                    packSend = new DatagramPacket(buf, buf.length, InetAddress.getByName("255.255.255.255"), PORT_SEND);
                    sockSend.send(packSend);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(NChat.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(NChat.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    class clsReceive extends Thread {

        private DatagramSocket sockRecv;
        private DatagramPacket packRecv;
        private JTextArea txt;
        private byte[] buffer;

        public clsReceive(JTextArea txtA) {
            System.out.println("receive thread created");
            this.txt = txtA;
            packRecv = new DatagramPacket(new byte[8000], 8000);
            try {
                sockRecv = new DatagramSocket(PORT_RECEIVE);
            } catch (SocketException ex) {
                Logger.getLogger(clsReceive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void run() {
            while (true) {
                try {
                    System.out.println("waiting for packet");
                    sockRecv.receive(packRecv);
                    buffer = packRecv.getData();

                    if (buffer[0] == 'M') {
                        String msg=clsFunctions.byteArrayToString(buffer);
                        String username=mapUsers.get(packRecv.getAddress());
                        txt.setText(txt.getText() + "\n" +username+" : "+ msg.substring(1));
                        System.out.println("Message");
                    }
                    else if (buffer[0] == 'R') {
//                        System.out.println("stuck here");
                        String decode=clsFunctions.byteArrayToString(buffer);
                        String user=decode.split(" ")[0].substring(1);
                        String ip=decode.split(" ")[1];
                        InetAddress userIp=InetAddress.getByName(ip);
                        if(userIp.equals(InetAddress.getLocalHost())){
                            continue;
                        }

                        mapUsers.put(userIp, user);
                        lstAllUsers.removeAll();
                        for(Map.Entry<InetAddress,String> entry: mapUsers.entrySet()){
                            lstAllUsers.add(entry.getValue()+" "+entry.getKey().toString().substring(1)) ;
                        }

                        String msg = "S" + lblDisplayName.getText().trim() + " " + InetAddress.getLocalHost().getHostAddress();
                        System.out.println("Refresh : " + msg);
                        byte[] buf = clsFunctions.strToByteArray(msg);
                        packSend = new DatagramPacket(buf, buf.length, packRecv.getAddress(), PORT_SEND);
                        sockSend.send(packSend);
                    }
                    else if (buffer[0] == 'S') {
                        String decode=clsFunctions.byteArrayToString(buffer);
                        String user=decode.split(" ")[0].substring(1);
                        String ip=decode.split(" ")[1];

                        InetAddress userIp=InetAddress.getByName(ip);
                        mapUsers.put(userIp, user);
                        lstAllUsers.removeAll();
                        for(Map.Entry<InetAddress,String> entry: mapUsers.entrySet()){
                            lstAllUsers.add(entry.getValue()+" "+entry.getKey().toString().substring(1)) ;
                        }
                        System.out.println("Response");
                    }
                    else if (buffer[0] == 'E') {
                        System.out.println("End");
                    }
                    System.out.println("got the packet");
                } catch (IOException ex) {
                    Logger.getLogger(clsReceive.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            System.out.println("receive thread finished");
        }
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        pnTop.add(buttDisplayName);
        lblDisplayName.setPreferredSize(new Dimension(200, 25));
        pnTop.add(lblDisplayName);
        pnTop.add(buttRefresh);
        this.add(pnTop, BorderLayout.PAGE_START);

        scpAllMsgs.setViewportView(txtAllMsgs);
        scpAllUsers.setViewportView(lstAllUsers);
        scpAllMsgs.setPreferredSize(new Dimension(380, 200));
        scpAllUsers.setPreferredSize(new Dimension(180, 200));
        pnCenter.add(scpAllMsgs);
        pnCenter.add(scpAllUsers);
        this.add(pnCenter, BorderLayout.CENTER);

        GridBagLayout gblBottom = new GridBagLayout();
        pnBottom.setLayout(gblBottom);
        GridBagConstraints gbclblMsg = new GridBagConstraints();
        gbclblMsg.gridy = 0;
        gbclblMsg.gridwidth = 3;
        lblMsg.setPreferredSize(new Dimension(380, 30));
        pnBottom.add(lblMsg, gbclblMsg);

        GridBagConstraints gbctxtMsg = new GridBagConstraints();
        gbctxtMsg.gridy = 1;
        gbctxtMsg.gridwidth = 3;
        gbctxtMsg.gridheight = 2;
        gbctxtMsg.insets = new Insets(0, 0, 10, 0);//, WIDTH, WIDTH, WIDTH)
        txtMsg.setPreferredSize(new Dimension(380, 60));
        pnBottom.add(txtMsg, gbctxtMsg);

        GridBagConstraints gbcbuttSend = new GridBagConstraints();
        gbcbuttSend.gridy = 1;
        gbcbuttSend.gridx = 3;
        gbcbuttSend.gridheight = 3;
        gbcbuttSend.insets = new Insets(0, 10, 10, 0);//, WIDTH, WIDTH, WIDTH)
        buttSend.setPreferredSize(new Dimension(180, 60));
        pnBottom.add(buttSend, gbcbuttSend);
        this.add(pnBottom, BorderLayout.PAGE_END);

        this.setSize(600, 370);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screen.getWidth() - this.getWidth());
        int y = (int) (screen.getHeight() - this.getHeight());
        this.setLocation(x / 2, y / 2);
        this.setVisible(true);
    }
    private JPanel pnTop = new JPanel(new FlowLayout());
    private JButton buttDisplayName = new JButton("Display Name ");
    private JLabel lblDisplayName = new JLabel("Default");
    private JButton buttRefresh = new JButton("Refresh List");
    private JPanel pnCenter = new JPanel(new FlowLayout());
    private JTextArea txtAllMsgs = new JTextArea();
    private JScrollPane scpAllMsgs = new JScrollPane();
    private List lstAllUsers = new List();
    private JScrollPane scpAllUsers = new JScrollPane();
    private JPanel pnBottom = new JPanel(new FlowLayout());
    private JLabel lblMsg = new JLabel("Message : ");
    private JTextField txtMsg = new JTextField();
    private JButton buttSend = new JButton("Send Message");
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
