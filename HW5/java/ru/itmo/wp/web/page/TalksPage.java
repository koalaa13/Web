package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            putMessage(request, view);
            view.put("user", user);
            List<User> users = userService.findAll();
            users.remove(user);
            view.put("users", users);
            List<Talk> usersTalks = talksService.findAllByUserId(user.getId());
            List<String> sendersLogins = new ArrayList<>();
            List<String> recipientsLogins = new ArrayList<>();
            for (Talk curTalk : usersTalks) {
                if (curTalk.getSourceUserId() == user.getId()) {
                    sendersLogins.add(user.getLogin());
                    recipientsLogins.add(userService.findById(curTalk.getTargetUserId()).getLogin());
                } else {
                    sendersLogins.add(userService.findById(curTalk.getSourceUserId()).getLogin());
                    recipientsLogins.add(user.getLogin());
                }
            }
            view.put("talks", usersTalks);
            view.put("sendersLogins", sendersLogins);
            view.put("recipientsLogins", recipientsLogins);
        } else {
            setMessage("Talks are available only for logged users");
            throw new RedirectException("/index");
        }
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        action(request, view);
        talksService.validateTalk(request.getParameter("text"), request.getParameter("recipient"));
        Talk talk = new Talk();
        talk.setText(request.getParameter("text"));
        talk.setSourceUserId(getUser().getId());
        talk.setTargetUserId(Long.parseLong(request.getParameter("recipient")));
        talksService.addTalk(talk);

        setMessage("Message sent!");
        throw new RedirectException("/talks");
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
