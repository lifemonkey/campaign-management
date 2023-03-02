package campaign.service.mapper;

import campaign.domain.Rule;
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

    private final FileMapper fileMapper;

    private final TransactionTypeMapper transactionTypeMapper;

    public RuleMapper(UserMapper userMapper, TargetListMapper targetListMapper, FileMapper fileMapper, TransactionTypeMapper transactionTypeMapper) {
        this.userMapper = userMapper;
        this.targetListMapper = targetListMapper;
        this.fileMapper = fileMapper;
        this.transactionTypeMapper = transactionTypeMapper;
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
            rule.setCampaignType(ruleDTO.getCampaignType());
            if (ruleDTO.getDurationType() != null) {
                rule.setDurationType(ruleDTO.getDurationType());
                // add some condition here to set condition values
                if (ruleDTO.getDurationValue() != null) {
                    rule.setDurationValue(ruleDTO.getDurationValue());
                }
            }

            if (ruleDTO.getRuleConfiguration() != null) {
                rule.setRuleConfiguration(ruleDTO.getRuleConfiguration());
            }

            rule.setTemplate((ruleDTO.isTemplate() != null) ? ruleDTO.isTemplate() : false);

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

            if (ruleVM.getCampaignType() != null) {
                rule.setCampaignType(ruleVM.getCampaignType());
            }

            if (ruleVM.getDurationType() != null) {
                rule.setDurationType(ruleVM.getDurationType());
                // add some condition here to set condition values
                if(ruleVM.getDurationValue() != null) {
                    rule.setDurationValue(ruleVM.getDurationValue());
                }
            }

            if (ruleVM.getRuleConfiguration() != null) {
                rule.setRuleConfiguration(ruleVM.getRuleConfiguration());
            }
            rule.setTemplate((ruleVM.isTemplate() != null) ? ruleVM.isTemplate() : false);

            return rule;
        }
    }

    public Rule updateRule(Rule ruleInDb, RuleVM ruleVM) {
        if (ruleVM == null || ruleInDb == null) {
            return null;
        } else {
            Rule rule = ruleInDb;

            if (ruleVM.getName() != null) {
                rule.setName(ruleVM.getName());
            }
            if (ruleVM.getDescription() != null) {
                rule.setDescription(ruleVM.getDescription());
            }
            if (ruleVM.getCampaignType() != null) {
                rule.setCampaignType(ruleVM.getCampaignType());
            }
            if (ruleVM.getDurationType() != null) {
                rule.setDurationType(ruleVM.getDurationType());
                // add some condition here to set condition values
                if(ruleVM.getDurationValue() != null) {
                    rule.setDurationValue(ruleVM.getDurationValue());
                }
            }
            if (ruleVM.getRuleConfiguration() != null) {
                rule.setRuleConfiguration(ruleVM.getRuleConfiguration());
            }
            rule.setTemplate((ruleVM.isTemplate() != null) ? ruleVM.isTemplate() : false);

            return rule;
        }
    }
}
