package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impls.EventRepositoryImpl;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public void addEvent(Event event) {
        eventRepository.save(event);
    }
}
