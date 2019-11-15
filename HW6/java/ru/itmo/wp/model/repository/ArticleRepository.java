package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    Article find(long id);
    List<Article> findAllByUsedId(long usedId);
    List<Article> findAll();
    void save(Article article);
    void setHiddenProp(long id, boolean hidden);
}
