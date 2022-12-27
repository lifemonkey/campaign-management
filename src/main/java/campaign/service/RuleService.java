package campaign.service;

import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.RuleDTO;
import campaign.service.mapper.RewardConditionMapper;
import campaign.service.mapper.RewardMapper;
import campaign.service.mapper.RuleMapper;
import campaign.web.rest.vm.RuleVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RuleService {

    private final Logger log = LoggerFactory.getLogger(RuleService.class);

    private final RuleRepository ruleRepository;

    private final RuleConfigurationRepository ruleConfigurationRepository;

    private final TransactionTypeRepository transactionTypeRepository;

    private final RuleMapper ruleMapper;

    private final RewardConditionRepository rewardConditionRepository;

    private final RewardConditionMapper rewardConditionMapper;

    public RuleService(RuleRepository ruleRepository,
                       RuleConfigurationRepository ruleConfigurationRepository,
                       TransactionTypeRepository transactionTypeRepository,
                       RuleMapper ruleMapper,
                       RewardConditionRepository rewardConditionRepository,
                       RewardConditionMapper rewardConditionMapper
    ) {
        this.ruleRepository = ruleRepository;
        this.ruleConfigurationRepository = ruleConfigurationRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.ruleMapper = ruleMapper;
        this.rewardConditionRepository = rewardConditionRepository;
        this.rewardConditionMapper = rewardConditionMapper;
    }

    @Transactional(readOnly = true)
    public RuleDTO getRuleById(Long ruleId) {
        Optional<Rule> ruleOpt = ruleRepository.findById(ruleId);
        if (ruleOpt.isPresent()) {
            return ruleMapper.ruleToRuleDTO(ruleOpt.get());
        }

        return new RuleDTO();
    }

    @Transactional(readOnly = true)
    public Page<RuleDTO> getAllRules(Pageable pageable) {
        return ruleRepository.findAll(pageable).map(RuleDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleDTO createRule(RuleVM ruleVM) {
        Rule rule = ruleMapper.ruleVMToRule(ruleVM);

        if (rule != null) {
            ruleRepository.save(rule);

            // handle rule configuration
            if (ruleVM.getRuleConfiguration() != null) {
                Optional<RuleConfiguration> ruleConfigurationOpt = ruleConfigurationRepository.findById(ruleVM.getRuleConfiguration());
                if (ruleConfigurationOpt.isPresent()) {
                    rule.setRuleConfiguration(ruleConfigurationOpt.get());
                }
            }

            // handle transaction type
            if (ruleVM.getTransactionType() != null) {
                Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findById(ruleVM.getTransactionType());
                if (transactionTypeOpt.isPresent()) {
                    rule.setTransactionType(transactionTypeOpt.get());
                }
            }
            // save rule
            ruleRepository.save(rule);

            // handle reward-conditions
            if (ruleVM.getRewardConditions().size() > 0) {
                // save reward conditions
                List<RewardCondition> rewardConditionList = rewardConditionMapper.rewardConditionVMToRewardConditions(ruleVM.getRewardConditions());
                rewardConditionList.forEach(rewardCondition -> rewardCondition.setRule(rule));
                rewardConditionRepository.saveAll(rewardConditionList);
            }

            return ruleMapper.ruleToRuleDTO(rule);
        }

        return new RuleDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleDTO cloneRule(Long id) {
        Optional<Rule> cloneRuleOpt = ruleRepository.findById(id);
        Rule toBerInserted = new Rule();

        if (cloneRuleOpt.isPresent()) {
            toBerInserted.setName(cloneRuleOpt.get().getName());
            toBerInserted.setDescription(cloneRuleOpt.get().getDescription());
            toBerInserted.setDurationType(cloneRuleOpt.get().getDurationType());
            toBerInserted.setDurationValue(cloneRuleOpt.get().getDurationValue());
            // clone reward conditions
            if (cloneRuleOpt.get().getRewardConditions() != null) {
                toBerInserted.addRewardConditions(cloneRuleOpt.get().getRewardConditions());
            }
            toBerInserted.setRuleConfiguration(cloneRuleOpt.get().getRuleConfiguration());
            toBerInserted.setTransactionType(cloneRuleOpt.get().getTransactionType());
//            toBerInserted.setCampaign(cloneRuleOpt.get().getCampaign());
        }

        return ruleMapper.ruleToRuleDTO(ruleRepository.save(toBerInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleDTO updateRule(Long id, RuleVM ruleVM) {
        Rule rule = ruleMapper.ruleVMToRule(ruleVM);
        rule.setId(id);

        // handle rule configuration
        if (ruleVM.getRuleConfiguration() != null) {
            Optional<RuleConfiguration> ruleConfigurationOpt = ruleConfigurationRepository.findById(ruleVM.getRuleConfiguration());
            if (ruleConfigurationOpt.isPresent()) {
                rule.setRuleConfiguration(ruleConfigurationOpt.get());
            }
        }

        // handle transaction type
        if (ruleVM.getTransactionType() != null) {
            Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findById(ruleVM.getTransactionType());
            if (transactionTypeOpt.isPresent()) {
                rule.setTransactionType(transactionTypeOpt.get());
            }
        }

        ruleRepository.save(rule);
        return ruleMapper.ruleToRuleDTO(rule);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRule(Long id) {
        Optional<Rule> ruleOpt = ruleRepository.findById(id);
        if (ruleOpt.isPresent()) {
            Rule rule = ruleOpt.get();
            // detach campaign
            rule.setCampaign(null);
            ruleRepository.save(rule);
            ruleRepository.delete(rule);
        }
    }
}
