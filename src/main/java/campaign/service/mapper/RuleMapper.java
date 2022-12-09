package campaign.service.mapper;

import campaign.domain.Rule;
import campaign.domain.RuleConfiguration;
import campaign.domain.TransactionType;
import campaign.service.dto.RuleDTO;
import campaign.web.rest.vm.RuleVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RuleMapper {

    private final UserMapper userMapper;

    private final TargetListMapper targetListMapper;

    private final FilesMapper filesMapper;

    public RuleMapper(UserMapper userMapper, TargetListMapper targetListMapper, FilesMapper filesMapper) {
        this.userMapper = userMapper;
        this.targetListMapper = targetListMapper;
        this.filesMapper = filesMapper;
    }

    public RuleDTO ruleToRuleDTO(Rule rule) {
        return new RuleDTO(rule);
    }

    public List<RuleDTO> ruleToRuleDTOs(List<Rule> rulList) {
        return rulList.stream()
            .filter(Objects::nonNull)
            .map(this::ruleToRuleDTO)
            .collect(Collectors.toList());
    }

    public Rule ruleDTOToRule(RuleDTO ruleDTO) {
        if (ruleDTO == null) {
            return null;
        } else {
            Rule rule = new Rule();
            rule.setId(ruleDTO.getId());
            rule.setName(ruleDTO.getName());
            rule.setDescription(ruleDTO.getDescription());
            if (ruleDTO.getDurationType() != null) {
                rule.setDurationType(ruleDTO.getDurationType());
                // add some condition here to set condition values
                if (ruleDTO.getDurationValue() != null) {
                    rule.setDurationValue(ruleDTO.getDurationValue());
                }
            }
            if (ruleDTO.getRewardCondition() != null) {
                rule.setRewardCondition(ruleDTO.getRewardCondition());
            }

            if (ruleDTO.getRuleConfiguration() != null) {
                rule.setRuleConfiguration(new RuleConfiguration(ruleDTO.getRuleConfiguration()));
            }
            if (ruleDTO.getTransactionType() != null) {
                rule.setTransactionType(new TransactionType(ruleDTO.getTransactionType()));
            }

            rule.setCreatedBy(ruleDTO.getCreatedBy());
            rule.setCreatedDate(ruleDTO.getCreatedDate());
            rule.setLastModifiedBy(ruleDTO.getLastModifiedBy());
            rule.setLastModifiedDate(ruleDTO.getLastModifiedDate());

            return rule;
        }
    }

    public List<Rule> ruleDTOToRules(List<RuleDTO> ruleDTOS) {
        return ruleDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::ruleDTOToRule)
            .collect(Collectors.toList());
    }

    public Rule ruleVMToRule(RuleVM ruleVM) {
        if (ruleVM == null || ruleVM.getName() == null) {
            return null;
        } else {
            Rule rule = new Rule(ruleVM.getName());

            if (ruleVM.getDescription() != null) {
                rule.setDescription(ruleVM.getDescription());
            }

            if (ruleVM.getDurationType() != null) {
                rule.setDurationType(ruleVM.getDurationType());
                // add some condition here to set condition values
                if(ruleVM.getDurationValue() != null) {
                    rule.setDurationValue(ruleVM.getDurationValue());
                }
            }

            if (ruleVM.getRewardCondition() != null) {
                rule.setRewardCondition(ruleVM.getRewardCondition());
            }

//            if (ruleVM.getRuleConfiguration() != null) {
//                rule.setRuleConfiguration(ruleVM.getRuleConfiguration());
//            }
//            if (ruleVM.getTransactionType() != null) {
//                rule.setTransactionType(ruleVM.getTransactionType());
//            }

            return rule;
        }
    }
}
