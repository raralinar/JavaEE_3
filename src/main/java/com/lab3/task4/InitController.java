package com.lab3.task4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet(name = "InitController", value = "/task4/start")
public class InitController extends HttpServlet {
    String URL = "/task4/index.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession(true);
        Model model = new Model();
        Map<Integer, Sign> fieldData = model.getField();
        List<Sign> data = model.getModelData();
        currentSession.setAttribute("field", model);
        currentSession.setAttribute("data", data);
        getServletContext().getRequestDispatcher(URL).forward(req, resp);
    }
}