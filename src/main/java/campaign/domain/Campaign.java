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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_sequence_generator")
    @SequenceGenerator(name = "campaign_sequence_generator", sequenceName = "campaign_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "action_reason", length = 400)
    private String actionReason;

    @Column(name = "notes", length = 4000)
    private String notes;

    @Column(name = "is_template", nullable = false)
    private Boolean isTemplate;

    @Column(name = "unlimited_lucky_code", nullable = false)
    private Boolean unlimitedLuckyCode;

    @Column(name = "unlimited_prize", nullable = false)
    private Boolean unlimitedPrize;

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @OneToOne
    @JoinColumn(name = "approved_rejected_by", referencedColumnName = "id")
    private User approvedRejectedBy;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinTable(name = "campaign_target_list",
        joinColumns = @JoinColumn(name = "campaign_id", nullable = false, referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "target_list_id", nullable = false, referencedColumnName = "id"))
    private List<TargetList> targetLists = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "campaign")
    private List<File> fileList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "campaign_rule",
        joinColumns = @JoinColumn(name = "campaign_id", nullable = false, referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "rule_id", nullable = false, referencedColumnName = "id"))
    private List<Rule> ruleList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "campaign")
    private List<GeneratedTime> generatedTimeList = new ArrayList<>();

    public Campaign() {
        this.name = "Default Campaign";
    }

    public Campaign(String name, String description, LocalDateTime fromDate, LocalDateTime endDate,
                    Integer campaignType, String notes, Status status, String actionReason, User approvedRejectedBy,
                    Boolean isTemplate, Boolean unlimitedLuckyCode, Boolean unlimitedPrize
    ) {
        this.name = name;
        this.description = description;
        this.fromDate = fromDate;
        this.endDate = endDate;
        this.campaignType = campaignType;
        this.notes = notes;
        this.status = status;
        this.actionReason = actionReason;
        this.approvedRejectedBy = approvedRejectedBy;
        this.isTemplate = isTemplate;
        this.unlimitedLuckyCode = unlimitedLuckyCode;
        this.unlimitedPrize = unlimitedPrize;
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

    public String getActionReason() {
        return actionReason;
    }

    public void setActionReason(String actionReason) {
        this.actionReason = actionReason;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public void updateTargetLists(List<TargetList> targetLists) {
        this.targetLists.clear();
        this.targetLists.addAll(targetLists);
    }

    public void clearTargetLists() {
        this.targetLists = new ArrayList<>();
    }

    public List<File> getFilesList() {
        return fileList;
    }

    public void addFilesList(List<File> fileList) {
        this.fileList.addAll(fileList);
    }

    public void updateFileList(List<File> files) {
        this.fileList.clear();
        this.fileList.addAll(files);
    }

    public void clearFileList() {
        this.fileList.clear();
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void addRuleList(List<Rule> ruleList) {
        this.ruleList.addAll(ruleList);
    }

    public void clearRuleList() {
        this.ruleList = new ArrayList<>();
    }

    public void updateRuleList(List<Rule> ruleList) {
        this.ruleList.clear();
        this.ruleList.addAll(ruleList);
    }

    public List<GeneratedTime> getGeneratedTimeList() {
        return generatedTimeList;
    }

    public void addGeneratedTimeList(List<GeneratedTime> generatedTimes) {
        this.generatedTimeList.addAll(generatedTimes);
    }

    public void clearGeneratedTimeList(List<GeneratedTime> generatedTimes) {
        this.generatedTimeList.clear();
        this.generatedTimeList.addAll(generatedTimes);
    }

    public void updateGeneratedTimeList(List<GeneratedTime> generatedTimes) {
        this.generatedTimeList.clear();
        this.generatedTimeList.addAll(generatedTimes);
    }

    public Campaign clone(String name) {
        return new Campaign(name, this.description, LocalDateTime.now(), LocalDateTime.now().plusDays(30),
            this.campaignType, this.notes, this.status, this.actionReason, this.approvedRejectedBy, false,
            this.unlimitedLuckyCode, this.unlimitedPrize);
    }

    public Boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }

    public Boolean getUnlimitedLuckyCode() {
        return unlimitedLuckyCode;
    }

    public void setUnlimitedLuckyCode(Boolean unlimitedLuckyCode) {
        this.unlimitedLuckyCode = unlimitedLuckyCode;
    }

    public Boolean getUnlimitedPrize() {
        return unlimitedPrize;
    }

    public void setUnlimitedPrize(Boolean unlimitedPrize) {
        this.unlimitedPrize = unlimitedPrize;
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
            ", actionReason='" + actionReason + '\'' +
            ", notes='" + notes + '\'' +
            '}';
    }
}
