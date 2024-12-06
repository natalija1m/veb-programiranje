package mk.finki.ukim.mk.lab1.service;

import mk.finki.ukim.mk.lab1.model.Event;
import mk.finki.ukim.mk.lab1.model.Location;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


public interface LocationService {
    public List<Location> findAll();
    void delete(Long id);
    Optional<Location> save_location(String name, String address, String capacity, String description);
}