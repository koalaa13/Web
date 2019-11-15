package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    private ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("message", "You should login to see your articles");
            throw new RedirectException("/index");
        }
    }

    private void findAllByUserId(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        view.put("myArticles", articleService.findAllByUserId(user.getId()));
    }

    private void changeHiddenProp(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");
        long id = Long.parseLong(request.getParameter("articleId"));
        Article articleToChange = articleService.find(id);
        articleService.validateChangeArticle(user, articleToChange);
        boolean cur = articleToChange.isHidden();
        articleService.setHiddenProp(id, !cur);
        view.put("currentHidProp", !cur);
    }
}
