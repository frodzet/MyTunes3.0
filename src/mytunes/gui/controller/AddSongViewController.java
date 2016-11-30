/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
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
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

/**
 * FXML Controller class
 *
 * @author James
 */
public class AddSongViewController implements Initializable {

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
        stage.close();
    }

    @FXML
    public void addSong() throws IOException, TagException
    {
        String title = txtTitle.getText();
        String artist = txtArtist.getText();
        String genre = txtGenre.getText();
        String path = txtPath.getText();
//        MP3File file = new MP3File(path);
//        long audioFileLength = file.getID3v2Tag().getSize();
//        int frameSize = file.getID3v2Tag().getFrameCount();
//        float durationInSeconds = (audioFileLength / frameSize);
        song = new Song(title, artist, genre, 4.20, 0, path);
        songModel.addSong(song);
        closeWindow();

    }

    @FXML
    private void browseForFile(ActionEvent event) throws IOException, TagException
    {
        FileChooser fileChooser = new FileChooser();
        Window win = root.getScene().getWindow();
        File file = fileChooser.showOpenDialog(win);
        txtPath.setText(file.getPath());
        String title;
        String artist;
        String genre;

        MP3File mp3file = new MP3File(file);
        System.out.println(mp3file.toString());
        title = mp3file.getID3v2Tag().getSongTitle();
        artist = mp3file.getID3v2Tag().getLeadArtist();
        genre = mp3file.getID3v2Tag().getSongGenre();

        txtTitle.setText(title);
        txtArtist.setText(artist);
        txtGenre.setText(genre);
        
        
//        long audioFileLength = mp3file.getID3v2Tag().getSize();
//        int frameSize = mp3file.getID3v2Tag().getFrameCount();
//        float durationInSeconds = (audioFileLength / frameSize);
//        
//        int length = 254;
//        int result = 15;
//        int some = (int) (audioFileLength-(frameSize*(frameSize+2)));
//        
//        System.out.println(audioFileLength);
//        System.out.println(frameSize);
//        System.out.println(durationInSeconds);
//        
//        System.out.println(some);
        
      
    }

}
