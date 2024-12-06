package mk.finki.ukim.mk.lab1.repository;

import mk.finki.ukim.mk.lab1.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab1.model.Event;
import mk.finki.ukim.mk.lab1.model.Location;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {
    public List<Event> findAll(){
        return DataHolder.events;
    }

    public List<Event> searchEvents(String text){
        return DataHolder.events
                .stream()
                .filter(event -> event.getName().contains(text) || event.getDescription().contains(text))
                .toList();
    }
    public Optional<Event> save_event(Long id, String name, String description, Double popularityScore, Location location) {
        Optional<Event> existingEvent = find_by_id(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            event.setName(name);
            event.setDescription(description);
            event.setPopularityScore(popularityScore);
            event.setLocation(location);
            return Optional.of(event);
        } else {
            Event new_event = new Event(name, description, popularityScore, location);
            DataHolder.events.add(new_event);  // Add new event if not found
            return Optional.of(new_event);
        }
    }

    public void delete_by_given_id(Long id){
        boolean k = DataHolder.events.removeIf(i -> i.getId().equals(id));
        System.out.println(String.format("I deleted: %b with id %d",k,id));
    }

    public Optional<Event> find_by_id(Long id) {
        return DataHolder.events.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}