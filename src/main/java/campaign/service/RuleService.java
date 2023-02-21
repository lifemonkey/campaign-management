package campaign.service;

import campaign.config.Constants;
import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.RuleDTO;
import campaign.service.mapper.RewardConditionMapper;
import campaign.service.mapper.RuleMapper;
import campaign.web.rest.vm.RewardConditionVM;
import campaign.web.rest.vm.RuleVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public Boolean ruleNameExisted(String name) {
        return ruleRepository.findByNameIgnoreCase(name).isPresent();
    }

    @Transactional(readOnly = true)
    public Page<RuleDTO> searchRules(Pageable pageable, String search, String appliedCampaign, Integer campaignType) {
        Page<Rule> ruleList;

        if (search != null && campaignType == null) {
            ruleList = ruleRepository.findAllByNameContainingIgnoreCase(search, pageable);
        } else if (search == null && campaignType != null) {
            ruleList = ruleRepository.findAllByCampaignType(campaignType, pageable);
        } else if (search != null && campaignType != null) {
            ruleList = ruleRepository.findAllByNameContainingIgnoreCaseAndCampaignType(search, campaignType, pageable);
        } else {
            ruleList = ruleRepository.findAll(pageable);
        }

        if (appliedCampaign == null || appliedCampaign.equalsIgnoreCase("all")) {
            return ruleList.map(RuleDTO::new);
        }

        // applied campaign: All, None, Campaign-name
        return new PageImpl<>(
            ruleList.stream()
                .filter(rule -> {
                    if (appliedCampaign.equalsIgnoreCase("none")) {
                        if (rule.getCampaignList() == null || rule.getCampaignList().isEmpty()) return true;
                    } else {
                        if (rule.getCampaignList().stream()
                            .filter(campaign -> campaign.getName().toLowerCase().contains(appliedCampaign.toLowerCase()))
                            .findAny().isPresent()) return true;
                    }
                    return false;
                })
                .collect(Collectors.toList()), ruleList.getPageable(), ruleList.getTotalElements()
        ).map(RuleDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleDTO createRule(RuleVM ruleVM) {
        Rule rule = ruleMapper.ruleVMToRule(ruleVM);

        if (rule != null) {
            ruleRepository.save(rule);

            // handle transaction types
            if (ruleVM.getTransactionTypes().size() > 0) {
                List<TransactionType> transactionTypes = transactionTypeRepository.findAllById(ruleVM.getTransactionTypes());
                if (!transactionTypes.isEmpty() && transactionTypes.size() == ruleVM.getTransactionTypes().size()) {
                    rule.addTransactionTypes(transactionTypes);
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
        if (!cloneRuleOpt.isPresent()) return null;

        Rule toBeInserted = new Rule();

        if (cloneRuleOpt.isPresent()) {
            String clonedName = cloneRuleOpt.get().getName() + Constants.CLONE_POSTFIX;
            List<Rule> rulesByName = ruleRepository.findByNameStartsWithIgnoreCase(clonedName);
            toBeInserted.setName(ServiceUtils
                .clonedCount(clonedName, rulesByName.stream().map(Rule::getName).collect(Collectors.toList())));
            toBeInserted.setDescription(cloneRuleOpt.get().getDescription());
            toBeInserted.setDurationType(cloneRuleOpt.get().getDurationType());
            toBeInserted.setDurationValue(cloneRuleOpt.get().getDurationValue());
            toBeInserted.setCampaignType(cloneRuleOpt.get().getCampaignType());
            // clone reward conditions
            if (cloneRuleOpt.get().getRewardConditions() != null) {
                toBeInserted.addRewardConditions(cloneRuleOpt.get().getRewardConditions());
            }
            toBeInserted.setRuleConfiguration(cloneRuleOpt.get().getRuleConfiguration());
            toBeInserted.addTransactionTypes(cloneRuleOpt.get().getTransactionTypes());
        }

        return ruleMapper.ruleToRuleDTO(ruleRepository.save(toBeInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleDTO updateRule(Long id, RuleVM ruleVM) {
        Optional<Rule> ruleOpt = ruleRepository.findById(id);
        if (!ruleOpt.isPresent()) return null;

        Rule rule = ruleMapper.updateRule(ruleOpt.get(), ruleVM);

        // handle transaction types
        if (ruleVM.getTransactionTypes().size() > 0) {
            Set<Long> transactionTypeIds = ruleVM.getTransactionTypes().stream().collect(Collectors.toSet());
            // detach transaction types
            List<TransactionType> toBeDetached = ruleOpt.get().getTransactionTypes();
            transactionTypeRepository.saveAll(
                toBeDetached.stream()
                    .filter(tt -> !transactionTypeIds.contains(tt.getId()))
                    .map(TransactionType::clearRuleList)
                    .collect(Collectors.toList()));
            // add transaction type to rule
            rule.updateTransactionTypes(transactionTypeRepository.findAllById(transactionTypeIds));
        }

        // handle reward-conditions
        if (ruleVM.getRewardConditions() != null) {
            Set<Long> rewardConditionIds =
                ruleVM.getRewardConditions().stream().map(RewardConditionVM::getId).collect(Collectors.toSet());
            // detach rule from reward-conditions
            List<RewardCondition> toBeDetached = rewardConditionRepository.findAllByRuleId(rule.getId());
            //rule.getRewardConditions().stream().map(RewardCondition::removeRule).collect(Collectors.toList());
            rewardConditionRepository.saveAll(
                toBeDetached.stream()
                    .filter(rc -> !rewardConditionIds.contains(rc.getId()))
                    .map(RewardCondition::removeRule)
                    .collect(Collectors.toList()));

            List<Reward> rewardList = rewardRepository.findAllById(
                ruleVM.getRewardConditions().stream().map(RewardConditionVM::getRewardId).collect(Collectors.toList()));

            // handle reward-conditions
            rule.updateRewardConditions(
                rewardConditionMapper.rewardConditionVMToRewardConditions(ruleVM.getRewardConditions(), rule, rewardList));
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
            rule.clearRewardConditions();
            rule.clearTransactionTypes();
            ruleRepository.save(rule);
            ruleRepository.delete(rule);
        }
    }
}
