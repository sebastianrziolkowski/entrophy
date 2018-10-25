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






public class Entropia extends Application {

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

    private void showSingleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            fileName=selectedFile.getPath();
            actionStatus.setText("File selected: " + fileName );
            try {
                BufferedReader bufor = new BufferedReader(new FileReader(fileName));
                textEdytor.setText(bufor.readLine());
            } catch (Exception e) {
                System.out.println("Error file load" + e.toString());
            }
        }



    }

}

