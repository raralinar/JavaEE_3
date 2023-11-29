package com.lab3.task1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.lab3.task1.LibraryServlet.readMusicFromXml;

@WebServlet("/task1/filter")
public class FilterServlet extends HttpServlet {
    List<Music> music = readMusicFromXml();

    public FilterServlet() throws ParserConfigurationException, IOException, SAXException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Music> filteredMusic = new ArrayList<>();
        String singer = req.getParameter("filterSinger");
        singer = singer.toUpperCase();
        for (Music m : music)
            if (m.getSinger().toUpperCase().contains(singer) || m.getSinger().equalsIgnoreCase(singer))
                filteredMusic.add(m);
        req.setAttribute("audioFiles", filteredMusic);
        RequestDispatcher dispatcher = req.getRequestDispatcher("lib.jsp");
        dispatcher.forward(req, resp);
    }
}
