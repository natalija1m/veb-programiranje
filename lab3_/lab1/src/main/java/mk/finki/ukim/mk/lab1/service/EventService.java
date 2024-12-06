package mk.finki.ukim.mk.lab1.service;

import mk.finki.ukim.mk.lab1.model.Event;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();
    List<Event> searchEvents(String text);
    Optional<Event> save_event(Long id,String name, String description, double popularityScore, Long locationID);
    void delete(Long id);
    Optional<Event> findEvent(Long id);
}