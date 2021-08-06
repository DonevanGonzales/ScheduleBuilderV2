package javas;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class EditTutor implements Initializable {
    public TextField fntf;
    public TextField lntf;
    public TextField nidtf;
    public ListView<String> listview;
    public TextField searchTF;

    public File database;
    public  Tutor tutor;

    Alert alert = new Alert(Alert.AlertType.NONE);

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

    public void SaveChangesBtn(ActionEvent actionEvent) throws IOException {
        if (!(fntf.getText().trim().isEmpty() || lntf.getText().trim().isEmpty())) {
            if ( ! fntf.getText().toLowerCase().matches(".*[^a-z].*") && ! lntf.getText().toLowerCase().matches(".*[^a-z].*") ) {
                if (database.isFile() && database.canRead()) {
                    replaceLines();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("File could not be read.");
                    alert.showAndWait();
                }
            } else {
                String content = "Please do not use any special characters.";
                alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.YES);
                alert.showAndWait();
            }

        } else {
            String content = "Please enter something into each text field";
            alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.YES);
            alert.showAndWait();
        }
    }


    public void setInfo(MouseEvent mouseEvent) throws IOException {
        FileReader fr = new FileReader(database);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        String[] words;  //Initialize the word Array
        String s;

        try{
            while((s = br.readLine()) != null){
                words = s.split(",");
                if(listview.getSelectionModel().getSelectedItem().contains(words[2])){
                    words = s.split(",");
                    fntf.setText(words[0]);
                    lntf.setText(words[1]);
                    nidtf.setText(words[2]);
                }
            }
            fntf.setDisable(false);
            lntf.setDisable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// read file one line at a time
// replace line as you read the file and store updated lines in StringBuffer
// overwrite the file with the new lines
    public void replaceLines() throws IOException {
            try {
                // input the (modified) file content to the StringBuffer "input"
                BufferedReader file = new BufferedReader(new FileReader(database));
                StringBuilder inputBuffer = new StringBuilder();
                String line;
                String[] words;

                while((line = file.readLine()) != null){
                    words = line.split(",",5);
                    if(listview.getSelectionModel().getSelectedItem().contains(words[2])){
                        line = fntf.getText().trim() + "," + lntf.getText().trim() + "," + nidtf.getText().trim() + "," + words[3] + "," + words[4];
                    }
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
                file.close();

                // write the new string with the replaced line OVER the same file
                FileOutputStream fileOut = new FileOutputStream(database);
                fileOut.write(inputBuffer.toString().getBytes());
                fileOut.close();

                updateList();
                String content = "Successfully edited " + fntf.getText().trim() + " " + lntf.getText().trim() + ": " + nidtf.getText().trim();
                alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES);
                alert.showAndWait();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                String content = "Something went wrong. Please select the tutor you wanted to edit again.";
                alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.YES);
                alert.showAndWait();
            }
    }

    public void clearText(ActionEvent actionEvent) {
    }
}
