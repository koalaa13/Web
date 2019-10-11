package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import ru.itmo.wp.util.Message;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageServlet extends HttpServlet {

    private final static String contentType = "application/json";
    private List<Message> messages = new ArrayList<>();

    private void makeResponse(HttpServletResponse response, Object data) throws IOException {
        String json = new Gson().toJson(data);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    private void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Object sessionUser = session.getAttribute("user");
        String requestUser = request.getParameter("user");
        String user = "";
        if (requestUser != null) {
            user = requestUser;
        } else {
            if (sessionUser != null) {
                user = String.valueOf(sessionUser);
            }
        }
        session.setAttribute("user", user);
        makeResponse(response, user);
    }

    private void findAll(HttpServletResponse response) throws IOException {
        makeResponse(response, messages);
    }

    private void add(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String user = String.valueOf(session.getAttribute("user"));
        String text = request.getParameter("text");
        messages.add(new Message(user, text));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(contentType);
        String requestURI = request.getRequestURI();
        if ("/message/auth".equals(requestURI)) {
            auth(request, response);
        }
        if ("/message/findAll".equals(requestURI)) {
            findAll(response);
        }
        if ("/message/add".equals(requestURI)) {
            add(request);
        }
    }
}
