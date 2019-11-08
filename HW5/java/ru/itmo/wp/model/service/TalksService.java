package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalksRepository;
import ru.itmo.wp.model.repository.impls.TalksRepositoryImpl;

import java.util.List;

public class TalksService {
    private final TalksRepository talksRepository = new TalksRepositoryImpl();

    public void validateTalk(String text, String recipientId) throws ValidationException {
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Message can not be empty");
        }
        try {
            long id = Long.parseLong(recipientId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect recipient");
        }
    }

    public void addTalk(Talk talk) {
        talksRepository.save(talk);
    }

    public List<Talk> findAllByUserId(long id) {
        return talksRepository.findAllByUserId(id);
    }
}
