package mk.finki.ukim.mk.lab1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import mk.finki.ukim.mk.lab1.model.EventBooking;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventBookingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Save or update the EventBooking entity
    public EventBooking save(EventBooking eventBooking) {
        if (eventBooking.getId() == null) {
            entityManager.persist(eventBooking);
        } else {
            eventBooking = entityManager.merge(eventBooking);
        }
        return eventBooking;
    }

//    // Updated findByUserId method - assuming you have user_id in EventBooking
//    public List<EventBooking> findByUserId(Long userId) {
//        // Assuming there is a 'user_id' field in EventBooking table
//        String query = "SELECT e FROM EventBooking e WHERE e.userId = :userId"; // Adjust to use userId directly
//        TypedQuery<EventBooking> typedQuery = entityManager.createQuery(query, EventBooking.class);
//        typedQuery.setParameter("userId", userId);
//        return typedQuery.getResultList();
//    }

    // Search by text method remains the same
    public List<EventBooking> searchByText(String text) {
        String query = "SELECT eb FROM EventBooking eb " +
                "WHERE eb.eventName LIKE :text " +
                "OR eb.attendeeName LIKE :text " +
                "OR eb.attendeeAddress LIKE :text";
        TypedQuery<EventBooking> typedQuery = entityManager.createQuery(query, EventBooking.class);
        typedQuery.setParameter("text", "%" + text + "%");
        return typedQuery.getResultList();
    }
}
