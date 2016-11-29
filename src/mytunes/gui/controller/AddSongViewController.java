/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;

/**
 * FXML Controller class
 *
 * @author James
 */
public class AddSongViewController implements Initializable
{

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtGenre;
    @FXML
    private TextField txtPath;

    @FXML
    private Button closeButton;

    private SongModel songModel;

    private Song song;
    ObservableList<Song> songs = FXCollections.observableArrayList();
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        songModel = SongModel.getInstance();

    }

    @FXML
    private void closeWindow()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    public void addSong()
    {
        String title = txtTitle.getText();
        String artist = txtArtist.getText();
        String genre = txtGenre.getText();
        String path = txtPath.getText();

        song = new Song(title, artist, genre, 4.20, 0, path);
        songModel.addSong(song);
        closeWindow();

    }

    @FXML
    private void browseForFile(ActionEvent event) {
           try
        {
            FileChooser fileChooser = new FileChooser();
            Window win = root.getScene().getWindow();
            File file = fileChooser.showOpenDialog(win);
            txtPath.setText(file.getPath());
            
            
        } catch (Exception ex)
        {
        }
    }
        
    

}
