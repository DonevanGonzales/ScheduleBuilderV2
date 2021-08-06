package javas;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public ListView<String> listview;
    public Tutor tutor;
    public Subjects subjects;

    public String name;
    public String netid;
    public ListView<String> listview0;
    public TextField tf0;
    public TextField tfTV;
    public TextField tfSchedule;
    public ListView<String> listviewSchedules;
    public Button genbtn;
    private String subject;
    public String[] times;
    public TextField searchTF;

    @FXML
    private BorderPane borderpaneSchedule;
    @FXML
    private BorderPane borderpaneStaff;
    @FXML
    private BorderPane borderpaneSubjects;
    @FXML
    private BorderPane borderpaneSubjectSchedules;

    private File database;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = new File("ScheduleBuilder\\src\\resources\\database.csv");
        
        try {
            updateList();
            updateTree();
        } catch (IOException e) {
            e.printStackTrace();
        }
        searchTF.textProperty().addListener((ChangeListener) (observable, oldVal, newVal) -> search((String) oldVal, (String) newVal, listview));
        tf0.textProperty().addListener((ChangeListener) (observable, oldVal, newVal) -> search((String) oldVal, (String) newVal, listview0));
        tfSchedule.textProperty().addListener((ChangeListener) (observable, oldVal, newVal) -> search((String) oldVal, (String) newVal, listviewSchedules));
    }

    private void updateTree() throws IOException {
        subjects = new Subjects();
        subjects.setSubjects();
        listviewSchedules.setItems(subjects.subjects.sorted());
    }

    public void search(String oldVal, String newVal, ListView<String> lv) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            if(lv == listviewSchedules){
                lv.setItems(subjects.subjects.sorted());
            } else {
                lv.setItems(tutor.names.sorted());
            }
        }
        String value = newVal.toUpperCase();
        ObservableList<String> subentries = FXCollections.observableArrayList();
        for (String entry : lv.getItems()) {
            boolean match = true;
            if (!entry.toUpperCase().contains(value)) {
                match = false;
            }
            if (match) {
                subentries.add(entry);
            }
        }
        lv.setItems(subentries);
    }

    @FXML
    private void AddTutor(ActionEvent event) {
        loadFxml("addTutor");
    }

    public void EditTutor(ActionEvent actionEvent) {
        loadFxml("editTutor");
    }

    public void RemoveTutor(ActionEvent actionEvent) {
        loadFxml("removeTutor");
    }

    private void loadFxml(String file){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(file+".fxml"));
            Parent root = loader.load();

            switch (file){
                case "addTutor":
                case "editTutor":
                case "removeTutor":
                    borderpaneStaff.setCenter(root);
                    break;
                case "setSchedule":
                    SetSchedule setSchedule = loader.getController();
                    setSchedule.setData(name, netid, times, listview.getSelectionModel().getSelectedItem());
                    borderpaneSchedule.setCenter(root);
                    break;
                case "setSubjects":
                    SetSubjects setSubjects = loader.getController();
                    setSubjects.getData(name, netid, subject, listview0.getSelectionModel().getSelectedItem());
                    borderpaneSubjects.setCenter(root);
                    break;
                case "subjectSchedules":
                default:
                    borderpaneSubjectSchedules.setCenter(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo(MouseEvent mouseEvent) throws FileNotFoundException {
        FileReader fr = new FileReader(database);  //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
        ListView<String> source = (ListView<String>) mouseEvent.getSource();
        String[] words;  //Initialize the word Array
        String s;
        try{
            while((s = br.readLine()) != null){
                    words = s.split(",",5);
                if(source.getSelectionModel().getSelectedItem().contains(words[2])){
                    name = words[1]+", "+words[0];
                    netid = words[2];
                    subject = words[3];
                    times = words[4].split(",");
                }
            }
            if(source == listview){
                loadFxml("setSchedule");
            } else if(source == listview0){
                loadFxml("setSubjects");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateList() throws IOException {
        tutor = new Tutor();
        listview.setItems(tutor.names.sorted());
        listview0.setItems(listview.getItems());
    }

    public void openDirChooser(ActionEvent actionEvent) throws IOException {
        //gets the primaryStage from Main.java
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(theStage);

        if(selectedDirectory!=null){
            //save to file for future reference
            FileWriter fileWriter = new FileWriter("ScheduleBuilder\\src\\resources\\ChosenDirectory.txt");
            fileWriter.write(selectedDirectory.getAbsolutePath());
            fileWriter.close();
        }
    }

    public void generateSchedule(ActionEvent actionEvent) {
        GenerateDocument generateDocument = new GenerateDocument(listviewSchedules.getSelectionModel().getSelectedItem(),subjects.subjects.indexOf(listviewSchedules.getSelectionModel().getSelectedItem()));
    }

    //for testing purposes
    public void getIndex(MouseEvent mouseEvent) {
        genbtn.setDisable(false);
//        System.out.println(listviewSchedules.getSelectionModel().getSelectedIndex());
//        System.out.println(listviewSchedules.getSelectionModel().getSelectedItem());
    }
}
