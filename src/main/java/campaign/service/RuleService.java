package campaign.service;

import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.CampaignDTO;
import campaign.service.dto.RuleDTO;
import campaign.service.mapper.RewardConditionMapper;
import campaign.service.mapper.RuleMapper;
import campaign.web.rest.vm.RewardConditionVM;
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

    private final TransactionTypeRepository transactionTypeRepository;

    private final RuleMapper ruleMapper;

    private final RewardConditionRepository rewardConditionRepository;

    private final RewardConditionMapper rewardConditionMapper;

    private final RewardRepository rewardRepository;

    public RuleService(RuleRepository ruleRepository,
                       TransactionTypeRepository transactionTypeRepository,
                       RuleMapper ruleMapper,
                       RewardConditionRepository rewardConditionRepository,
                       RewardConditionMapper rewardConditionMapper,
                       RewardRepository rewardRepository
    ) {
        this.ruleRepository = ruleRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.ruleMapper = ruleMapper;
        this.rewardConditionRepository = rewardConditionRepository;
        this.rewardConditionMapper = rewardConditionMapper;
        this.rewardRepository = rewardRepository;
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
    public Page<RuleDTO> searchRules(Pageable pageable, String search, Integer campaignType) {
        if (search != null && campaignType == null) {
            return ruleRepository.findAllByNameContaining(search, pageable).map(RuleDTO::new);
        } else if (search == null && campaignType != null) {
            return ruleRepository.findAllByCampaignType(campaignType, pageable).map(RuleDTO::new);
        } else {
            return ruleRepository.findAllByNameContainingAndCampaignType(search, campaignType, pageable).map(RuleDTO::new);
        }
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

            // handle transaction type
            if (ruleVM.getTransactionType() != null) {
                Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findById(ruleVM.getTransactionType());
                if (transactionTypeOpt.isPresent()) {
                    rule.setTransactionType(transactionTypeOpt.get());
                }
            }

            // handle reward-conditions
            if (ruleVM.getRewardConditions().size() > 0) {
                List<RewardConditionVM> rewardConditionVMs = ruleVM.getRewardConditions();
                List<Reward> rewardList = rewardRepository.findAllById(
                    rewardConditionVMs.stream().map(RewardConditionVM::getRewardId).collect(Collectors.toList()));
                List<RewardCondition> rewardConditionList =
                    rewardConditionMapper.rewardConditionVMToRewardConditions(rewardConditionVMs, rule, rewardList);
                // save reward conditions
                rewardConditionRepository.saveAll(rewardConditionList);
                rule.addRewardConditions(rewardConditionList);
            }

            return ruleMapper.ruleToRuleDTO(ruleRepository.save(rule));
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
            toBerInserted.setCampaignType(cloneRuleOpt.get().getCampaignType());
            // clone reward conditions
            if (cloneRuleOpt.get().getRewardConditions() != null) {
                toBerInserted.addRewardConditions(cloneRuleOpt.get().getRewardConditions());
            }
            toBerInserted.setRuleConfiguration(cloneRuleOpt.get().getRuleConfiguration());
            toBerInserted.setTransactionType(cloneRuleOpt.get().getTransactionType());
        }

        return ruleMapper.ruleToRuleDTO(ruleRepository.save(toBerInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleDTO updateRule(Long id, RuleVM ruleVM) {
        Rule rule = ruleMapper.ruleVMToRule(ruleVM);
        rule.setId(id);

        // handle transaction type
        if (ruleVM.getTransactionType() != null) {
            Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findById(ruleVM.getTransactionType());
            if (transactionTypeOpt.isPresent()) {
                rule.setTransactionType(transactionTypeOpt.get());
            }
        }

        // handle reward-conditions
        if (ruleVM.getRewardConditions() != null) {
            List<RewardConditionVM> rewardConditionVMs = ruleVM.getRewardConditions();
            List<Reward> rewardList = rewardRepository.findAllById(
                rewardConditionVMs.stream().map(RewardConditionVM::getRewardId).collect(Collectors.toList()));
            List<RewardCondition> rewardConditionList =
                rewardConditionMapper.rewardConditionVMToRewardConditions(rewardConditionVMs, rule, rewardList);
            // detach rule from reward-conditions
            rewardConditionRepository.saveAll(rewardConditionList);
            // update reward conditions list in rule
            rule.clearRewardConditions();
            rule.addRewardConditions(rewardConditionList);
        }

        return ruleMapper.ruleToRuleDTO(ruleRepository.save(rule));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRule(Long id) {
        Optional<Rule> ruleOpt = ruleRepository.findById(id);
        if (ruleOpt.isPresent()) {
            Rule rule = ruleOpt.get();
            // detach campaign
            rule.clearCampaignList();
            ruleRepository.save(rule);
            ruleRepository.delete(rule);
        }
    }
}
