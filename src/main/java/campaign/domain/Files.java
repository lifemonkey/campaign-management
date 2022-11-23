package campaign.domain;

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
public class Files extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @NotBlank
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

    public Files() {
    }

    public Files(String name, String description, Integer fileType, String imageUrl, byte[] imageBlob) {
        this.name = name;
        this.description = description;
        this.fileType = fileType;
        this.imageUrl = imageUrl;
        this.imageBlob = imageBlob;
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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
