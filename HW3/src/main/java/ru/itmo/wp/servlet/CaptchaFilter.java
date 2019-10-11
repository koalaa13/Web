package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {

    private static final Random random = new Random();
    private static final String beginForm = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "</head>" +
            "<body>" +
            "<img src=\"data:image/png;base64, ";
    private static final String endForm = "\"/>" +
            "<form method=\"get\" action=\"\">" +
            "<input name=\"captcha\" id=\"captcha\"/>" +
            "</form>" +
            "</body>" +
            "</html>";

    private Integer genCode() {
        return random.nextInt(900) + 100;
    }

    private String getCaptchaForm(Integer code) {
        String encodedImage = Base64.getEncoder().encodeToString(ImageUtils.toPng(code.toString()));
        return beginForm + encodedImage + endForm;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String captcha = request.getParameter("captcha");
        if (captcha != null) {
            Object rightAnswer = session.getAttribute("code");
            if (rightAnswer != null && rightAnswer.equals(captcha)) {
                session.setAttribute("passedCaptcha", "true");
                response.sendRedirect(request.getRequestURI());
                return;
            }
        }
        Object captchaResult = session.getAttribute("passedCaptcha");
        if (captchaResult == null) {
            session.setAttribute("passedCaptcha", "false");
        }
        if (session.getAttribute("passedCaptcha").equals("false")) {
            DelayedHttpServletResponse delayedResponse = new DelayedHttpServletResponse(response);
            Integer code = genCode();
            session.setAttribute("code", code.toString());
            chain.doFilter(request, delayedResponse);
            response.getWriter().print(getCaptchaForm(code));
            response.getWriter().flush();
        } else {
            chain.doFilter(request, response);
        }
    }
}
