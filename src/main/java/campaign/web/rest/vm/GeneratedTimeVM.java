package campaign.web.rest.vm;

import java.time.LocalDateTime;

public class GeneratedTimeVM {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long campaignId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    @Override
    public String toString() {
        return "GeneratedTimeVM{" +
            "id=" + id +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", campaignId=" + campaignId +
            '}';
    }
}
