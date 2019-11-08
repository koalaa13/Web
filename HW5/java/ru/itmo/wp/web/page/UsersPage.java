package ru.itmo.wp.web.page;

import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class UsersPage extends Page {
    private void action(Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
    }

    @Override
    protected void after(HttpServletRequest request, Map<String, Object> view) {
        super.after(request, view);
    }
}
