package campaign.repository;

import campaign.domain.Campaign;
import campaign.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findAllByNameContainingIgnoreCase(String name);

    List<Campaign> findAllByCampaignType(Integer campaignType);

    List<Campaign> findAllByNameContainingIgnoreCaseAndCampaignType(String name, Integer campaignType);

    List<Campaign> findAllByStatus(Status status);

    Optional<Campaign> findByNameIgnoreCase(String name);

    List<Campaign> findByNameStartsWithIgnoreCase(String name);
}
