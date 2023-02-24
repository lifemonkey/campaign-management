package campaign.repository;

import campaign.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RuleRepository extends JpaRepository<Rule, Long> {

    List<Rule> findAllByNameContainingIgnoreCase(String name);

    List<Rule> findAllByCampaignType(Integer campaignType);

    List<Rule> findAllByNameContainingIgnoreCaseAndCampaignType(String name, Integer campaignType);

    Optional<Rule> findByNameIgnoreCase(String name);

    List<Rule> findByNameStartsWithIgnoreCase(String name);
}
