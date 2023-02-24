package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "transaction_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionType extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_type_sequence_generator")
    @SequenceGenerator(name = "transaction_type_sequence_generator", sequenceName = "transaction_type_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "external_id", length = 25)
    private String externalId;

    @Column(name = "status", length = 25)
    private Integer status;

    @Column(name = "trans_type_english", length = 200)
    private String transTypeEN;

    @Column(name = "trans_type_swahili", length = 200)
    private String transTypeSW;

    @ManyToMany(mappedBy = "transactionTypes", fetch = FetchType.LAZY)
    List<Rule> ruleList = new ArrayList<>();

    public TransactionType() {
    }

    public TransactionType(String name) {
        this.name = name;
    }

    public TransactionType(String name, String description, String externalId, Integer status, String transTypeEN, String transTypeSW) {
        this.name = name;
        this.description = description;
        this.externalId = externalId;
        this.status = status;
        this.transTypeEN = transTypeEN;
        this.transTypeSW = transTypeSW;
    }

    public TransactionType name(String name) {
        this.name = name;
        return this;
    }

    public TransactionType description(String description) {
        this.description = description;
        return this;
    }

    public TransactionType externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public TransactionType status(Integer status) {
        this.status = status;
        return this;
    }

    public TransactionType transTypeEN(String transTypeEN) {
        this.transTypeEN = transTypeEN;
        return this;
    }

    public TransactionType transTypeSW(String transTypeSW) {
        this.transTypeSW = transTypeSW;
        return this;
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransTypeEN() {
        return transTypeEN;
    }

    public void setTransTypeEN(String transTypeEN) {
        this.transTypeEN = transTypeEN;
    }

    public String getTransTypeSW() {
        return transTypeSW;
    }

    public void setTransTypeSW(String transTypeSW) {
        this.transTypeSW = transTypeSW;
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void addRuleList(List<Rule> ruleList) {
        this.ruleList.addAll(ruleList);
    }

    public TransactionType clearRuleList() {
        this.ruleList.clear();
        return this;
    }

    public TransactionType clone(String name) {
        return new TransactionType(name, this.description, this.externalId, this.status, this.transTypeEN, this.transTypeSW);
    }

    @Override
    public String toString() {
        return "TransactionType{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", externalId='" + externalId + '\'' +
            ", status=" + status +
            ", transTypeEN='" + transTypeEN + '\'' +
            ", transTypeSW='" + transTypeSW + '\'' +
            '}';
    }
}
