/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package licenta;

import DataManagment.DataManager;
import Views.SummaryView;
import DataProcessing.DataCollector;
import DataManagment.HtmlExporter;
import DataManagment.LatexExporter;
import Helpers.StringHelper;
import Models.AplicationModel;
import Models.DayModel;
import Models.LocalTimeRangeModel;
import Models.SessionModel;
import Models.TopicModel;
import Views.ConstraintsView;
import Views.ScheduleView;
import Views.TableSettingsView;
import Views.TextEditor;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author Ronaldo
 */
public class Licenta extends Application 
{   
    private BorderPane borderPane = new BorderPane();
    private AplicationModel aplicationModel=new AplicationModel();
    private DataManager dataManager=new DataManager(aplicationModel);
    private DataCollector dataCollector= new DataCollector(); 
    private Scene scene=new Scene(borderPane);
    private byte[] aplicationModelSerialized;
    private Stage stage=null;
    
    @Override
    public void start(Stage primaryStage) 
    {
        
        MenuBar menuBar=createMenu();       
        borderPane.setTop(menuBar);                 
        borderPane.setCenter(addAnchorPane(addGridPane()));
        stage=primaryStage;
    
        scene.getStylesheets().add("Styling/styles.css");
                                                     
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        
        primaryStage.setTitle("IEEE Conference Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        stage.setOnCloseRequest(confirmCloseEventHandler);
                
        Parameters params = getParameters();
        List<String> parameters=params.getRaw();
        
        if(parameters.size()>0)
        {
            this.aplicationModel=fileToAplicationModel(parameters.get(0));
        }
    }
    
    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> 
    {
        boolean hasModification=false;
        try
        {
           ByteArrayOutputStream bos=new ByteArrayOutputStream();
           ObjectOutputStream memeoryOutStream = new ObjectOutputStream(bos);
           memeoryOutStream.writeObject(aplicationModel);
            
           byte[] data=bos.toByteArray();
           memeoryOutStream.close();
           bos.close();
           
           if(data.length!=aplicationModelSerialized.length)
           {
               hasModification=true;
           }else
           {
               for(int i=0;i<data.length;i++)
               {
                   if(data[i]!=aplicationModelSerialized[i])
                   {
                       hasModification=true;
                       break;
                   }
               }
           }
               

        }catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        
        
        if(hasModification)
        {
            Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION,                
                        "Press Exit to close the application, or press Cancel to say on and go to save it."
            );
            Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                    ButtonType.OK
            );
            exitButton.setText("Exit");
            closeConfirmation.setHeaderText(" You have some unsaved changes that will be lost if you decide to exit.\n Are you sure you want to exit?\n");
            closeConfirmation.initModality(Modality.APPLICATION_MODAL);
            closeConfirmation.initOwner(stage);

            Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
            if (!ButtonType.OK.equals(closeResponse.get())) 
            {
                event.consume();
            }
        }
        
    };

    
    /**
     * @param args the command line arguments
     */
         
    public static void main(String[] args)throws Exception 
    {
        launch(args);
    }
    
    
    private MenuBar createMenu()
    { 
        MenuBar menuBar = new MenuBar();
 
        // --- Menu File
        Menu menuFile = new Menu("File");
        
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem loadMenuItem = new MenuItem("Load");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(actionEvent ->
                stage.fireEvent(
                        new WindowEvent(
                                stage,
                                WindowEvent.WINDOW_CLOSE_REQUEST
                        )
                ));
        
       // if(aplicationModel.getPathToThesaurus()==null)
        //{
          //  newMenuItem.setDisable(true);
       // }

        menuFile.getItems().addAll(newMenuItem,loadMenuItem, saveMenuItem,
        new SeparatorMenuItem(), exitMenuItem);
        
        newMenuItem.setOnAction(actionEvent -> clickNew());
        saveMenuItem.setOnAction(actionEvent -> clickSave());
        loadMenuItem.setOnAction(actionEvent -> clickLoad());
        
        // --- Menu Generate
        Menu menuGenerate = new Menu("Generate");
        MenuItem generateHtmlMenuItem = new MenuItem("Generate Html");
        MenuItem generateLatexMenuItem = new MenuItem("Generate Latex");
                
        menuGenerate.getItems().addAll(generateHtmlMenuItem,generateLatexMenuItem);
        
        generateHtmlMenuItem.setOnAction(actionEvent -> clickgGenerateHtml());
        generateLatexMenuItem.setOnAction(actionEvent -> clickGenerateLatex());
        
        // --- Menu Settings
        Menu menuSettings = new Menu("Settings");
        MenuItem pathToThesaurusMenuItem = new MenuItem("Path to thesaurus");
        MenuItem pathToTecherAffiliationMenuItem = new MenuItem("Path to teacher affiliation");
        MenuItem lectureLenghtMenuItem = new MenuItem("Lecture lenght");
        pathToThesaurusMenuItem.setOnAction(actionEvent -> clickPathToThesaurus(newMenuItem));
        pathToTecherAffiliationMenuItem.setOnAction(actionEvent -> clickPathToTecherAffiliation(generateHtmlMenuItem,generateLatexMenuItem));
        lectureLenghtMenuItem.setOnAction(actionEvent -> clickLectureLength());
        
        if(aplicationModel.getPathToTecherAffiliation()==null)
        {
            generateHtmlMenuItem.setDisable(true);
            generateLatexMenuItem.setDisable(true);
        }
                                                                       
       // menuSettings.getItems().addAll(pathToThesaurusMenuItem,pathToTecherAffiliationMenuItem,lectureLenghtMenuItem);
        menuSettings.getItems().addAll(pathToTecherAffiliationMenuItem,lectureLenghtMenuItem);
        
        // --- Menu View
        Menu menuView = new Menu("View");
        MenuItem timeTableMenuItem = new MenuItem("Schedule");
        MenuItem summaryMenuItem = new MenuItem("Summary");
        MenuItem constraintMenuItem = new MenuItem("Constraints");
        MenuItem homeMenuItem = new MenuItem("Home");
        
        timeTableMenuItem.setOnAction(actionEvent -> clickViewSchedule());
        summaryMenuItem.setOnAction(actionEvent -> clickViewSummary());
        constraintMenuItem.setOnAction(actionEvent -> clickViewConstraints());
        homeMenuItem.setOnAction(actionEvent -> start(stage));
        
        menuView.getItems().addAll(timeTableMenuItem,summaryMenuItem,constraintMenuItem,new SeparatorMenuItem(),homeMenuItem);
              
        menuBar.getMenus().addAll(menuFile,menuSettings, menuView,menuGenerate);
                  
        return menuBar;

    }
    
    private void clickPathToTecherAffiliation(MenuItem generateHtmlMenuItem,MenuItem generateLatexMenuItem)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Path to teacher affiliation");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("txt", "*.txt")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            aplicationModel.setPathToTecherAffiliation(file.getPath());
            generateHtmlMenuItem.setDisable(false);
            generateLatexMenuItem.setDisable(false);
        }       
    }
    
    private void clickGenerateLatex()
    {   
        aplicationModel.setTeacherAffiliationMap(dataCollector.getTeacherAffiliations(aplicationModel.getPathToTecherAffiliation()));
        
        for(DayModel day : aplicationModel.getDays())
        {
            day.calculatedPeriodForLectures(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
        }
                
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Generate latex");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("tex", "*.tex")
        );

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) 
        {
            try 
            {
                String path=file.getPath();

                FileWriter fileWriter=new FileWriter(path);
                                
                BufferedWriter buffer=new BufferedWriter(fileWriter);
                LatexExporter exporter=new LatexExporter(buffer);
                exporter.genearateLatex(aplicationModel);
                buffer.close();              
                
            } catch (IOException ex) 
            {
                System.out.println(ex.getMessage());
            }
        }        
    }
    
    private void clickgGenerateHtml()
    {   
        aplicationModel.setTeacherAffiliationMap(dataCollector.getTeacherAffiliations(aplicationModel.getPathToTecherAffiliation()));
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Generate html");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("html", "*.html")
        );

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) 
        {
            try 
            {
                String path=file.getPath();

                FileWriter fileWriter=new FileWriter(path);
                                
                BufferedWriter buffer=new BufferedWriter(fileWriter);
                HtmlExporter exporter=new HtmlExporter(buffer);
                exporter.genearateHtml(aplicationModel);
                buffer.close();              
                
            } catch (IOException ex) 
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    private void clickViewSchedule()
    {   
        ScheduleView scheduleView=new ScheduleView(aplicationModel);
        borderPane.setCenter(scheduleView);
    }
    
    private void clickViewConstraints()
    {        
        dataManager.updatedConstraints();
        ConstraintsView constraintView=new ConstraintsView(aplicationModel);
        borderPane.setCenter(constraintView);
    }
    
    private void clickViewSummary()
    {
        SummaryView summaryView=new SummaryView(aplicationModel);
        borderPane.setCenter(summaryView);
    }
    
    private void clickLectureLength()
    {
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Lecture lenght");
        dialog.setHeaderText("Set the lecture lengths in minute");
        dialog.getDialogPane().setPrefSize(300, 150);

        GridPane dialogContent=new GridPane();
        
        Text textShortLectureLength=new Text("Short lecture length:");
        textShortLectureLength.setFont(StringHelper.font16Bold);
        dialogContent.add(textShortLectureLength, 0, 0);
        
        TextEditor shortLectureLength=new TextEditor(Integer.toString(aplicationModel.getShortLectureDuration()));
        shortLectureLength.setIsNumeric(true);
        shortLectureLength.setFont(StringHelper.font16);
        dialogContent.add(shortLectureLength, 1, 0);
        
        Text textLongLectureLength=new Text("Long lecture length:");
        textLongLectureLength.setFont(StringHelper.font16Bold);
        dialogContent.add(textLongLectureLength, 0, 1);
        
        TextEditor longLectureLength=new TextEditor(Integer.toString(aplicationModel.getLongLectureDuration()));
        longLectureLength.setIsNumeric(true);
        longLectureLength.setFont(StringHelper.font16);
        dialogContent.add(longLectureLength, 1, 1);
        
        
        dialog.getDialogPane().setContent(dialogContent);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        Optional<ButtonType> result = dialog.showAndWait();
                        
        if ((result.isPresent()) && (result.get() == buttonTypeOk)) 
        {
            aplicationModel.setShortLectureDuration(Integer.parseInt(shortLectureLength.getText()));
            aplicationModel.setLongLectureDuration(Integer.parseInt(longLectureLength.getText()));
            
            for(DayModel day : aplicationModel.getDays())
            {
                day.calculateTimesAccordingToLecturesDuration(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
            }
            
            start(stage);
        }
    }
    
    private void clickNew()
    {
        int numberOfDays=2;
        int deafultBreakDuration=5;
        
        ButtonType buttonTypeNext = new ButtonType("Next", ButtonBar.ButtonData.NEXT_FORWARD);
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result;
        Dialog dialog = new Dialog<>();
        
        TextEditor textEditor=new TextEditor();    
        textEditor.setText(Integer.toString(deafultBreakDuration));
        textEditor.setIsNumeric(true);
        
        dialog.setHeaderText("Insert deafult break duration:");
        dialog.getDialogPane().setPrefSize(200, 150);

        dialog.getDialogPane().setContent(textEditor);

        dialog.getDialogPane().getButtonTypes().add(buttonTypeNext);
        dialog.getDialogPane().getButtonTypes().add(buttonCancel);

        result = dialog.showAndWait();

        if ((result.isPresent()) && (result.get() == buttonTypeNext)) 
        {    
            textEditor.setText(Integer.toString(numberOfDays));

            dialog.setHeaderText("Insert number of days:");
            dialog.getDialogPane().setPrefSize(200, 150);

            dialog.getDialogPane().setContent(textEditor);

            result = dialog.showAndWait();
            if ((result.isPresent()) && (result.get() == buttonTypeNext)) 
            {
                numberOfDays=(Integer.parseInt(textEditor.getText()));
                ArrayList<DayModel> days=new ArrayList<DayModel>();
                
                for(int i=0; i<numberOfDays;i++)
                {
                    DayModel day=new DayModel();
                    day.setDay(LocalDate.now());
                    day.setTotalPeriod(new LocalTimeRangeModel(8, 0, 360));
                    day.setNumberOfSessionsPerDay(4);
                    
                    days.add(day);
                    
                    TableSettingsView tableSettings=new TableSettingsView(day);
                    
                    dialog.setHeaderText("Day Settings:");
                    dialog.getDialogPane().setPrefSize(400, 350);

                    dialog.getDialogPane().setContent(tableSettings);

                    result = dialog.showAndWait();
                }
                
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Path to folder containing the input files for IEEE Conference");

                File file = directoryChooser.showDialog(stage);
                if (file != null) 
                {

                    String path=file.getPath();                                                    
                    aplicationModel.setTopics(dataCollector.getTopics(path));
                    aplicationModel.setDays(dataCollector.getDays(deafultBreakDuration,days,aplicationModel.getTopics()));
                    for(DayModel day : days)
                    {
                        day.calculateTimesAccordingToLecturesDuration(aplicationModel.getShortLectureDuration(),aplicationModel.getLongLectureDuration());
                    }
                    aplicationModel.setConstraints(dataManager.getConstraints(aplicationModel.getTopics()));
                }
            }                      
        }
        setAplicationModelSerialized();       
        start(stage);      
    }
    
    private void setAplicationModelSerialized()
    {
        try
        {   
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream memeoryOutStream = new ObjectOutputStream(bos);
            memeoryOutStream.writeObject(aplicationModel);
            aplicationModelSerialized=bos.toByteArray();
            
            memeoryOutStream.close();
            bos.close();
        }catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
     private void clickSave()
     {       
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save IEEE Conference");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("ser", "*.ser")
        );

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) 
        {
            try 
            {
                String path=file.getPath();

                FileOutputStream fileOut = new FileOutputStream(path);
                                
                ObjectOutputStream fileOutStream = new ObjectOutputStream(fileOut);
                              
                fileOutStream.writeObject(aplicationModel);
                fileOutStream.close();
                
                fileOut.close();              
                
            } catch (IOException ex) 
            {
                System.out.println(ex.getMessage());
            }
        }      
    }
     
    private void clickLoad()
    {       
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load IEEE Conference");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("ser", "*.ser")
        );
        File file = fileChooser.showOpenDialog(stage);
        
        if(file!=null)
        {
            this.aplicationModel=fileToAplicationModel(file.getPath()); 
        } 
        setAplicationModelSerialized();
        start(stage);
    }
    
    private AplicationModel fileToAplicationModel(String path)
    {
        try
        {
           FileInputStream fileIn = new FileInputStream(path);
           ObjectInputStream in = new ObjectInputStream(fileIn);
           aplicationModel = (AplicationModel) in.readObject();

           in.close();
           fileIn.close();
           return aplicationModel;
        }catch(IOException i)
        {
           i.printStackTrace();
           return null;
        }catch(ClassNotFoundException c)
        {
           System.out.println("Employee class not found");
           c.printStackTrace();
           return null;
        }
    }
    
    private void clickPathToThesaurus(MenuItem newMenuItem)
    {       
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Path to thesaurus");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("txt", "*.txt")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            aplicationModel.setPathToThesaurus(file.getPath());
            newMenuItem.setDisable(false);
        }      
    }
    
    private GridPane addGridPane() 
    {
        GridPane grid = new GridPane();   
        grid.getStyleClass().add("grid");

        Text title = new Text("Lets start");
        title.setFont(StringHelper.font20Bold);
        grid.add(title, 1, 0); 
                
        Text chartSubtitle = new Text("In order to work with this program you need to import datas from txt files or you can continue an earlier saved work.\n"
                + "If you choose to import from txt files, you need to set the path to the folder where are the txt files. The datas in txt\n files should respect "
                + "the following convention:\n-fisrt row:The paper's title\n"
                + "-second row:Authors\n"
                + "-third row:number of pages\n"
                + "-4th row:type(S or F) short or full\n"
                +"-5th row: keywords separated with comma\n"
                +"-6th row: generated keywords separated with comma\n"
                +"-7th row: topic\n"
                +"-8th row: the abstract\n"
                +"\n"
                +"You can save your work, and load it in the file menu\n"
                +"The number of paralel session per day can be selected under settings menu\n"
                +"\n"
                +"Note:\n"
                +"If you want to select 'new' from 'file' menu, first you must select the path to file iie_thesaurus in the 'settings' menu\n"
                +"If you want to change the deafult break duration, you can do it from the 'settings' menu, and the change will be \napplied only if you decide to upload new files. The default break is 10 minutes\n");
        grid.add(chartSubtitle, 1, 1, 2, 1);
              
        return grid;
    }
 
    private AnchorPane addAnchorPane(GridPane grid) 
    {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getStyleClass().add("pane");
        
        anchorpane.getChildren().add(grid);
        AnchorPane.setTopAnchor(grid, 10.0);

        return anchorpane;
    }
    
}
