package campaign.repository;

import campaign.domain.RuleConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RuleConfigurationRepository extends JpaRepository<RuleConfiguration, Long> {

    Optional<RuleConfiguration> findByName(String name);
}
