/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.bll.SongManager;
import mytunes.gui.model.SongModel;

/**
 *
 * @author Simon Birkedal
 */
public class MainViewController implements Initializable {

    private SongManager songManager;
    private Song song;
    ObservableList<Song> songs = FXCollections.observableArrayList();
    private SongModel songModel;
    private Song selectedSong;
    private boolean isPlaying = false;

    @FXML
    private Button btnPlay;
    @FXML
    private TableView<Song> tableSongs;
    @FXML
    private ProgressBar progressTime;
    @FXML
    private TableColumn<Song, String> colName;
    @FXML
    private TableColumn<Song, String> colArtist;
    @FXML
    private TableColumn<Song, String> colGenre;
    @FXML
    private TableColumn<Song, Double> colDuration;
    @FXML
    private TableColumn<Song, Double> colRating;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblSongPlaying;
    @FXML
    private ListView<Song> listPlayList;
    @FXML
    private Slider slideVol;
    @FXML
    private MenuBar menuBar;

    public MainViewController()
    {
    }

    private void loadAddSongView() throws IOException
    {
        Stage primStage = (Stage) tableSongs.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/AddSongView.fxml"));
        Parent root = loader.load();

        AddSongViewController addSongViewController = loader.getController();

        Stage AddSongViewStage = new Stage();
        AddSongViewStage.setScene(new Scene(root));

        AddSongViewStage.initModality(Modality.WINDOW_MODAL);
        AddSongViewStage.initOwner(primStage);

        AddSongViewStage.show();

    }

    @FXML
    public void handleAddSongButton(ActionEvent event) throws IOException
    {
        loadAddSongView();
    }

    @FXML
    public void tableSelected(MouseEvent event)
    {
        selectedSong = tableSongs.selectionModelProperty().getValue().getSelectedItem();
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
        {
            if (selectedSong != null)
            {
                Song newSong = new Song(selectedSong.getTitle(), selectedSong.getArtist(), selectedSong.getGenre(), selectedSong.getDuration(), 0, selectedSong.getPath());
                songManager.pauseSong();
                songManager.playSong(newSong, true);
                changePlayButton(false);
            }

        }
    }

    @FXML
    public void handlePlayPauseButton()
    {
        //Play button pressed
        if (!isPlaying)
        {
            if (selectedSong != null)
            {
                Song newSong = new Song(selectedSong.getTitle(), selectedSong.getArtist(), selectedSong.getGenre(), selectedSong.getDuration(), 0, selectedSong.getPath());

                songManager.playSong(newSong, false);
            }
        }
        //Pause button pressed
        else if (isPlaying)
        {
            songManager.pauseSong();
        }
        changePlayButton(isPlaying);
    }

    public ObservableList<Song> getSongs()
    {
        return songs;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        songManager = new SongManager();
        songModel = SongModel.getInstance();
        colName.setCellValueFactory(new PropertyValueFactory<>("title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        setSongs();
        tableSongs.setItems(songs);
    }

    public TableView<Song> getTableSongs()
    {
        return tableSongs;
    }

    public void setSongs()
    {
        songs = (ObservableList<Song>) songModel.getSongs();
    }

    private void changePlayButton(boolean playing)
    {
        if (playing)
        {
            btnPlay.setText("Play");
            isPlaying = false;
        }
        else
        {
            btnPlay.setText("Pause");
            isPlaying = true;
        }
    }

}
