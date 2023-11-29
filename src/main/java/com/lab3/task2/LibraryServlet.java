package com.lab3.task2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@WebServlet("/task2/information")
public class LibraryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Model model = new Model();
        if (action.equals("add")) {
            String singer = request.getParameter("singer");
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            String id = Integer.toString(model.randomID());
            model.addMusicToXml(singer, title, genre, id);
        } else if (action.equals("delete")) {
            String id = request.getParameter("id");
            try {
                model.deleteMusicFromXml(id);
            } catch (SAXException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            request.setAttribute("audioFiles", Model.readMusicFromXml());
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("lib.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("audioFiles", Model.readMusicFromXml());
            request.getRequestDispatcher("lib.jsp").forward(request, response);
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}

