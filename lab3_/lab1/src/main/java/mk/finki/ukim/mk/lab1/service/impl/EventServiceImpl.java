package mk.finki.ukim.mk.lab1.service.implementation;

import mk.finki.ukim.mk.lab1.model.Event;
import mk.finki.ukim.mk.lab1.model.Location;
import mk.finki.ukim.mk.lab1.repository.EventRepository;
import mk.finki.ukim.mk.lab1.repository.LocationRepository;
import mk.finki.ukim.mk.lab1.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    public final EventRepository eventRepository;
    public final LocationRepository locationRepository;


    public EventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text) {
        if (text.isEmpty()) throw new IllegalArgumentException("I cant search without any text!");
        return eventRepository.searchEvents(text);
    }

    @Override
    public Optional<Event> save_event(Long id,String name, String description, double popularityScore, Long locationID) {
        Optional<Location> location_for_saving = locationRepository.find_by_ID(locationID);
        return this.eventRepository.save_event(id,name,description,popularityScore, location_for_saving.orElse(null));
    }

    @Override
    public void delete(Long id) {
        this.eventRepository.delete_by_given_id(id);
    }

    @Override
    public Optional<Event> findEvent(Long id) {
        return eventRepository.find_by_id(id);
    }
}