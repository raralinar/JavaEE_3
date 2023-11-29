package com.lab3.task4;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "Logic", value = "/task4/logic")
public class Model extends HttpServlet {
    String URL = "/lab3/task4/index.jsp";
    private final Map<Integer, Sign> field;
    public Model() {
        field = new HashMap<>();
        field.put(0, Sign.EMPTY);
        field.put(1, Sign.EMPTY);
        field.put(2, Sign.EMPTY);
        field.put(3, Sign.EMPTY);
        field.put(4, Sign.EMPTY);
        field.put(5, Sign.EMPTY);
        field.put(6, Sign.EMPTY);
        field.put(7, Sign.EMPTY);
        field.put(8, Sign.EMPTY);
    }
    public Sign getEmpty(){return Sign.EMPTY;}
    public Sign getCross(){return Sign.CROSS;}
    public Sign getNought(){return Sign.NOUGHT;}
    public Map<Integer, Sign> getField() {
        return field;
    }

    public int getEmptyModelIndex() {
        return field.entrySet().stream()
                .filter(e -> e.getValue() == Sign.EMPTY)
                .map(Map.Entry::getKey)
                .findFirst().orElse(-1);
    }

    public List<Sign> getModelData() {
        return field.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public Sign checkWin() {
        List<List<Integer>> winPossibilities = List.of(
                List.of(0, 1, 2),
                List.of(3, 4, 5),
                List.of(6, 7, 8),
                List.of(0, 3, 6),
                List.of(1, 4, 7),
                List.of(2, 5, 8),
                List.of(0, 4, 8),
                List.of(2, 4, 6)
        );
        for (List<Integer> winPossibility : winPossibilities) {
            if (field.get(winPossibility.get(0)) == field.get(winPossibility.get(1))
                    && field.get(winPossibility.get(0)) == field.get(winPossibility.get(2))
                    && field.get(winPossibility.get(0)) != Sign.EMPTY) {
                return field.get(winPossibility.get(0));
            }
        }
        return Sign.EMPTY;
    }

     boolean checkWinController(HttpServletResponse response, HttpSession currentSession, Model model) throws IOException {
        Sign winner = model.checkWin();
        if (Sign.CROSS == winner || Sign.NOUGHT == winner) {
            currentSession.setAttribute("winner", winner);
            List<Sign> data = model.getModelData();
            currentSession.setAttribute("data", data);
            response.sendRedirect(URL);
            return true;
        }
        return false;
    }

     int getSelectedIndex(HttpServletRequest request) {
        String click = request.getParameter("click");
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(click) : 0;
    }

     Model extractModel(HttpSession currentSession) {
        Object fieldAttribute = currentSession.getAttribute("field");
        if (Model.class != fieldAttribute.getClass()) {
            currentSession.invalidate();
            throw new RuntimeException("Session is broken, try one more time");
        }
        return (Model) fieldAttribute;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        Model model =this.extractModel(currentSession);

        int index = model.getSelectedIndex(req);
        Sign currentSign = model.getField().get(index);

        if (Sign.EMPTY != currentSign) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(URL);
            dispatcher.forward(req, resp);
            return;
        }

        model.getField().put(index, Sign.CROSS);

        if (model.checkWinController(resp, currentSession, model)) {
            return;
        }

        int emptyFieldIndex = model.getEmptyModelIndex();

        if (emptyFieldIndex >= 0) {
            model.getField().put(emptyFieldIndex, Sign.NOUGHT);
            if (model.checkWinController(resp, currentSession, model)) {
                return;
            }
        }
        else {
            currentSession.setAttribute("draw", true);
            List<Sign> data = model.getModelData();
            currentSession.setAttribute("data", data);
            resp.sendRedirect(URL);
            return;
        }

        List<Sign> data = model.getModelData();
        currentSession.setAttribute("data", data);
        currentSession.setAttribute("field", model);

        resp.sendRedirect(URL);
    }
}