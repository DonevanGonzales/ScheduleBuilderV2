package javas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SetSubjects implements Initializable {

    @FXML
    public CheckBox algebra;
    public CheckBox precal;
    public CheckBox cal1;
    public CheckBox cal2;
    public CheckBox cal3;
    public CheckBox buscal1;
    public CheckBox buscal2;
    public CheckBox discreet1;
    public CheckBox discreet2;
    public CheckBox linear;
    public CheckBox elemstat;
    public CheckBox difeq;
    public CheckBox genchem1;
    public CheckBox genchem2;
    public CheckBox ochem1;
    public CheckBox ochem2;
    public CheckBox funbio;
    public CheckBox orgbio;
    public CheckBox micro;
    public CheckBox gen;
    public CheckBox phys1;
    public CheckBox phys2;
    public CheckBox mech;
    public CheckBox fa;
    public CheckBox ma;
    public CheckBox ia1;
    public CheckBox ia2;
    public CheckBox bf;
    public CheckBox fm;
    public CheckBox pmicro;
    public CheckBox pmacro;
    public CheckBox busstat;
    public CheckBox writing;
    public CheckBox span1;
    public CheckBox span2;
    public CheckBox phil;
    public CheckBox ethics;
    public CheckBox logic;
    public CheckBox stats;
    public CheckBox cs1;
    public CheckBox cs2;
    public CheckBox cs3;
    public TabPane subjectpane;
    public Button save1;

    @FXML
    private Label nmlb;
    @FXML
    private Label nidlb;

    private String subject;
    private String buffer;

    private File database;
    private String dataSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = new File("ScheduleBuilder\\src\\resources\\database.csv");
    }

    public void getData(String name, String netid, String sub, String selectedIndex) {
        if(subjectpane.isDisabled()){
            subjectpane.setDisable(false);
        }
        nmlb.setText(name);
        nidlb.setText(netid);
        subject = sub;
        buffer = sub;
        dataSelected = selectedIndex;

        char[] a = subject.toCharArray();
        if(a[0] == '1') algebra.setSelected(true);
        if(a[1] == '1') precal.setSelected(true);
        if(a[2] == '1') cal1.setSelected(true);
        if(a[3] == '1') cal2.setSelected(true);
        if(a[4] == '1') cal3.setSelected(true);
        if(a[5] == '1') buscal1.setSelected(true);
        if(a[6] == '1') buscal2.setSelected(true);
        if(a[7] == '1') discreet1.setSelected(true);
        if(a[8] == '1') discreet2.setSelected(true);
        if(a[9] == '1') linear.setSelected(true);
        if(a[10] == '1') elemstat.setSelected(true);
        if(a[11] == '1') difeq.setSelected(true);
        if(a[12] == '1') genchem1.setSelected(true);
        if(a[13] == '1') genchem2.setSelected(true);
        if(a[14] == '1') ochem1.setSelected(true);
        if(a[15] == '1') ochem2.setSelected(true);
        if(a[16] == '1') funbio.setSelected(true);
        if(a[17] == '1') orgbio.setSelected(true);
        if(a[18] == '1') micro.setSelected(true);
        if(a[19] == '1') gen.setSelected(true);
        if(a[20] == '1') phys1.setSelected(true);
        if(a[21] == '1') phys2.setSelected(true);
        if(a[22] == '1') mech.setSelected(true);
        if(a[23] == '1') fa.setSelected(true);
        if(a[24] == '1') ma.setSelected(true);
        if(a[25] == '1') ia1.setSelected(true);
        if(a[26] == '1') ia2.setSelected(true);
        if(a[27] == '1') bf.setSelected(true);
        if(a[28] == '1') fm.setSelected(true);
        if(a[29] == '1') pmicro.setSelected(true);
        if(a[30] == '1') pmacro.setSelected(true);
        if(a[31] == '1') busstat.setSelected(true);
        if(a[32] == '1') writing.setSelected(true);
        if(a[33] == '1') span1.setSelected(true);
        if(a[34] == '1') span2.setSelected(true);
        if(a[35] == '1') phil.setSelected(true);
        if(a[36] == '1') ethics.setSelected(true);
        if(a[37] == '1') logic.setSelected(true);
        if(a[38] == '1') stats.setSelected(true);
        if(a[39] == '1') cs1.setSelected(true);
        if(a[40] == '1') cs2.setSelected(true);
        if(a[41] == '1') cs3.setSelected(true);
    }
    
    public void loadToBuffer(ActionEvent actionEvent) {
        if(save1.isDisabled()){
            save1.setDisable(false);
        }
        CheckBox source = (CheckBox) actionEvent.getSource();
        char[] a=buffer.toCharArray();
        if(source == algebra) a[0] = (source.isSelected() ? '1' : '0');
        if(source == precal) a[1] = (source.isSelected() ? '1' : '0');
        if(source == cal1) a[2] = (source.isSelected() ? '1' : '0');
        if(source == cal2) a[3] = (source.isSelected() ? '1' : '0');
        if(source == cal3) a[4] = (source.isSelected() ? '1' : '0');
        if(source == buscal1) a[5] = (source.isSelected() ? '1' : '0');
        if(source == buscal2) a[6] = (source.isSelected() ? '1' : '0');
        if(source == discreet1) a[7] = (source.isSelected() ? '1' : '0');
        if(source == discreet2) a[8] = (source.isSelected() ? '1' : '0');
        if(source == linear) a[9] = (source.isSelected() ? '1' : '0');
        if(source == elemstat) a[10] = (source.isSelected() ? '1' : '0');
        if(source == difeq) a[11] = (source.isSelected() ? '1' : '0');
        if(source == genchem1) a[12] = (source.isSelected() ? '1' : '0');
        if(source == genchem2) a[13] = (source.isSelected() ? '1' : '0');
        if(source == ochem1) a[14] = (source.isSelected() ? '1' : '0');
        if(source == ochem2) a[15] = (source.isSelected() ? '1' : '0');
        if(source == funbio) a[16] = (source.isSelected() ? '1' : '0');
        if(source == orgbio) a[17] = (source.isSelected() ? '1' : '0');
        if(source == micro) a[18] = (source.isSelected() ? '1' : '0');
        if(source == gen) a[19] = (source.isSelected() ? '1' : '0');
        if(source == phys1) a[20] = (source.isSelected() ? '1' : '0');
        if(source == phys2) a[21] = (source.isSelected() ? '1' : '0');
        if(source == mech) a[22] = (source.isSelected() ? '1' : '0');
        if(source == fa) a[23] = (source.isSelected() ? '1' : '0');
        if(source == ma) a[24] = (source.isSelected() ? '1' : '0');
        if(source == ia1) a[25] = (source.isSelected() ? '1' : '0');
        if(source == ia2) a[26] = (source.isSelected() ? '1' : '0');
        if(source == bf) a[27] = (source.isSelected() ? '1' : '0');
        if(source == fm) a[28] = (source.isSelected() ? '1' : '0');
        if(source == pmicro) a[29] = (source.isSelected() ? '1' : '0');
        if(source == pmacro) a[30] = (source.isSelected() ? '1' : '0');
        if(source == busstat) a[31] = (source.isSelected() ? '1' : '0');
        if(source == writing) a[32] = (source.isSelected() ? '1' : '0');
        if(source == span1) a[33] = (source.isSelected() ? '1' : '0');
        if(source == span2) a[34] = (source.isSelected() ? '1' : '0');
        if(source == phil) a[35] = (source.isSelected() ? '1' : '0');
        if(source == ethics) a[36] = (source.isSelected() ? '1' : '0');
        if(source == logic) a[37] = (source.isSelected() ? '1' : '0');
        if(source == stats) a[38] = (source.isSelected() ? '1' : '0');
        if(source == cs1) a[39] = (source.isSelected() ? '1' : '0');
        if(source == cs2) a[40] = (source.isSelected() ? '1' : '0');
        if(source == cs3) a[41] = (source.isSelected() ? '1' : '0');

        buffer = String.valueOf(a);
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
                    words = line.split(",",5);
                    if (dataSelected.contains(words[2])) {
                        line = words[0] + "," + words[1] + "," + words[2] + "," + buffer +","+words[4];
                    }
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
                file.close();

                // write the new string with the replaced line OVER the same file
                FileOutputStream fileOut = new FileOutputStream(database);
                fileOut.write(inputBuffer.toString().getBytes());
                fileOut.close();

                save1.setDisable(true);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
