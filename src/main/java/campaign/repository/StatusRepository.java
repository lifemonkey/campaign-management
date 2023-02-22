package campaign.repository;

import campaign.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByNameIgnoreCase(String name);

    Page<Status> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
