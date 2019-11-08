package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class RegisterPage extends Page {
    private void register(HttpServletRequest request) throws ValidationException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        String email = request.getParameter("email");
        user.setEmail(email);

        userService.validateRegistration(user, password, passwordConfirmation);
        userService.register(user, password);

        setMessage("You are successfully registered!");
        throw new RedirectException("/index");
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
