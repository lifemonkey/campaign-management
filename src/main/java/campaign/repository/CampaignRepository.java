package campaign.repository;

import campaign.domain.Campaign;
import campaign.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Page<Campaign> findAllByNameContaining(String name, Pageable pageable);

    Page<Campaign> findAllByCampaignType(Integer campaignType, Pageable pageable);

    Page<Campaign> findAllByNameContainingAndCampaignType(String name, Integer campaignType, Pageable pageable);

    List<Campaign> findAllByStatus(Status status);
}
