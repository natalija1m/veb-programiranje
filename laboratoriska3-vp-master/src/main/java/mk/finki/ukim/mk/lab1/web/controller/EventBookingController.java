package mk.finki.ukim.mk.lab1.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.finki.ukim.mk.lab1.service.EventBookingService;
import mk.finki.ukim.mk.lab1.model.EventBooking;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class EventBookingController {

    private final EventBookingService eventBookingService;


    public EventBookingController(EventBookingService eventBookingService) {
        this.eventBookingService = eventBookingService;
    }


    @GetMapping("/eventBooking")
    public String bookEvent(@RequestParam String name,
                            @RequestParam String attendeeName,
                            @RequestParam Integer numTickets,
                            HttpServletRequest request,
                            Model model) {
        String attendeeAddress=request.getRemoteAddr();
        try {
            // Place the booking
            EventBooking booking = eventBookingService.placeBooking(name, attendeeName, attendeeAddress, numTickets, request);

            // Add attributes to the model
            model.addAttribute("ipAddress",attendeeAddress);
            model.addAttribute("booking", booking);

            return "bookingConfirmation";  // Return the booking confirmation page
        } catch (Exception e) {
            return "redirect:/events?error=BookingFailed";
        }
    }

    @GetMapping("/searchBookings")
    public String searchBookings(@RequestParam String query, Model model) {
        List<EventBooking> results = eventBookingService.searchByText(query);
        model.addAttribute("results", results);
        return "searchResults";  // The view template to display the results
    }

}
