package campaign.service;

import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.RuleDTO;
import campaign.service.mapper.RuleMapper;
import campaign.web.rest.vm.RuleVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RuleService {

    private final Logger log = LoggerFactory.getLogger(RuleService.class);

    private final RuleRepository ruleRepository;

    private final RuleConfigurationRepository ruleConfigurationRepository;

    private final TransactionTypeRepository transactionTypeRepository;

    private final RuleMapper ruleMapper;

    public RuleService(RuleRepository ruleRepository,
                       RuleConfigurationRepository ruleConfigurationRepository,
                       TransactionTypeRepository transactionTypeRepository,
                       RuleMapper ruleMapper
    ) {
        this.ruleRepository = ruleRepository;
        this.ruleConfigurationRepository = ruleConfigurationRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.ruleMapper = ruleMapper;
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
                Optional<RuleConfiguration> ruleConfigurationOpt = ruleConfigurationRepository.findByName(ruleVM.getRuleConfiguration());
                if (ruleConfigurationOpt.isPresent()) {
                    rule.setRuleConfiguration(ruleConfigurationOpt.get());
                }
            }

            // handle transaction type
            if (ruleVM.getTransactionType() != null) {
                Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findByName(ruleVM.getTransactionType());
                if (transactionTypeOpt.isPresent()) {
                    rule.setTransactionType(transactionTypeOpt.get());
                }
            }

            ruleRepository.save(rule);
            return ruleMapper.ruleToRuleDTO(rule);
        }

        return new RuleDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public RuleDTO updateRule(Long id, RuleVM ruleVM) {
        Rule rule = ruleMapper.ruleVMToRule(ruleVM);
        rule.setId(id);

        // handle rule configuration
        if (ruleVM.getRuleConfiguration() != null) {
            Optional<RuleConfiguration> ruleConfigurationOpt = ruleConfigurationRepository.findByName(ruleVM.getRuleConfiguration());
            if (ruleConfigurationOpt.isPresent()) {
                rule.setRuleConfiguration(ruleConfigurationOpt.get());
            }
        }

        // handle transaction type
        if (ruleVM.getTransactionType() != null) {
            Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findByName(ruleVM.getTransactionType());
            if (transactionTypeOpt.isPresent()) {
                rule.setTransactionType(transactionTypeOpt.get());
            }
        }

        ruleRepository.save(rule);
        return ruleMapper.ruleToRuleDTO(rule);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }
}
