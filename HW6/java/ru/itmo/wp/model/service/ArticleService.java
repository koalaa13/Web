package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private final UserService userService = new UserService();

    public static class ArticleView {
        ArticleView(String title, String text, Date creationTime, String login) {
            this.title = title;
            this.text = text;
            this.creationTime = creationTime;
            this.login = login;
        }

        private String title;
        private String text;
        private Date creationTime;
        private String login;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setCreationTime(Date creationTime) {
            this.creationTime = creationTime;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getText() {
            return text;
        }

        public Date getCreationTime() {
            return creationTime;
        }

        public String getLogin() {
            return login;
        }
    }

    public void validateArticle(String title, String text) throws ValidationException {
        if (Strings.isNullOrEmpty(title)) {
            throw new ValidationException("Title can't be empty");
        }

        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Text can't be empty");
        }

        if (title.length() > 100) {
            throw new ValidationException("Title is too long");
        }

        if (text.length() > 10000) {
            throw new ValidationException("Text is too long");
        }

    }

    public void validateChangeArticle(User user, Article article) throws ValidationException {
        if (user.getId() != article.getUserId()) {
            throw new ValidationException("You can't change this article, you are not creator of it.");
        }
    }

    public List<Article> findAllByUserId(long userId) {
        return articleRepository.findAllByUsedId(userId);
    }

    public void setHiddenProp(long id, boolean hidden) {
        articleRepository.setHiddenProp(id, hidden);
    }

    public void insert(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<ArticleView> findAllArticleViews() {
        List<ArticleView> res = new ArrayList<>();
        List<Article> articles = articleRepository.findAll();
        for (Article cur : articles) {
            if (!cur.isHidden()) {
                res.add(new ArticleView(cur.getTitle(), cur.getText(), cur.getCreationTime(), userService.find(cur.getUserId()).getLogin()));
            }
        }
        return res;
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }
}
