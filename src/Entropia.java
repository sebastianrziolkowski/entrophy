import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
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


    private Text actionStatus;
    private Text resultText;
    private Stage savedStage;
    public String fileName;
    private TextField textEdytor;

    public static void main(String[] args) {

        Application.launch(args);


        if (args.length == 0) {
            System.out.println("Nie podano pliku do odczytu!");
            return;
        }


       // fileName = args[0];

       // System.out.println("Wczytano plik " + fileName);




    }

    public void start(Stage primaryStage) {
        Button open = new Button("open");
        Button load = new Button("load");
        textEdytor = new TextField();
        textEdytor.setMinSize(500,25);

        open.setOnAction(new SingleFcButtonListener());
        load.setOnAction(new ActionButtonListener());

        HBox textBox = new HBox(10);
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(open,load);
        textBox.getChildren().addAll(textEdytor);

        actionStatus = new Text();
        resultText = new Text("Result:");


        VBox vbox = new VBox(30);
        vbox.getChildren().addAll( buttonBox, textBox,  actionStatus, resultText);
        Scene scene = new Scene(vbox, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        savedStage = primaryStage;
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
        }
    }


    private void showSingleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            fileName=selectedFile.getPath();
            actionStatus.setText("File selected: " + fileName );
            try {
                BufferedReader bufor = new BufferedReader(new FileReader(fileName));

                while(bufor.ready())
                {
                    textEdytor.setText(textEdytor.getText() + bufor.readLine());
                }
            } catch (Exception e) {
                System.out.println("Error file load" + e.toString());
            }
        }



    }

    private void countChar()
    {
        boolean check = false;
        ArrayList<charElement> charArrayList = new ArrayList<charElement>();
        try
        {
            String text = textEdytor.getText();

            if(charArrayList.isEmpty())
            {
                charArrayList.add(new charElement(1,text.charAt(0)));
            }

            if(!charArrayList.isEmpty())
            {
                for(int i=1;i<text.length();i++)
                {
                    check=false;
                   for(int j=0;j<charArrayList.size();j++)
                   {
                       if((int)charArrayList.get(j).code==(int)text.charAt(i))
                       {
                           check = true;
                           charArrayList.get(j).value++;
                       }
                   }
                   if(check==false)
                   {
                       charArrayList.add(new charElement(1,text.charAt(i)));
                   }
                }
            }


            Collections.sort(charArrayList, new SortByValue());

            for(int i=0;i<charArrayList.size();i++)
            {
                System.out.println(charArrayList.get(i).code + " -> " + charArrayList.get(i).value);
            }


        }
        catch (Exception e)
        {
            System.out.println("Get text from edytor error" + e.toString());
        }
    }

}

