package mk.finki.ukim.mk.lab1.bootstrap;


import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab1.model.Event;
import mk.finki.ukim.mk.lab1.model.EventBooking;
import mk.finki.ukim.mk.lab1.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Event> events = new ArrayList<>();
    public static List<Location> locations = new ArrayList<>();
    public static List<EventBooking> eventBookings;

    @PostConstruct
    public void init() {
        locations.add(new Location("l1","a1","100","d1"));
        locations.add(new Location("l2","a2", "200","d2"));
        locations.add(new Location("l3","a3","300","d3"));
        locations.add(new Location("l4","a4","400","d4"));

        eventBookings=new ArrayList<>();
        events.add(new Event("e1", "d1", 7.8,locations.get(2)));
        events.add(new Event("e2", "d2", 6.4,locations.get(3)));
        events.add(new Event("e3", "d3", 8.1,locations.get(0)));
        events.add(new Event("e4", "d4", 5.7,locations.get(3)));
        events.add(new Event("e5", "d5", 6.9,locations.get(2)));
        events.add(new Event("e6", "d6", 7.3,locations.get(2)));
        events.add(new Event("e7", "d7", 7.0,locations.get(1)));
        events.add(new Event("e8", "dd8", 6.6, locations.get(0)));
        events.add(new Event("e9", "d9", 5.5, locations.get(1)));
    }

}