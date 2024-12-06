package mk.finki.ukim.mk.lab1.service.impl;


import mk.finki.ukim.mk.lab1.model.EventBooking;
import mk.finki.ukim.mk.lab1.repository.EventBookingRepository;
import mk.finki.ukim.mk.lab1.service.EventBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EventBookingServiceImpl implements EventBookingService {

    private final EventBookingRepository eventBookingRepository;

    public EventBookingServiceImpl(EventBookingRepository eventRepository) {
        this.eventBookingRepository = eventRepository;
    }

    @Override
    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets) {
        return eventBookingRepository.placeBooking(eventName,attendeeName,attendeeAddress,numberOfTickets);
    }

    @Override
    public List<EventBooking> searchByText(String text) {
        return eventBookingRepository.search(text) ;
    }
}