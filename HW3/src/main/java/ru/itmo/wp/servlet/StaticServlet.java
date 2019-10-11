package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] uris = request.getRequestURI().split("[+]");
        final String staticData = getServletContext().getRealPath(".") + "/../main/webapp/static";
        for (String uri : uris) {
            File file = new File(staticData, uri);
            if (file.isFile()) {
                makeResponse(response, file);
            } else {
                file = new File(getServletContext().getRealPath("/static"), uri);
                if (file.isFile()) {
                    makeResponse(response, file);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

    private void makeResponse(HttpServletResponse response, File file) throws IOException {
        if (response.getContentType() == null) {
            response.setContentType(getContentTypeFromName(file.getPath()));
        }
        OutputStream outputStream = response.getOutputStream();
        Files.copy(file.toPath(), outputStream);
        outputStream.flush();
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
