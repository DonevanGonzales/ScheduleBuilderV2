package javas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class Subjects {
    public ObservableList<String> subjects = FXCollections.observableArrayList();
    public ArrayList<Integer> index = new ArrayList<>();

    public void setSubjects() throws IOException {
        File database = new File("ScheduleBuilder\\src\\resources\\subjects.txt");
        FileReader fr = null;  //Creation of File Reader object
        String[] tutorInfo;
        String[] time;
        String s;
        int i=0;

        fr = new FileReader(database);
        BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object

        if(database.isFile() && database.canRead()){
            while((s = br.readLine()) != null) {
                subjects.add(s);
                index.add(i);
                i++;
            }
        }
    }
}
