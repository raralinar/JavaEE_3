package com.lab3.task3;

import com.lab3.task2.Music;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlTree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class Controller {

    public TextField search;
    public Button searchButton, fullInfoButton;
    public TableView<Music> table;
    public VBox addVBox, deleteVBox;
    public TextField singerField, titleField, genreField, idField;
    public Button addButton, deleteButton;
    public Model model = new Model();

    public void initialize() throws UnsupportedEncodingException {
        sendRequest();
        searchButton.setOnAction(e -> {
            try {
                handleSearch(search.getText());
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        });
        fullInfoButton.setOnAction(e -> {
            try {
                handleFullINfoButton();
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        });
        addButton.setOnAction(e -> {
            try {
                handleAddButton(singerField.getText(), titleField.getText(), genreField.getText());
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteButton.setOnAction(e -> {
            try {
                handleDeleteButton(idField.getText());
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleDeleteButton(String id) throws UnsupportedEncodingException {
        String result = model.send("http://localhost:8087/lab3/task2/information?action=delete&id=" + id, "GET");
        ObservableList<Music> music = FXCollections.observableArrayList();
        Document html = Jsoup.parse(result);
        Element table1 = html.select("table").get(1);
        Elements tr = table1.getElementsByTag("tr");
        for (int i = 1; i < tr.size(); i++) {
            Element e = tr.get(i);
            Elements td = e.getElementsByTag("td");
            Music m = new Music(td.get(2).text(), td.get(1).text(), td.get(3).text(), td.get(0).text());
            music.add(m);
        }
        changeTable(music);
        idField.setText("");
    }

    private void handleAddButton(String singer, String title, String genre) throws UnsupportedEncodingException {
        String result = model.send("http://localhost:8087/lab3/task2/information?action=add&singer=" + URLEncoder.encode(singer, StandardCharsets.UTF_8) +
                "&title=" +  URLEncoder.encode(title, StandardCharsets.UTF_8) + "&genre=" +  URLEncoder.encode(genre, StandardCharsets.UTF_8), "GET");
        ObservableList<Music> music = FXCollections.observableArrayList();
        Document html = Jsoup.parse(result);
        Element table1 = html.select("table").get(1);
        Elements tr = table1.getElementsByTag("tr");
        for (int i = 1; i < tr.size(); i++) {
            Element e = tr.get(i);
            Elements td = e.getElementsByTag("td");
            Music m = new Music(td.get(2).text(), td.get(1).text(), td.get(3).text(), td.get(0).text());
            music.add(m);
        }
        changeTable(music);
    }

    private void handleFullINfoButton() throws UnsupportedEncodingException {
        sendRequest();
        search.setText("");
    }

    private void handleSearch(String singer) throws UnsupportedEncodingException {
        String url = "http://localhost:8087/lab3/task2/filter?filterSinger=" + singer;
        String result = model.send(url, "GET");
        ObservableList<Music> music = FXCollections.observableArrayList();
        Document html = Jsoup.parse(result);
        Element table1 = html.select("table").get(1);
        Elements tr = table1.getElementsByTag("tr");
        for (int i = 1; i < tr.size(); i++) {
            Element e = tr.get(i);
            Elements td = e.getElementsByTag("td");
            if (td.get(1).text().toUpperCase().contains(singer.toUpperCase()) || td.get(1).text().equalsIgnoreCase(singer)) {
                Music m = new Music(td.get(2).text(), td.get(1).text(), td.get(3).text(), td.get(0).text());
                music.add(m);
            }
        }
        changeTable(music);
    }

    private void changeTable(ObservableList<Music> music) {

        TableColumn<Music, String> idColumn = (TableColumn<Music, String>) table.getColumns().get(0);
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        TableColumn<Music, String> singerColumn = (TableColumn<Music, String>) table.getColumns().get(1);
        singerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSinger()));

        TableColumn<Music, String> titleColumn = (TableColumn<Music, String>) table.getColumns().get(2);
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));


        TableColumn<Music, String> genreColumn = (TableColumn<Music, String>) table.getColumns().get(3);
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));

        table.setItems(music);
    }

    private void sendRequest() throws UnsupportedEncodingException {
        String result = model.send("http://localhost:8087/lab3/task2/information", "POST");
        ObservableList<Music> music = FXCollections.observableArrayList();
        Document html = Jsoup.parse(result);
        Element table1 = html.select("table").get(1);
        Elements tr = table1.getElementsByTag("tr");
        for (int i = 1; i < tr.size(); i++) {
            Element e = tr.get(i);
            Elements td = e.getElementsByTag("td");
            Music m = new Music(td.get(2).text(), td.get(1).text(), td.get(3).text(), td.get(0).text());
            music.add(m);
        }
        changeTable(music);
    }
}
