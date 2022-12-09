package campaign.repository;

import campaign.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Long> {

    List<Rule> findByCampaignId(Integer campaignId);

}
