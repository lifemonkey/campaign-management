package campaign.repository;

import campaign.domain.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RuleRepository extends JpaRepository<Rule, Long> {

    Page<Rule> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Rule> findAllByCampaignType(Integer campaignType, Pageable pageable);

    Page<Rule> findAllByNameContainingIgnoreCaseAndCampaignType(String name, Integer campaignType, Pageable pageable);

    Optional<Rule> findByNameIgnoreCase(String name);

    List<Rule> findByNameStartsWithIgnoreCase(String name);
}
