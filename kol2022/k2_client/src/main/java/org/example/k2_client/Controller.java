package org.example.k2_client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    @FXML
    private TextField filterField;

    @FXML
    private ListView<String> wordList;

    @FXML
    private Label wordCountLabel;

    private List<String> history = new ArrayList<>();

    @FXML
    public void onFilterChanged(){          //dopisane do fxml
        String userText = filterField.getText();

        wordList.getItems().clear();

        for(String s : history){
            String[] elements = s.split(" ");
            if(elements[1].startsWith(userText)){
                wordList.getItems().add(s);
            }
        }
        Collections.sort(wordList.getItems(), (str1, str2) -> {             //sortowanie
            String word1 = str1.split(" ")[1];
            String word2 = str2.split(" ")[1];

            return word1.compareTo(word2);
        });

    }

    public void receiveWord(String entry){
        Platform.runLater( () ->{
            String userText = filterField.getText();
            String[] elements = entry.split(" ");
            if (elements[1].startsWith(userText) || userText.isEmpty()) {
                wordList.getItems().add(entry);
            }
            history.add(entry);
            int count = history.size();
            wordCountLabel.setText(String.valueOf(count));
            Collections.sort(wordList.getItems(), (str1, str2) -> {
                String word1 = str1.split(" ")[1];
                String word2 = str2.split(" ")[1];

                return word1.compareTo(word2);
            });
        });
    }


}
