import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.util.Comparator;
import java.util.stream.Collectors;


public class Entropia extends Application {

        class charElement
        {
            int value=0;
            char code;

            charElement(int i, char Code) {
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

    private ScrollPane scrollPane = new ScrollPane();
    private ArrayList<charElement> charArrayList = new ArrayList<>();
    private Text resultText = new Text("Result:");
    private Text actionStatus;
    private TextField textField;
    private static int lettersNumber =0;

    public static void main(String[] args) {

        Application.launch(args);


        if (args.length == 0 && lettersNumber==0) {
            System.out.println("Nie podano pliku do odczytu!");
        }

    }

    public void start(Stage primaryStage) {
        Button open = new Button("Open file");
        Button load = new Button("Analyze");
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
        Scene scene = new Scene(vbox, 545, 310);
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

                textField.setText("");
                while(bufor.ready())
                {
                    textField.setText(textField.getText() +bufor.lines().collect(Collectors.joining()));
                }
            } catch (Exception e) {
                System.out.println("Error file load" + e.toString());
            }
        }



    }

    private static double log2(double n) {
        return Math.log(n) / Math.log(2);
    }

    private double calcEntropy()
    {
        double entropy = 0;
        for(charElement aCharArrayList : charArrayList)
        {
            double prob = (double)aCharArrayList.value / lettersNumber;
            entropy -= prob * log2(prob);
        }

        return entropy;
    }

    private void showResult()
    {
        java.text.DecimalFormat df=new java.text.DecimalFormat("0.0000");

        String result="";
        for(charElement acharArrayList : charArrayList)
        {
            result+=acharArrayList.code +"\t->\t"  + acharArrayList.value + "\t(" + df.format((double)acharArrayList.value/(double)lettersNumber) +") \n";
        }
        result+="\n H(X) =" + calcEntropy();
        resultText.setText(result);

    }

    private void countChar()
    {
        charArrayList.clear();
        boolean check = false;
        try
        {
            String text = textField.getText();
            lettersNumber = text.length();
            //System.out.println(lettersNumber);

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


            charArrayList.sort(new SortByValue());

            /*
            for (charElement aCharArrayList : charArrayList) {
                System.out.println(aCharArrayList.code + " -> " + aCharArrayList.value);
            }
            */


        }
        catch (Exception e)
        {
            System.out.println("Text error. " + e.toString());
        }
    }

}

