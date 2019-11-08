package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.TalksService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    final UserService userService = new UserService();
    private final EventService eventService = new EventService();
    final TalksService talksService = new TalksService();

    private HttpServletRequest mainRequest;

    void addEvent(Event.Type type) {
        Event event = new Event();
        event.setType(type);
        event.setUserId(getUser().getId());
        eventService.addEvent(event);
    }

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        mainRequest = request;
        putUser(request, view);
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findUserCount());
    }

    private void putUser(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    void setMessage(String message) {
        if (!Strings.isNullOrEmpty(message)) {
            mainRequest.getSession().setAttribute("message", message);
        }
    }

    User getUser() {
        return (User) mainRequest.getSession().getAttribute("user");
    }

    void setUser(User user) {
        mainRequest.getSession().setAttribute("user", user);
    }

    void removeUser() {
        mainRequest.getSession().removeAttribute("user");
    }

    protected void action() {
        // No operations.
    }
}
