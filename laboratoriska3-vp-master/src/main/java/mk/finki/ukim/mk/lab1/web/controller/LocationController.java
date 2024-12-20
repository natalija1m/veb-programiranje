package mk.finki.ukim.mk.lab1.web.controller;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.lab1.model.Location;
import mk.finki.ukim.mk.lab1.repository.EventRepository;
import mk.finki.ukim.mk.lab1.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository, EventRepository eventRepository) {
        this.locationRepository = locationRepository;
        this.eventRepository = eventRepository;
    }


    @GetMapping
    public String listLocations(Model model) {
        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);
        return "list";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("location", new Location());
        return "add";
    }


    @PostMapping("/add")
    public String addLocation(@ModelAttribute Location location) {
        locationRepository.save(location);
        return "redirect:/locations";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteLocation(@PathVariable Long id) {
        // First delete all events related to the location
        eventRepository.deleteAllByLocationId(id);

        // Then delete the location itself
        locationRepository.deleteById(id);

        return "redirect:/locations";
    }
}
