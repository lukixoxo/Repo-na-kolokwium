package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ChatBox extends JFrame {
    JTextArea chatArea;
    JTextField messageField;
    JList<String> userList;
    JButton sendButton;
    PrintWriter out;
    DefaultListModel<String> model;

    public ChatBox(PrintWriter pr) {
        this.out = pr;
        setLayout(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(Color.lightGray);
        messageField = new JTextField(50);
        userList = new JList<>();
        userList.setBackground(Color.GRAY);
        sendButton = new JButton("WYŚLIJ");
        setTitle("Chat");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,500);
        setVisible(true);

        model = new DefaultListModel<>();
        userList.setModel(model);

        JPanel panel = new JPanel();
        panel.add(messageField);
        panel.add(sendButton);
        add(panel,BorderLayout.SOUTH);
        add(chatArea, BorderLayout.CENTER);
        add(userList, BorderLayout.EAST);

        //funkcjonalnosc:

        sendButton.addActionListener(e -> {
            sendMessage();
        });
        messageField.addActionListener(e ->{
            sendMessage();
        });


    }

    private void sendMessage(){
        String message = messageField.getText();
//        chatArea.append(message + '\n');
        out.println(message);
        messageField.setText("");
    }

    public static void main(String[] args){
    }

}
