package javas;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RemoveTutor implements Initializable {
    private File database;

    @FXML
    public Label fnlb;
    public Label lnlb;
    public Label nidlb;
    public ListView<String> listview;
    public TextField searchTF;
    private Tutor tutor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = new File("src/database.csv");
        try {
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        searchTF.textProperty().addListener((ChangeListener) (observable, oldVal, newVal) -> search((String) oldVal, (String) newVal));
    }

    public void search(String oldVal, String newVal) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            listview.setItems(tutor.names);
        }
        String value = newVal.toUpperCase();
        ObservableList<String> subentries = FXCollections.observableArrayList();
        for (String entry : listview.getItems()) {
            boolean match = true;
            String entryText = entry;
            if (!entryText.toUpperCase().contains(value)) {
                match = false;
            }
            if (match) {
                subentries.add(entryText);
            }
        }
        listview.setItems(subentries);
    }

    public void updateList() throws IOException {
        tutor = new Tutor();
        listview.setItems(tutor.names.sorted());
    }

    public void setInfo(MouseEvent mouseEvent) throws IOException {
        FileReader fr = new FileReader(database);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String[] words;  //Initialize the word Array
        String s;

        try {
            while((s = br.readLine()) != null){
                words = s.split(",",5);
                if(listview.getSelectionModel().getSelectedItem().contains(words[2])){
                    words = s.split(",");
                    fnlb.setText(words[0]);
                    lnlb.setText(words[1]);
                    nidlb.setText(words[2]);
                    System.out.println(words[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RemoveTutorBtn(ActionEvent actionEvent) throws IOException {
        String selection = lnlb.getText() + " " + fnlb.getText() + " " + nidlb.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selection + " ?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            deleteTutor();
            updateList();
        }
    }

    public void deleteTutor() throws IOException {
        try {
            // input the (modified) file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(database));
            StringBuilder inputBuffer = new StringBuilder();
            String line;
            String[] words;

            while((line = file.readLine()) != null){
                words = line.split(",");
                if(!listview.getSelectionModel().getSelectedItem().contains(words[2])){
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
            }
            file.close();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(database);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

            fnlb.setText(null);
            lnlb.setText(null);
            nidlb.setText(null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void clearText(ActionEvent actionEvent) {
    }
}
