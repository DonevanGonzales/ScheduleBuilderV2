package javas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Tutor{
    public void setList() throws IOException {
        File database = new File("ScheduleBuilder\\src\\resources\\database.csv");
        FileReader fr = null;  //Creation of File Reader object
        String[] tutorInfo;
        String[] time;
        String s;

        fr = new FileReader(database);
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object

        int i = 0;
        if(database.isFile() && database.canRead()){
            while((s = br.readLine()) != null) {
                tutorInfo = s.split(",",5);
                firstName.add(tutorInfo[0]);

                lastName.add(tutorInfo[1]);

                netID.add(tutorInfo[2]);

                subjects.add(tutorInfo[3]);

                time = tutorInfo[4].split(",");
                agivenday.addAll(Arrays.asList(time));
                times.add(agivenday);

                i++;
            }
        }
    }

    public void setNames() throws IOException {
        File database = new File("ScheduleBuilder\\src\\resources\\database.csv");
        FileReader fr = null;  //Creation of File Reader object
        String[] words;
        String s;

        fr = new FileReader(database);
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object

        if(database.isFile() && database.canRead()){
            while((s = br.readLine()) != null){
                words = s.split(",");
                names.add(words[1]+", "+words[0]+", "+words[2]);
            }
        }
    }

    public ObservableList<String> names = FXCollections.observableArrayList();

    public ArrayList<String> firstName = new ArrayList<>();
    public ArrayList<String> lastName = new ArrayList<>();
    public ArrayList<String> netID = new ArrayList<>();
    public ArrayList<String> subjects = new ArrayList<>();
    public ArrayList<ArrayList<String>> times = new ArrayList<>();
    public ArrayList<String> agivenday =  new ArrayList<>();


    public Tutor() throws IOException {
        setNames();
    }




}

