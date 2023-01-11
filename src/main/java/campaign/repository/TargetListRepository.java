package campaign.repository;

import campaign.domain.TargetList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetListRepository extends JpaRepository<TargetList, Long> {

    Page<TargetList> findAllByNameContaining(String name, Pageable pageable);

    Page<TargetList> findAllByTargetType(Integer targetType, Pageable pageable);

    Page<TargetList> findAllByNameContainingAndTargetType(String name, Integer targetType, Pageable pageable);
}
