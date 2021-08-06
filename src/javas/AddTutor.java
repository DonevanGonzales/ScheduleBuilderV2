package javas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.*;

public class AddTutor implements Initializable {
    public TextField fntf;
    public TextField lntf;
    public TextField nidtf;

    public File database;

    public static Alert alert = new Alert(Alert.AlertType.NONE);
    public TextField searchTF;
    public ListView listview;
    public Tutor tutor;
    private boolean canAdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = new File("src/database.csv");
        try {
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateList() throws IOException {
        tutor = new Tutor();
        listview.setItems(tutor.names.sorted());
    }

    @FXML
    public void createTutor(ActionEvent actionEvent) throws IOException {
        canAdd = true;
        if (!(fntf.getText().trim().isEmpty() || lntf.getText().trim().isEmpty() || nidtf.getText().trim().isEmpty())) {
            if (!fntf.getText().toLowerCase().matches(".*[^a-z].*")
                    && !lntf.getText().toLowerCase().matches(".*[^a-z].*")) {
                if (database.isFile() && database.canRead()) {
                    FileReader fr = new FileReader(database); // Creation of File Reader object
                    BufferedReader br = new BufferedReader(fr); // Creation of BufferedReader object
                    FileWriter fw = new FileWriter(database, true);
                    // search for netID in database
                    String[] words = null; // Intialize the word Array
                    String s;
                    String input = nidtf.getText(); // Input word to be searched
                    input = input.toLowerCase();
                    int count = 0; // Intialize the word to zero
                    try {
                        // checks if netID exists in the database
                        while ((s = br.readLine()) != null) {
                            words = s.split(",");
                            if (words[2].equals(input)) {
                                alert.setAlertType(Alert.AlertType.WARNING);
                                alert.setContentText("A user with this NetID already exists");
                                alert.showAndWait();
                                count++;
                                canAdd = false;
                            }
                        }
                        if (canAdd) {
                            // add tutor to database
                            String content = "Are you sure this is correct? " + fntf.getText().trim() + " "
                                    + lntf.getText().trim() + " " + nidtf.getText().trim().toLowerCase();
                            alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.CANCEL);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.YES) {
                                if (count == 0) {
                                    String lineToAdd = fntf.getText().trim() + "," + lntf.getText().trim() + ","
                                            + nidtf.getText().trim().toLowerCase();
                                    String subjects = ",000000000000000000000000000000000000000000";
                                    String time = ",000000000000000000000000";
                                    fw.append(lineToAdd);
                                    fw.append(subjects);
                                    for (int i = 0; i < 6; i++) {
                                        fw.append(time);
                                    }
                                    fw.append("\n");
                                    fw.flush();
                                    fw.close();
                                    clearTextFields();
                                    updateList();
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("File could not be read.");
                    alert.showAndWait();
                }
            } else {
                String content = "Please do not use any special characters in the first and last name text fields.";
                alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.YES);
                alert.showAndWait();
            }
        } else {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("There are empty fields, please enter in all information");
            alert.showAndWait();
        } // at least on text field is empty
    }

    public void clearText(ActionEvent actionEvent) {
        clearTextFields();
    }

    private void clearTextFields() {
        fntf.clear();
        lntf.clear();
        nidtf.clear();
    }

    public void setInfo(MouseEvent mouseEvent) {
    }
}
