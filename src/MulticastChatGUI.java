import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

class MulticastChatGUI {
    private JPanel panel1;
    private JTextField HandleName;
    private JTextArea Chatbox;
    private JTextField messageBox;
    private JButton JOINCHATButton;
    private JButton SENDMESSAGEButton;
    private JButton LEAVECHATButton;
    private JButton EXITButton;
    private JTextField groupIP;
    private JTextField port;
    private JLabel chatHandlerNameLabel;
    private JLabel messageLabel;
    private JLabel portLabel;
    private JLabel chatGroupIPLabel;
    private MulticastListener multicastListener;
    private MulticastSender multicastSender;
    private int currentNumMessages;
    private ArrayList<String> messagesArray;

    public MulticastChatGUI() {
        messagesArray = new ArrayList<String>();
        currentNumMessages = 0;
        Chatbox.setText("Welcome please hit join to begin chatting\n");
        messagesArray.add("Welcome please hit join to begin chatting\n");
        updateSystemListener updater = new updateSystemListener();
        Timer updateSystem = new Timer(700, updater);
        updateSystem.start();
        multicastListener = new MulticastListener(groupIP.getText(), Integer.parseInt(port.getText()),
                HandleName.getText(), currentNumMessages, new ArrayList<String>());
        multicastSender = new MulticastSender();
        JOINCHATButton.addActionListener(e -> {
            JOINCHATButton.setEnabled(false);
            String groupName = groupIP.getText();
            String avatar = HandleName.getText();
            int portNum = Integer.parseInt(port.getText());
            multicastSender.sendMessage(avatar + " is connected to chat!", groupName, portNum);
            multicastListener = new MulticastListener(groupName, portNum, avatar, currentNumMessages, messagesArray);
            multicastListener.start();
            currentNumMessages = multicastListener.amountOfMessages();
            LEAVECHATButton.setEnabled(true);
            SENDMESSAGEButton.setEnabled(true);
            groupIP.setEnabled(false);
            port.setEnabled(false);
            HandleName.setEnabled(false);
        });
        LEAVECHATButton.addActionListener(e -> {
            LEAVECHATButton.setEnabled(false);
            try {
                multicastListener.beginShutdownSequence();
                String mess = HandleName.getText() + " is disconnecting from chat!";
                multicastListener.addMessage(mess);
                multicastSender.sendMessage(mess, groupIP.getText(), Integer.parseInt(port.getText()));
                multicastListener.join(1000);
            } catch (Exception err) {
                err.printStackTrace();
            }
            JOINCHATButton.setEnabled(true);
            SENDMESSAGEButton.setEnabled(false);
            groupIP.setEnabled(true);
            port.setEnabled(true);
            HandleName.setEnabled(true);
        });
        SENDMESSAGEButton.addActionListener(e -> {
            String mess = HandleName.getText() + ": " + messageBox.getText();
            multicastListener.addMessage(mess);
            multicastSender.sendMessage(mess, groupIP.getText(), Integer.parseInt(port.getText()));
            messageBox.setText("");
        });
        EXITButton.addActionListener(e -> {
            if (LEAVECHATButton.isEnabled()) {
                multicastListener.beginShutdownSequence();
                try {
                    multicastListener.join(500);
                } catch (InterruptedException err) {
                    err.printStackTrace();
                }
                String mess = HandleName.getText() + " has exited chat!";
                multicastSender.sendMessage(mess, groupIP.getText(), Integer.parseInt(port.getText()));
            }
            System.exit(0);
        });
        messageBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10 && LEAVECHATButton.isEnabled()) {
                    SENDMESSAGEButton.doClick();
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 20, 80, 20), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHEAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel2.add(panel3, gbc);
        chatHandlerNameLabel = new JLabel();
        chatHandlerNameLabel.setText("Chat Handler Name: ");
        panel3.add(chatHandlerNameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        HandleName = new JTextField();
        HandleName.setBackground(new Color(-3354929));
        HandleName.setColumns(10);
        HandleName.setForeground(new Color(-16777216));
        HandleName.setText("Unknown User");
        panel3.add(HandleName, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel4, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel4.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        Chatbox = new JTextArea();
        Chatbox.setBackground(new Color(-3354929));
        Chatbox.setEditable(false);
        Chatbox.setForeground(new Color(-12500671));
        Chatbox.setText("");
        scrollPane1.setViewportView(Chatbox);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, 10));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 10;
        panel2.add(panel5, gbc);
        messageLabel = new JLabel();
        messageLabel.setText("Message");
        panel5.add(messageLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        messageBox = new JTextField();
        messageBox.setBackground(new Color(-3354929));
        messageBox.setForeground(new Color(-16777216));
        panel5.add(messageBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel6, gbc);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        JOINCHATButton = new JButton();
        JOINCHATButton.setText("JOIN CHAT");
        panel7.add(JOINCHATButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SENDMESSAGEButton = new JButton();
        SENDMESSAGEButton.setEnabled(false);
        SENDMESSAGEButton.setText("SEND MESSAGE");
        panel7.add(SENDMESSAGEButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LEAVECHATButton = new JButton();
        LEAVECHATButton.setEnabled(false);
        LEAVECHATButton.setText("LEAVE CHAT");
        panel7.add(LEAVECHATButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        EXITButton = new JButton();
        EXITButton.setText("EXIT");
        panel7.add(EXITButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel8, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        chatGroupIPLabel = new JLabel();
        chatGroupIPLabel.setText("Chat Group IP");
        panel8.add(chatGroupIPLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        groupIP = new JTextField();
        groupIP.setBackground(new Color(-3354929));
        groupIP.setForeground(new Color(-16777216));
        groupIP.setText("224.0.0.1");
        panel8.add(groupIP, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        portLabel = new JLabel();
        portLabel.setText("Port   ");
        panel8.add(portLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        port = new JTextField();
        port.setBackground(new Color(-3354929));
        port.setForeground(new Color(-16777216));
        port.setText("4001");
        panel8.add(port, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    private class updateSystemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                messagesArray = multicastListener.getMessages();
                int numMessages = multicastListener.amountOfMessages();
                while (currentNumMessages != numMessages) {
                    String mess = messagesArray.get(currentNumMessages++);

                    Chatbox.setText(Chatbox.getText() + mess + "\n");

                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    public void startGui() {
        JFrame frame = new JFrame("Multicast Chat Room");
        frame.setContentPane(new MulticastChatGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
