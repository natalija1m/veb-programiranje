package mk.finki.ukim.mk.lab1.repository;

import mk.finki.ukim.mk.lab1.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab1.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository {
    public List<Location> findAll(){
        return DataHolder.locations;
    }
    public Optional<Location> find_by_ID(Long id){
        return DataHolder.locations.stream().filter(i -> i.getId().equals(id)).findFirst();
    }
    public void delete_location(Long id){
        Optional<Location> location_to_delete = DataHolder.locations
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
        if(location_to_delete.isPresent()){
            Optional<Location> new_one = DataHolder.locations.stream().filter(i -> !i.getId().equals(id)).findFirst();
            new_one.ifPresent(loc -> {
                DataHolder.events.stream()
                        .filter(event -> event.getLocation().getId().equals(id))
                        .forEach(event -> event.setLocation(loc));
            });
            DataHolder.locations.removeIf(i -> i.getId().equals(id));
        }
    }
    public Optional<Location> save_new_location(String name, String address, String capacity, String description){
        Location new_location = new Location(name,address,capacity,description);
        DataHolder.locations.add(new_location);
        return Optional.of(new_location);
    }
}