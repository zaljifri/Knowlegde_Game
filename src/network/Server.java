package network;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.DefaultCaret;
import static network.Server.sessionNo;
import static network.Server.serverLog;

public class Server extends javax.swing.JFrame {

    // the server constructor, some GUI modifications are done inside
    public Server() {
        initComponents();
        setLocationRelativeTo(null); //centering the gui frame
        // setting an image inside the frame
        ImageIcon ico = new ImageIcon("gameImage.jpg");
        Image img = ico.getImage();
        Image imgScale = img.getScaledInstance(label_img.getWidth(), label_img.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        label_img.setIcon(scaledIcon);  // set the game image to the label
        setIconImage(new ImageIcon("serverIcon.png").getImage()); //set the icon for the frame
        this.setTitle("Knowledge Game - Server"); //set frame title
        ScrollPanel.setBorder(null); // removing the scrollpane border
        // setting the the scrollbar
        ScrollPanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.LIGHT_GRAY;
                this.thumbDarkShadowColor = Color.gray;
                this.thumbHighlightColor = Color.LIGHT_GRAY;
                this.thumbLightShadowColor = Color.LIGHT_GRAY;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(Color.WHITE);
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(Color.WHITE);
                return button;
            }
        });

        // auto-scroll
        DefaultCaret caret = (DefaultCaret) serverLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        greenPanel = new javax.swing.JPanel();
        label_img = new javax.swing.JLabel();
        label_playerNo = new javax.swing.JLabel();
        label_connection = new javax.swing.JLabel();
        ScrollPanel = new javax.swing.JScrollPane();
        serverLog = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        greenPanel.setBackground(new java.awt.Color(166, 214, 202));

        label_img.setMaximumSize(new java.awt.Dimension(845, 400));

        javax.swing.GroupLayout greenPanelLayout = new javax.swing.GroupLayout(greenPanel);
        greenPanel.setLayout(greenPanelLayout);
        greenPanelLayout.setHorizontalGroup(
            greenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(greenPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(greenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_playerNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_img, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_connection, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                .addContainerGap())
        );
        greenPanelLayout.setVerticalGroup(
            greenPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, greenPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_playerNo, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_img, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(label_connection)
                .addContainerGap())
        );

        ScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollPanel.setToolTipText("");
        ScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        ScrollPanel.setHorizontalScrollBar(null);

        serverLog.setColumns(20);
        serverLog.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        serverLog.setLineWrap(true);
        serverLog.setRows(5);
        serverLog.setTabSize(12);
        serverLog.setWrapStyleWord(true);
        serverLog.setAutoscrolls(false);
        serverLog.setBorder(null);
        serverLog.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        serverLog.setDoubleBuffered(true);
        ScrollPanel.setViewportView(serverLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(greenPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(ScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ScrollPanel)
            .addComponent(greenPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ScrollPanel.getAccessibleContext().setAccessibleParent(ScrollPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    // static variables to be seen throughout the class
    static int sessionNo = 1;
    static int playerNo = 0;

    public static void main(String args[]) {
        /* Create and display the form */
        new Server().setVisible(true);

        // placing the connection initiation inside a try-catch block
        try {
            ServerSocket serverSocket = new ServerSocket(8888);  // creating TCP server socket 
            label_connection.setText(new Date() + ": Server started at socket " + serverSocket.getLocalPort() + "\n");

            // the server will always listens to clients requests 
            // each pair of clients will play withi the same thread
            while (true) {
                serverLog.append("Wait for players to join session " + sessionNo + "\n");

                Socket player1 = serverSocket.accept();  // accept player 1
                serverLog.append("\n" + new Date() + ": Player 1 joiend session " + sessionNo + "\n");
                serverLog.append("Player1's IP address " + player1.getInetAddress().getHostAddress() + "\n");
                new DataOutputStream(player1.getOutputStream()).writeInt(1);  // notify the client he is player 1

                Socket player2 = serverSocket.accept();  // accept player 2
                serverLog.append("\n" + new Date() + ": Player 2 joiend session " + sessionNo + "\n");
                serverLog.append("Player2's IP address " + player2.getInetAddress().getHostAddress() + "\n");
                new DataOutputStream(player2.getOutputStream()).writeInt(2);  // notify the client he is player 2

                serverLog.append("\n" + new Date() + ": Start a thread for session " + sessionNo++ + "\n\n");
                new Thread(new HandleASession(player1, player2)).start();  // start a new thread for session so the server can serve to other clients at the same time

            }

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    // GUI elements:
    // panels: ScrollPaanel, greenPanel
    // labels: label_connection, label_imag, label_playerNo
    // text area: serverLog
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPanel;
    private static javax.swing.JPanel greenPanel;
    private static javax.swing.JLabel label_connection;
    private static javax.swing.JLabel label_img;
    private static javax.swing.JLabel label_playerNo;
    public static javax.swing.JTextArea serverLog;
    // End of variables declaration//GEN-END:variables

}

class HandleASession implements Runnable {

    private Socket player1;
    private Socket player2;
    private DataInputStream fromPlayer1;
    private DataInputStream fromPlayer2;
    private DataOutputStream toPlayer1;
    private DataOutputStream toPlayer2;
    private int currentSession = sessionNo - 1;
    private boolean contin = true;
    private String playerAns;

    public HandleASession(Socket player1, Socket player2) throws FileNotFoundException {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        try {
            // I/O streams read from and write to clients
            fromPlayer1 = new DataInputStream(player1.getInputStream());
            toPlayer1 = new DataOutputStream(player1.getOutputStream());
            fromPlayer2 = new DataInputStream(player2.getInputStream());
            toPlayer2 = new DataOutputStream(player2.getOutputStream());

            // reading the questions from the file
            int i = 0;
            String[][] questionArray = new String[10][6];
            File QuizQ = new File("CPCS371QuizCh1.txt");
            Scanner readQuiz = new Scanner(QuizQ);

            while (readQuiz.hasNext()) {
                String questions = readQuiz.nextLine();  // the question along with its choices
                String answers = readQuiz.nextLine();  // the correct choice
                String[] qArray = questions.split("A. |B. |C. |D. ");
                for (int j = 0; j < qArray.length; j++) {
                    questionArray[i][j] = qArray[j];
                }
                questionArray[i][5] = answers;
                i++;
            }

            toPlayer1.writeInt(1);// inform player 1 that player 2 has joined

            int question = 0;
            int player1_score = 0;
            int player2_score = 0;
            int round = 1;
            serverLog.append("\n");
            // Start the game
            while (question < 10 && contin) {
//------------------------------player 1
                serverLog.append("Now it's player 1 turn! (session " + (currentSession) + " ) \n");
                toPlayer1.writeUTF("It is now your turn!");  // notify player 1 to play
                for (int j = 0; j < 5; j++) {
                    toPlayer1.writeUTF(questionArray[question][j]);  //send question to player 1
                }
                playerAns = fromPlayer1.readUTF();  // reading player 1's answer
                if (questionArray[question][5].equals(playerAns)) {  // if the answer is correct
                    toPlayer1.writeUTF("You answered question No." + ++question + " Correctly!");  // notify player 1 that his answer is correct
                    toPlayer2.writeUTF("Player 1 answered question No." + question + " Correctly!\n");  // notify player 2 that player 1's answer is correct
                    player1_score++;  // increment player 1's score
                } else {  // the answer is wrong
                    toPlayer1.writeUTF("WRONG Answer for question No." + ++question); // notify player 1 that his answer is wrong
                    toPlayer2.writeUTF("Player 1 WRONG Answer for question No." + question);  // notify player 2 that player 1's answer is wrong
                }
                toPlayer1.writeUTF("It is now player 2 turn!");  // notify player 1 that it is player 2's turn

//------------------------------player 2
                serverLog.append("Now it's player 2 turn! (session " + (currentSession) + " ) \n");
                toPlayer2.writeUTF("It is now your turn!");   // notify player 2 to play
                for (int j = 0; j < 5; j++) {
                    toPlayer2.writeUTF(questionArray[question][j]);  //send question to player 2
                }
                playerAns = fromPlayer2.readUTF();  // reading player 1's answer
                if (questionArray[question][5].equals(playerAns)) {  // if the answer is correct
                    toPlayer2.writeUTF("You answered question No." + ++question + " Correctly!");  // notify player 2 that his answer is correct
                    toPlayer1.writeUTF("Player 2 answered question No." + question + " Correctly!");  // notify player 1 that player 2's answer is correct
                    player2_score++;
                } else {
                    toPlayer2.writeUTF("WRONG Answer for question No." + ++question);  // notify player 2 that his answer is wrong
                    toPlayer1.writeUTF("Player 2 WRONG Answer for question No." + question);  // notify player 1 that player 2's answer is wrong
                }
                check_winner(player1_score, player2_score, round);  // checking if there is a winner/tie or to continue playing at the end of each round
                if (round < 5 && contin) {
                    toPlayer2.writeUTF("It is now player 1 turn!");
                }
                round++;
                toPlayer2.writeBoolean(contin);

            }

        } catch (Exception ex) {
            System.out.println(ex);

        }

    }
//------------------------------ checking the score of each player 

    public void check_winner(int p1_score, int p2_score, int round) throws IOException {
        // case 1
        if (java.lang.Math.abs(p1_score - p2_score) >= 3) {
            contin = false;
            if (p1_score > p2_score) {
                toPlayer1.writeUTF("You WON!");
                toPlayer2.writeUTF("Player 1 has WON!");
            } else {
                toPlayer2.writeUTF("You WON!");
                toPlayer1.writeUTF("Player 2 has WON!");
            }
            player1.close();  // close player 1's socket
            player2.close();  // close player 2's socket

            // case 2
        } else if (java.lang.Math.abs(p1_score - p2_score) == 2 && round >= 4) {
            contin = false;
            if (p1_score > p2_score) {
                toPlayer1.writeUTF("You WON!");
                toPlayer2.writeUTF("Player 1 has WON!");
            } else {
                toPlayer2.writeUTF("You WON!");
                toPlayer1.writeUTF("Player 2 has WON!");
            }
            player1.close();  // close player 1's socket
            player2.close();  // close player 2's socket

            // case 3
        } else if (round == 5) {
            contin = false;
            if (p1_score == p2_score) {
                toPlayer1.writeUTF("Game is over, no winner!");
                toPlayer2.writeUTF("Game is over, no winner!");
            } else {
                if (p1_score > p2_score) {
                    toPlayer1.writeUTF("You WON!");
                    toPlayer2.writeUTF("Player 1 has WON!");
                } else {
                    toPlayer2.writeUTF("You WON!");
                    toPlayer1.writeUTF("Player 2 has WON!");
                }

            }
            player1.close();  // close player 1's socket
            player2.close();  // close player 2's socket
        }
    }
};
