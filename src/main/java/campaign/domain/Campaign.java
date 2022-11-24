package campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campaign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Campaign extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @JsonIgnore
    @Column(name = "from_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime fromDate;

    @JsonIgnore
    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;

    @Column(name = "campaign_type", length = 1)
    private Integer campaignType;

    @Column(name = "notes", length = 4000)
    private String notes;

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status statusId;

    @OneToOne()
    @JoinColumn(name = "approved_rejected_by", referencedColumnName = "id")
    private User approvedRejectedBy;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "campaign_target_list",
        joinColumns = @JoinColumn(name = "target_list_id", nullable = false, updatable = false, referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "campaign_id", nullable = false, updatable = false, referencedColumnName = "id"))
    private List<TargetList> targetLists = new ArrayList<>();;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "campaign")
    private List<Files> filesList = new ArrayList<>();;

    public Campaign() {
    }

    public Campaign(String name, String description, LocalDateTime fromDate, LocalDateTime endDate, Integer campaignType, String notes, Status statusId, User approvedRejectedBy) {
        this.name = name;
        this.description = description;
        this.fromDate = fromDate;
        this.endDate = endDate;
        this.campaignType = campaignType;
        this.notes = notes;
        this.statusId = statusId;
        this.approvedRejectedBy = approvedRejectedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(Integer campaignType) {
        this.campaignType = campaignType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public User getApprovedRejectedBy() {
        return approvedRejectedBy;
    }

    public void setApprovedRejectedBy(User approvedRejectedBy) {
        this.approvedRejectedBy = approvedRejectedBy;
    }

    public List<TargetList> getTargetLists() {
        return targetLists;
    }

    public void addTargetLists(List<TargetList> targetLists) {
        this.targetLists.addAll(targetLists);
    }

    public List<Files> getFilesList() {
        return filesList;
    }

    public void addFilesList(List<Files> filesList) {
        this.filesList.addAll(filesList);
    }

    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", fromDate=" + fromDate +
            ", endDate=" + endDate +
            ", campaignType=" + campaignType +
            ", notes='" + notes + '\'' +
            ", statusId=" + statusId +
//            ", approvedRejectedBy=" + approvedRejectedBy.getUsername() +
//            ", targetLists=" + targetLists +
//            ", filesList=" + filesList +
            '}';
    }
}
