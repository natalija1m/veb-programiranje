package mk.finki.ukim.mk.lab1.repository;


import mk.finki.ukim.mk.lab1.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab1.model.EventBooking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventBookingRepository {

    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets) {
        EventBooking eventBooking = new EventBooking(eventName, attendeeName, attendeeAddress, (long) numberOfTickets);
        DataHolder.eventBookings.add(eventBooking);
        return eventBooking;
    }
    public List<EventBooking> search(String text) {
        return DataHolder.eventBookings.stream()
                .filter(booking -> booking.getAttendeeName().equals(text)).collect(Collectors.toList());
    }
}