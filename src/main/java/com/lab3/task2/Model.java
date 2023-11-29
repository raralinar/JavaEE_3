package com.lab3.task2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
    private static final List<Music> musicList;

    static {
        try {
            musicList = readMusicFromXml();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private static final List<Integer> ids = fillIDs();
    private static final String URL = "/Users/alinavoronina/Downloads/course_3.1/OOP_Java/Lab_3/src/main/webapp/task2/music.xml";
    public static List<Music> readMusicFromXml() throws ParserConfigurationException, IOException, SAXException {
        File file = new File(URL);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("track");
        List<Music> list = new ArrayList<>();
        for (int itr = 0; itr < nodeList.getLength(); itr++)
        {
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                Music music = new Music(
                        eElement.getElementsByTagName("title").item(0).getTextContent(),
                        eElement.getElementsByTagName("singer").item(0).getTextContent(),
                        eElement.getElementsByTagName("genre").item(0).getTextContent(),
                        eElement.getElementsByTagName("id").item(0).getTextContent());
                list.add(music);
            }
        }
        return list;
    }

    public List<Music> getFilteredMusic(String singer) {
        List<Music> filteredMusic = new ArrayList<>();
        singer = singer.toUpperCase();
        for (Music m : musicList)
            if (m.getSinger().toUpperCase().contains(singer))
                filteredMusic.add(m);
        return filteredMusic;
    }

    public int randomID() {
        int id = new Random().nextInt(0, 1000);
        while (ids.contains(id)) {
            id = new Random().nextInt(0, 1000);
        }
        return id;
    }

    public void addMusicToXml(String singer, String title, String genre, String id) {
        try {
            File xmlFile = new File(URL);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            Element root = document.getDocumentElement();

            Element newServer = document.createElement("track");

            Element id1 = document.createElement("id");
            id1.appendChild(document.createTextNode(id));
            newServer.appendChild(id1);
            Element singer1 = document.createElement("singer");
            singer1.appendChild(document.createTextNode(singer));
            newServer.appendChild(singer1);
            Element title1 = document.createElement("title");
            title1.appendChild(document.createTextNode(title));
            newServer.appendChild(title1);
            Element genre1 = document.createElement("genre");
            genre1.appendChild(document.createTextNode(genre));
            newServer.appendChild(genre1);

            root.appendChild(newServer);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(URL);
            transformer.transform(source, result);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void deleteMusicFromXml(String id) throws IOException, SAXException, ParserConfigurationException {
        File xmlFile = new File(URL);
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            NodeList products = document.getElementsByTagName("track");
            for (int i = 0; i < products.getLength(); i++) {
                Element product = (Element) products.item(i);
                Element idTag = (Element) product.getElementsByTagName("id").item(0);
                if (idTag.getTextContent().equals(id)) {
                    idTag.getParentNode().getParentNode().removeChild(products.item(i));
                    break;
                }
            }
            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(URL);
            transformer.transform(source, result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Integer> getIds() {
       return ids;
    }
    private static List<Integer> fillIDs() {
        List<Integer> i = new ArrayList<>();
        for (Music m : musicList)
            i.add(Integer.parseInt(m.getId()));
        return i;
    }
}
