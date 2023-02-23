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

    @ManyToMany(mappedBy = "transactionTypes", fetch = FetchType.LAZY)
    List<Rule> ruleList = new ArrayList<>();

    public TransactionType() {
    }

    public TransactionType(String name) {
        this.name = name;
    }

    public TransactionType(String name, String description) {
        this.name = name;
        this.description = description;
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
        return new TransactionType(name, this.description);
    }

    @Override
    public String toString() {
        return "TransactionType{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
