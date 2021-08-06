package javas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SetSchedule implements Initializable{

    public Tutor tutor;
    public String[] buff;

    @FXML
    public Label nmlb;
    public Label nidlb;
    public GridPane grid;
    public Button save2;
    private File database;
    private String dataSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = new File("src/database.csv");
        try {
            tutor = new Tutor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(String name, String netid, String[] time, String selectedIndex) {
        if(grid.isDisabled()){
            grid.setDisable(false);
        }
        nmlb.setText(name);
        nidlb.setText(netid);
        buff = time;
        dataSelected = selectedIndex;
        char[] scheduled;
        for(int i=0;i<6;i++){
            scheduled = time[i].toCharArray();
            for(int j=0;j<24;j++){
                CheckBox btn = new CheckBox();
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setMaxHeight(Double.MAX_VALUE);
                btn.setAlignment(Pos.CENTER);
                btn.setBorder(Border.EMPTY);
                btn.getStyleClass().add("btn-schedule");
                btn.setId(i + "," + j);

                if(scheduled[j] == '1'){
                    btn.setSelected(true);
                } else {
                    btn.setSelected(false);
                }

                btn.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
                    if(save2.isDisabled()){
                        save2.setDisable(false);
                    }
                    String position;
                    char[] a;
                    for(int i1 = 0; i1 <6; i1++) {
                        a = buff[i1].toCharArray();
                        for (int j1 = 0; j1 < 24; j1++) {
                            position = ( i1 + "," + j1);
                            if(position.equals(btn.getId())){
                                if(btn.isSelected()){
                                    a[j1] = '1';
                                } else {
                                    a[j1] = '0';
                                }
                            }
                        }
                        buff[i1] = String.valueOf(a);
                    }
                });
                //btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                grid.add(btn,i+1,j+1);
            }
        }
    }

    public void saveChanges(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                // input the (modified) file content to the StringBuffer "input"
                BufferedReader file = new BufferedReader(new FileReader(database));
                StringBuilder inputBuffer = new StringBuilder();
                String line;
                String[] words;
                while((line = file.readLine()) != null){
                    words = line.split(",");
                    if (dataSelected.contains(words[2])) {
                        line = words[0] + "," + words[1] + "," + words[2] + "," + words[3];
                        for (String s : buff) {
                            line += "," + s;
                        }
                    }
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
                file.close();

                // write the new string with the replaced line OVER the same file
                FileOutputStream fileOut = new FileOutputStream(database);
                fileOut.write(inputBuffer.toString().getBytes());
                fileOut.close();

                save2.setDisable(true);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
