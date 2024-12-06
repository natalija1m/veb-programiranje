package mk.finki.ukim.mk.lab1.service.impl;

import mk.finki.ukim.mk.lab1.model.Location;
import mk.finki.ukim.mk.lab1.repository.LocationRepository;
import mk.finki.ukim.mk.lab1.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    public final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAll() {return locationRepository.findAll();}

    @Override
    public void delete(Long id) {
        this.locationRepository.delete_location(id);
    }

    @Override
    public Optional<Location> save_location(String name, String address, String capacity, String description) {
        return this.locationRepository.save_new_location(name,address,capacity,description);
    }
}