
package compi1.subsetify;
 
import java.io.StringReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import javafx.scene.layout.HBox;


/**
 *
 * @author Steven
 */
public class Compi1SubSetify extends Application {

   private TextArea textArea, consoleTextArea;
    private File currentFile;
    
    

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SubSeftify - Text Editor");

        textArea = new TextArea();
        consoleTextArea = new TextArea(); // Inicializa la nueva TextArea
        
        consoleTextArea.setEditable(false);
        HBox textAreasBox = new HBox(textArea, consoleTextArea);
        //SplitPane splitPane = new SplitPane(textArea, consoleTextArea); // Divide la ventana en dos áreas

        BorderPane root = new BorderPane(textAreasBox);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Archivo");
        
        
        MenuItem openItem = new MenuItem("Abrir");
        openItem.setOnAction(e -> openFile());

        MenuItem saveItem = new MenuItem("Guardar");
        saveItem.setOnAction(e -> saveFile());

        MenuItem saveAsItem = new MenuItem("Guardar como...");
        saveAsItem.setOnAction(e -> saveAsFile());
        fileMenu.getItems().addAll(openItem, saveItem, saveAsItem);
        
        
        menuBar.getMenus().add(fileMenu);
        
        Button btnAnalizar = new Button("ANALIZAR");
        btnAnalizar.setOnAction(e -> analizar(textArea.getText()));
        
        Button btnGraficar = new Button("GRAFICAR");
        
        
        
        root.setTop(menuBar);
        root.setBottom(createToolbar(btnAnalizar,btnGraficar) );

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

     public static void analizar (String entrada){
        try {
            analizadores.lexico  lexico = new analizadores.lexico(new StringReader(entrada)); 
            analizadores.sintactico  parser = new analizadores.sintactico(lexico);
            parser.parse();
        } catch (Exception e) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println(e);
        } 
    } 
    
     private ToolBar createToolbar(Button btnAnalizar, Button btnGraficar) {
        ToolBar toolbar = new ToolBar();
        toolbar.getItems().addAll(btnAnalizar,btnGraficar);
        return toolbar;
    }
    
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                String content = new String(Files.readAllBytes(selectedFile.toPath()));
                textArea.setText(content);
                currentFile = selectedFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveAsFile();
        } else {
            try {
                Files.write(currentFile.toPath(), textArea.getText().getBytes(), StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveAsFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            try {
                Files.write(selectedFile.toPath(), textArea.getText().getBytes(), StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
                currentFile = selectedFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void analizadores(String ruta, String jflexFile, String cupFile){
        try {
            String opcionesJflex[] =  {ruta+jflexFile,"-d",ruta};
            jflex.Main.generate(opcionesJflex);

            String opcionesCup[] =  {"-destdir", ruta,"-parser","sintactico",ruta+cupFile};
            java_cup.Main.main(opcionesCup);
            
        } catch (Exception e) {
            System.out.println("No se ha podido generar los analizadores");
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
        //analizadores("src/analizadores/","lexico.flex","sintactico.cup");
    }
      
}
