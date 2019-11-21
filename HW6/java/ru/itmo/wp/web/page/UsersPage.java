package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("loggedUser", request.getSession().getAttribute("user"));
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void changeAdminProperty(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User curUser = (User) request.getSession().getAttribute("user");
        userService.validateChangeAdminProp(curUser);
        long id = Long.parseLong(request.getParameter("userToChange"));
        boolean newValue = Boolean.parseBoolean(request.getParameter("newValue"));
        User userToChange = userService.find(id);
        if (userToChange.isAdmin() != newValue) {
            userService.setAdmin(id, newValue);
        }
        view.put("currentAdminProp", newValue);
    }
}
