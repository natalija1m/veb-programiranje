package mk.finki.ukim.mk.lab1.service;


import mk.finki.ukim.mk.lab1.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();
    public Event saveEvent(Event event);
    public void deleteEvent(Long id);
    public Optional<Event> findById(Long id);
    List<Event> searchEvents(String text);
    List<Event> findAllByLocation_Id(Long id);
    Optional<Event> saveEvent(String name, String description, double popularityScore, Long locationId);
    Event updateEvent(Long eventId, String name, String description, double popularityScore, Long locationId);
    boolean eventNameAndLocationExists(String name, Long locationId);
    public boolean eventNameExists(String eventName);
    public boolean locationIdExists(Long locationId);
//    void deleteEvent(Long eventId);
}