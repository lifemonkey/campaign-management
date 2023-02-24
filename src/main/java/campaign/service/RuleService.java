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

import java.util.*;
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

    private final FileRepository fileRepository;

    public RuleService(RuleRepository ruleRepository,
                       TransactionTypeRepository transactionTypeRepository,
                       RuleMapper ruleMapper,
                       RewardConditionRepository rewardConditionRepository,
                       RewardConditionMapper rewardConditionMapper,
                       RewardRepository rewardRepository,
                       FileRepository fileRepository
    ) {
        this.ruleRepository = ruleRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.ruleMapper = ruleMapper;
        this.rewardConditionRepository = rewardConditionRepository;
        this.rewardConditionMapper = rewardConditionMapper;
        this.rewardRepository = rewardRepository;
        this.fileRepository = fileRepository;
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
        List<Rule> ruleList;

        if (search != null && campaignType == null) {
            ruleList = ruleRepository.findAllByNameContainingIgnoreCase(search);
        } else if (search == null && campaignType != null) {
            ruleList = ruleRepository.findAllByCampaignType(campaignType);
        } else if (search != null && campaignType != null) {
            ruleList = ruleRepository.findAllByNameContainingIgnoreCaseAndCampaignType(search, campaignType);
        } else {
            ruleList = ruleRepository.findAll();
        }

        // sort
        sortResults(pageable, ruleList);

        // applied campaign: All, None, Campaign-name
        List<RuleDTO> filteredList = ruleList.stream()
            .filter(rule -> {
                if (appliedCampaign == null || appliedCampaign.isEmpty()
                    | appliedCampaign.equalsIgnoreCase("all")
                ) {
                    return true;
                }

                // filter for appliedCampaign is none
                if (appliedCampaign.equalsIgnoreCase("none")) {
                    return rule.getCampaignList().isEmpty();
                } else {
                    // filter for specific appliedCampaign name
                    return rule.getCampaignList().stream()
                        .filter(campaign -> campaign.getName().toLowerCase().contains(appliedCampaign.toLowerCase()))
                        .findAny().isPresent();
                }
            })
            .map(RuleDTO::new)
            .collect(Collectors.toList());

        return new PageImpl<>(ServiceUtils.getPageContent(pageable, filteredList) , pageable, filteredList.size());
    }

    private void sortResults(Pageable pageable, List<Rule> toBeSortedList) {
        if (pageable.getSort().stream()
            .filter(sort -> sort.getProperty().toLowerCase() == Constants.SORT_BY_CREATED_DATE).findAny()
            .isPresent()
        ) {
            if (pageable.getSort().stream().filter(sort -> sort.isDescending()).findAny().isPresent()) {
                Collections.sort(toBeSortedList, Comparator.comparing(Rule::getCreatedDate).reversed());
            } else {
                Collections.sort(toBeSortedList, Comparator.comparing(Rule::getCreatedDate));
            }
        } else {
            if (pageable.getSort().stream().filter(sort -> sort.isDescending()).findAny().isPresent()) {
                Collections.sort(toBeSortedList, Comparator.comparing(Rule::getName).reversed());
            } else {
                Collections.sort(toBeSortedList, Comparator.comparing(Rule::getName));
            }
        }
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

        String clonedName = cloneRuleOpt.get().getName() + Constants.CLONE_POSTFIX;
        List<Rule> rulesByName = ruleRepository.findByNameStartsWithIgnoreCase(clonedName);
        Rule toBeInsertedRule = cloneRuleOpt.get().clone(
            ServiceUtils
                .clonedFileName(clonedName, rulesByName.stream().map(Rule::getName).collect(Collectors.toList())));
        // save rule to db
        ruleRepository.save(toBeInsertedRule);

        // do not add campaign
        // clone transaction type
        if (!cloneRuleOpt.get().getTransactionTypes().isEmpty()) {
            toBeInsertedRule.updateTransactionTypes(cloneRuleOpt.get().getTransactionTypes());
        }

        // clone reward conditions
        List<RewardCondition> clonedRewardConditions = cloneRuleOpt.get().getRewardConditions().stream()
            .map(rewardCondition -> {
                RewardCondition clonedRewardCondition = rewardCondition.clone();
                clonedRewardCondition.setRule(toBeInsertedRule);
                clonedRewardCondition.setReward(rewardCondition.getReward());
                return clonedRewardCondition;
            })
            .collect(Collectors.toList());

        if (!clonedRewardConditions.isEmpty()) {
            // two ways binding, add reward conditions to rule
            rewardConditionRepository.saveAll(clonedRewardConditions);
            toBeInsertedRule.updateRewardConditions(clonedRewardConditions);
            Map<Long, String> rewardConditionRewadMap = new HashMap<>();

            // clone reward and re-assign cloned reward to reward conditions
            Map<String, List<File>> rewardNameFileMap = new HashMap<>();
            List<Reward> toBeSavedRewards = clonedRewardConditions.stream()
                .map(rewardCondition -> {
                    String rewardClonedName = rewardCondition.getReward().getName() + Constants.CLONE_POSTFIX;
                    List<Reward> rewardsByName = rewardRepository.findByNameStartsWithIgnoreCase(rewardClonedName);
                    String finalRewardName = ServiceUtils
                        .clonedFileName(rewardClonedName, rewardsByName.stream().map(Reward::getName).collect(Collectors.toList()));
                    Reward toBeCloned = rewardCondition.getReward().clone(finalRewardName);
                    // do not apply campaign
                    // toBeCloned.setCampaignId(toBeInsertedCampaign.getId());
                    // TODO add reward to reward conditions;
                    rewardConditionRewadMap.put(rewardCondition.getId(), finalRewardName);

                    // add file to be cloned
                    if (rewardNameFileMap.containsKey(finalRewardName)) {
                        rewardNameFileMap.get(finalRewardName).addAll(rewardCondition.getReward().getFiles());
                    } else {
                        rewardNameFileMap.put(finalRewardName, new ArrayList<>(rewardCondition.getReward().getFiles()));
                    }
                    return toBeCloned;
                })
                .collect(Collectors.toList());

            // save reward to db
            rewardRepository.saveAll(toBeSavedRewards);
            rewardConditionRepository.saveAll(
                clonedRewardConditions.stream().map(rewardCondition -> {
                    if (rewardConditionRewadMap.containsKey(rewardCondition.getId())) {
                        rewardCondition.setReward(toBeSavedRewards.stream()
                            .filter(reward -> rewardConditionRewadMap.get(rewardCondition.getId()) == reward.getName())
                            .findFirst().orElse(null));
                    }
                    return rewardCondition;
                }).collect(Collectors.toList()));

            // cloned file of reward
            if (!toBeSavedRewards.isEmpty()) {
                List<File> toBeClonedRewardFiles = new ArrayList<>();
                toBeSavedRewards.stream().forEach(r -> {
                    if (rewardNameFileMap.containsKey(r.getName())) {
                        toBeClonedRewardFiles.addAll(
                            buildSavedFiles(rewardNameFileMap.get(r.getName())).stream()
                                .map(file -> file.addReward(r))
                                .collect(Collectors.toList()));
                    }
                });
                // save file to db
                fileRepository.saveAll(toBeClonedRewardFiles);
            }

            // save reward conditions after update cloned reward
            rewardConditionRepository.saveAll(clonedRewardConditions);
        }

        return ruleMapper.ruleToRuleDTO(ruleRepository.save(toBeInsertedRule));
    }

    private List<File> buildSavedFiles(List<File> fileList) {
        return fileList.stream()
            .map(file -> {
                String clonedFileName = ServiceUtils.getFileName(file.getName());
                List<File> filesByName = fileRepository.findByNameStartsWithIgnoreCase(clonedFileName);
                File clonedFile = file.clone(
                    ServiceUtils
                        .clonedFileName(clonedFileName, filesByName.stream().map(File::getName).collect(Collectors.toList()))
                        + ServiceUtils.getFileNameExt(file.getName()));
                return clonedFile;
            })
            .collect(Collectors.toList());
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
