package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface TalksRepository extends BasicRepository<Talk> {
    List<Talk> findAllByUserId(long id);
}
