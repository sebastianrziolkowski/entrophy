import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Entropia extends Application {

        class charElement
        {
            int value=0;
            char code;

            public charElement(int i, char Code) {
               value=i;
               code=Code;
            }
        }


        //* https://medium.freecodecamp.org/utilizing-javas-arrays-sort-for-any-list-of-objects-e3e2db61d70b
        class SortByValue implements Comparator<charElement> {
            public int compare(charElement a, charElement b) {
                return b.value - a.value;
            }
        }

    ScrollPane scrollPane = new ScrollPane();
    ArrayList<charElement> charArrayList = new ArrayList<charElement>();
    private Text resultText = new Text("Result:");
    private Text actionStatus;
    private TextField textField;

    public static void main(String[] args) {

        Application.launch(args);


        if (args.length == 0) {
            System.out.println("Nie podano pliku do odczytu!");
            return;
        }

    }

    public void start(Stage primaryStage) {
        Button open = new Button("open");
        Button load = new Button("load");
        textField = new TextField();
        textField.setText("Type here!");
        textField.setMinSize(500,25);



        HBox textBox = new HBox(10);
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(open,load);
        textBox.getChildren().addAll(textField);

        actionStatus = new Text();



        VBox vbox = new VBox(30);
        vbox.getChildren().addAll( buttonBox, textBox,  actionStatus,  scrollPane);
        Scene scene = new Scene(vbox, 545, 300);
        primaryStage.setTitle("Entropy calculator");
        primaryStage.setScene(scene);
        primaryStage.show();

        open.setOnAction(new SingleFcButtonListener());
        load.setOnAction(new ActionButtonListener());

        scrollPane.setMaxWidth(250);
        scrollPane.setMinHeight(150);
        scrollPane.setContent(resultText);

    }

    private class SingleFcButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            showSingleFileChooser();
        }
    }


    private class ActionButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            countChar();
            showResult();

        }
    }


    private void showSingleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String fileName = selectedFile.getPath();
            actionStatus.setText("File selected: " + fileName);
            try {
                BufferedReader bufor = new BufferedReader(new FileReader(fileName));

                while(bufor.ready())
                {
                    textField.setText(textField.getText() + bufor.readLine());
                }
            } catch (Exception e) {
                System.out.println("Error file load" + e.toString());
            }
        }



    }


    private void showResult()
    {
        //resultText
        //charArrayList
        String result="";
        for(charElement acharArrayList : charArrayList)
        {
            result+=acharArrayList.code +"\t->\t"  + acharArrayList.value + "\n";
        }
        resultText.setText(result);


    }

    private void countChar()
    {
        charArrayList.clear();
        boolean check = false;
        try
        {
            String text = textField.getText();

            if(charArrayList.isEmpty())
            {
                charArrayList.add(new charElement(1,text.charAt(0)));
            }

            if(!charArrayList.isEmpty())
            {
                for(int i=1;i<text.length();i++)
                {
                    check=false;
                    for (charElement aCharArrayList : charArrayList) {
                        if ((int) aCharArrayList.code == (int) text.charAt(i)) {
                            check = true;
                            aCharArrayList.value++;
                        }
                    }
                   if(!check)
                   {
                       charArrayList.add(new charElement(1,text.charAt(i)));
                   }
                }
            }


            Collections.sort(charArrayList, new SortByValue());

            for (charElement aCharArrayList : charArrayList) {
                System.out.println(aCharArrayList.code + " -> " + aCharArrayList.value);
            }


        }
        catch (Exception e)
        {
            System.out.println("Get text from edytor error" + e.toString());
        }
    }

}

