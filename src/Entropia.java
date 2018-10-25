import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;






public class Entropia extends Application {

    private Text actionStatus;
    private Stage savedStage;

    public static void main(String[] args) {

        Application.launch(args);


        if (args.length == 0) {
            System.out.println("Nie podano pliku do odczytu!");
            return;
        }


        String fileName = args[0];

        System.out.println("Wczytano plik " + fileName);


        try {

            BufferedReader bufor = new BufferedReader(new FileReader(fileName));


            int kodZnaku;

            while (bufor.ready()) {

                kodZnaku = bufor.read();

                System.out.println(kodZnaku + "\t" + (char) kodZnaku);


            }

        } catch (Exception ex) {

            System.out.println("Bład przekazywania tekstu!" + ex);

        }


    }

    public void start(Stage primaryStage) {
        Button open = new Button("open");
        open.setOnAction(new SingleFcButtonListener());
        HBox open1 = new HBox(10);
        open1.getChildren().addAll(open);
        Button load = new Button("load");
        HBox load1 = new HBox(10);
        load1.getChildren().addAll(load);

        actionStatus = new Text();



        VBox vbox = new VBox(30);
        vbox.getChildren().addAll( open1,load1,  actionStatus);
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

            actionStatus.setText("File selected: " + selectedFile.getName());
        }

    }

}

