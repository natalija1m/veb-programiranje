package mk.finki.ukim.mk.lab1.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.finki.ukim.mk.lab1.model.Event;
import mk.finki.ukim.mk.lab1.model.EventBooking;
import mk.finki.ukim.mk.lab1.service.EventBookingService;
import mk.finki.ukim.mk.lab1.service.EventService;
import mk.finki.ukim.mk.lab1.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;
    private final EventBookingService eventBookingService;
//    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, LocationService locationService, EventBookingService eventBookingService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.eventBookingService = eventBookingService;
    }
@GetMapping
public String getEventsPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) Long locationId,
                            Model model,
                            HttpSession session) {

    // Check if user is stored in the session


    // Fetch events based on location filter
    if (locationId != null) {
        model.addAttribute("events", eventService.findAllByLocation_Id(locationId));
    } else {
        model.addAttribute("events", eventService.listAll());
    }

    // Add all locations to the model for the location filter dropdown
    model.addAttribute("locations", locationService.findALl());

    return "listEvents";
}


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveEvent(@RequestParam(required = false) Long eventId,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam String popularityScore,
                            @RequestParam Long locationId,
                            RedirectAttributes redirectAttributes) {

        System.out.println("Saving event with the following details:");
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Popularity Score: " + popularityScore);
        System.out.println("Location ID: " + locationId);

        double parsedPopularityScore;
        try {
            parsedPopularityScore = Double.parseDouble(popularityScore);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("status", "no");
            redirectAttributes.addFlashAttribute("errorMessage", "Popularity Score must be a number.");
            return "redirect:/events/add-form";
        }
        if (name == null || name.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("status", "no");
            redirectAttributes.addFlashAttribute("errorMessage", "Event name cannot be empty.");
            return "redirect:/events/add-form";
        }

        if (description == null || description.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("status", "no");
            redirectAttributes.addFlashAttribute("errorMessage", "Event description cannot be empty.");
            return "redirect:/events/add-form";
        }

        if (parsedPopularityScore < 0) {
            redirectAttributes.addFlashAttribute("status", "no");
            redirectAttributes.addFlashAttribute("errorMessage", "Popularity score must be a positive number.");
            return "redirect:/events/add-form";
        }

        if (eventService.eventNameAndLocationExists(name, locationId)) {
            redirectAttributes.addFlashAttribute("status", "no");
            redirectAttributes.addFlashAttribute("errorMessage", "Event with this name and location already exists.");
            return "redirect:/events/add-form";
        }
        try {
            eventService.saveEvent(name, description, parsedPopularityScore, locationId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("status", "no");
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while saving the event.");
            e.printStackTrace();  // Log the stack trace for debugging
            return "redirect:/events/add-form";
        }

        return "redirect:/events";
    }


    @PostMapping("/edit/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editEvent(@PathVariable Long eventId,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false) String popularityScore,
                            @RequestParam(required = false) Long locationId) {
        double parsedPopularityScore = Double.parseDouble(null);
        if (popularityScore != null) {
            try {
                parsedPopularityScore = Integer.parseInt(popularityScore);
            } catch (NumberFormatException e) {
                return "redirect:/events?error=InvalidPopularityScore";
            }
        }
        eventService.updateEvent(eventId, name, description, parsedPopularityScore, locationId);
        return "redirect:/events";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

    @GetMapping("/edit-form/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditEventForm(@PathVariable Long eventId, Model model) {

        Event event = eventService.findById(eventId).orElse(null);
        if (event == null) {
            return "redirect:/events?error=EventNotFound";
        }
        model.addAttribute("event", event);
        model.addAttribute("locations", locationService.findALl());
        return "add-event";
    }

    @GetMapping("add-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddEventPage(Model model) {
        model.addAttribute("locations", locationService.findALl());
        return "add-event";
    }

//    @GetMapping("/eventBooking")
//    public String bookEvent(@RequestParam String name,
//                            @RequestParam String attendeeName,
//                            @RequestParam Integer numTickets,
//                            HttpServletRequest request,
//                            Model model) {
//        try {
//            String attendeeAddress=request.getRemoteAddr();
//            EventBooking booking = eventBookingService.placeBooking(name, attendeeName, attendeeAddress, numTickets, request);
//
//            // Add booking details to the model
//            model.addAttribute("booking", booking);
//
//            return "bookingConfirmation";
//        } catch (Exception e) {
//            return "redirect:/events?error=BookingFailed";
//        }
//    }
    @PostMapping("/eventBooking")
    public String bookEvent(@RequestParam String name,
                            @RequestParam String attendeeName,
                            @RequestParam Integer numTickets,
                            HttpServletRequest request,
                            Model model) {
        System.out.println("Booking Event: " + name + ", Attendee: " + attendeeName + ", Tickets: " + numTickets);

        try {
            String attendeeAddress = request.getRemoteAddr();
            EventBooking booking = eventBookingService.placeBooking(name, attendeeName, attendeeAddress, numTickets, request);

            model.addAttribute("booking", booking);
            return "bookingConfirmation";
        } catch (Exception e) {
            return "redirect:/events?error=BookingFailed";
        }
    }
}

