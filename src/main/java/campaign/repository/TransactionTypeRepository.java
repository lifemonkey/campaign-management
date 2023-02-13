package campaign.repository;

import campaign.domain.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    Optional<TransactionType> findByNameIgnoreCase(String name);

    Page<TransactionType> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
