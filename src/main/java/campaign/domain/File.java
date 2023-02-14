package campaign.domain;

import campaign.config.Constants;
import campaign.service.dto.FileDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "files")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class File extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_sequence_generator")
    @SequenceGenerator(name = "file_sequence_generator", sequenceName = "field_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", length = 200, unique = true)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "file_type", length = 1)
    private Integer fileType;

    @Column(name = "image_url")
    private String imageUrl = null;

    @JsonIgnore
    @Column(name = "image_blob", columnDefinition = "BLOB")
    @Lob
    private byte[]  imageBlob;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id", referencedColumnName = "id")
    private Reward reward;

    public File() {}

    public File(FileDTO file) {
        this.name = file.getName();
        this.description = file.getDescription();
        this.fileType = file.getFileType();
        this.imageUrl = file.getImageUrl();
    }

    public File(String name, String description, Integer fileType, String imageUrl) {
        this.name = name;
        this.description = description;
        this.fileType = fileType;
        this.imageUrl = imageUrl;
    }

    public File clone() {
        String fileName = this.getName();
        if (this.getName() != null && !this.getName().isEmpty()) {
            String[] fileNameArr = this.getName().split("\\.");
            if (fileNameArr.length == 2) {
                fileName = fileNameArr[0] + Constants.CLONE_POSTFIX + "." + fileNameArr[1];
            }
        }

        return new File(fileName, this.getDescription(), this.getFileType(), this.getImageUrl());
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

    public File name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File description(String description) {
        this.description = description;
        return this;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public File type(Integer fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public File url(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public File removeCampaign() {
        this.campaign = null;
        return this;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public File removeReward() {
        this.reward = null;
        return this;
    }

    @Override
    public String toString() {
        return "Files{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", fileType=" + fileType +
            ", imageUrl='" + imageUrl + '\'' +
            ", imageBlob=" + Arrays.toString(imageBlob) +
            ", campaign=" + campaign +
            '}';
    }
}
