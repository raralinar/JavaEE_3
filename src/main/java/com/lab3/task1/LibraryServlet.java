package com.lab3.task1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet("/task1/information")
public class LibraryServlet extends HttpServlet {
    List<Music> music = readMusicFromXml();
    List<Integer> ids = createId();
    String URL ="/Users/alinavoronina/Downloads/course_3.1/OOP_Java/Lab_3/src/main/webapp/task1/music.xml";

    public LibraryServlet() throws ParserConfigurationException, IOException, SAXException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("audioFiles", music);
        request.getRequestDispatcher("lib.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("add")) {
            String singer = request.getParameter("singer");
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            String id = Integer.toString(randomID());
            music.add(new Music(title,singer,genre,id));
            addMusicToXml(singer, title, genre, id);
        } else if (action.equals("delete")) {
            String id = request.getParameter("id");
            try {
                for (Music m : music)
                    if (m.getId().equals(id)) {
                        music.remove(m);
                        break;
                    }
                deleteMusicFromXml(id);
            } catch (SAXException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        request.setAttribute("audioFiles", music);
        request.getRequestDispatcher("lib.jsp").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/task1/lib.jsp");
    }
    private List<Integer> createId() {
        List<Integer> i = new ArrayList<>();
        for (Music m : music)
            i.add(Integer.parseInt(m.getId()));
        return i;
    }

    private int randomID() {
        int id = new Random().nextInt(0, 1000);
        while (ids.contains(id)) {
            id = new Random().nextInt(0, 1000);
        }
        return id;
    }

    public static List<Music> readMusicFromXml() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("/Users/alinavoronina/Downloads/course_3.1/OOP_Java/Lab_3/src/main/webapp/task1/music.xml");
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

    private void addMusicToXml(String singer, String title, String genre, String id) {
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

    private void deleteMusicFromXml(String id) throws IOException, SAXException, ParserConfigurationException {
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
}

