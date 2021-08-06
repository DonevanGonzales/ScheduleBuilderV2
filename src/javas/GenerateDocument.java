package javas;

import javafx.scene.control.Alert;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.*;

public class GenerateDocument {
    public GenerateDocument(String title, int subject) {
        try {
            Tutor tutor = new Tutor();
            tutor.setList();

            //opens template
            XWPFDocument document = new XWPFDocument(new FileInputStream("ScheduleBuilder/src/resources/Template.docx"));

            //sets the title
            for (XWPFParagraph paragraph : document.getParagraphs()){
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = paragraph.createRun();
                run.setFontSize(18);
                run.setFontFamily("Times New Roman");
                run.setBold(true);
                run.setItalic(true);
                run.setText(title);
            }

            //gets access to the first table in the document
            XWPFTable table = document.getTableArray(0);

            for(int timeOfDay = 0; timeOfDay < 24; timeOfDay++){
                XWPFTableRow someRow = table.getRow(timeOfDay+1);
                for(int i = 0; i < tutor.firstName.size(); i++){// goes through the database one at a time i=tutor
                    // i = tutor charAt(the selected subject)
                    if(tutor.subjects.get(i).charAt(subject) == '1'){ // true if tutor can tutor the selected subject
                        //System.out.println(tutor.firstName.get(i)+" can do algebra");
                        for(int day = 0; day < 6; day++){
                            //System.out.println(tutor.firstName.get(i)+" works "+tutor.times.get(i).get(i*6+day).charAt(0));
                            if(tutor.times.get(i).get(i*6+day).charAt(timeOfDay) == '1') {
                                //whoWorks += tutor.firstName.get(i) + " ";
                                //getCell(time of day so out of 24)
                                someRow.getCell(day + 1).setText(someRow.getCell(day + 1).getText() + tutor.firstName.get(i) + " ");
                            }

                        }
                    }
                }
            }

            try {
                FileReader fr = new FileReader("ScheduleBuilder\\src\\resources\\ChosenDirectory.txt");  //Creation of File Reader object
                BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
                //search for netID in database
                String dir = br.readLine();

                document.write(new FileOutputStream(dir+"\\"+title+".docx"));
                document.close();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Successfully created schedule for "+title);
                alert.showAndWait();

            } catch (FileNotFoundException fe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("File path was not set, please set it in the Management tab under \"Set Directory\"\n"+fe);
                alert.showAndWait();
            }

        } catch (Exception e) {
            //TODO make alert stating that the schedule could not be generated
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not create file\n"+e);
            alert.showAndWait();
        }
    }
}
