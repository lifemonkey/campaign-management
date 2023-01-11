package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.TransactionTypeService;
import campaign.service.dto.TransactionTypeDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.ResponseCode;
import campaign.web.rest.vm.ResponseVM;
import campaign.web.rest.vm.TransactionTypeVM;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionTypeResource {

    private final Logger log = LoggerFactory.getLogger(TransactionTypeResource.class);

    private final TransactionTypeService transactionTypeService;

    public TransactionTypeResource(TransactionTypeService transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    /**
     * GET /transaction-types : get all transaction-types
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/transaction-types")
    @Timed
    public ResponseEntity<List<TransactionTypeDTO>> getAllTransactionTypes(
        Pageable pageable,
        @RequestParam(required = false) String search
    ) {
        Page<TransactionTypeDTO> page;
        if (search != null) {
            page = transactionTypeService.searchTransactionTypes(pageable, search);
        } else {
            page = transactionTypeService.getAllTransactionTypes(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transaction-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /transaction-type : get transaction-type by id
     *
     * @PathVariable id transaction-type id
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/transaction-type/{id}")
    @Timed
    public ResponseEntity<Object> getTransactionTypeById(@Valid @PathVariable Long id) {
        TransactionTypeDTO transactionType = transactionTypeService.getTransactionById(id);
        if (transactionType != null) {
            return new ResponseEntity<>(transactionType, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_TRANSACTION_TYPE_NOT_FOUND,
                "Transaction Type ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

    /**
     * POST /transaction-type : Create transaction-type
     *
     * @RequestBody transaction-type information to be created
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/transaction-type")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<TransactionTypeDTO> createTransactionType(@RequestBody TransactionTypeVM transactionTypeVM) {
        return new ResponseEntity<> (transactionTypeService.createTransactionType(transactionTypeVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * POST /transaction-type : Duplicate existing rule
     *
     * @RequestParam to be clone transaction-type
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/transaction-type/clone")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<TransactionTypeDTO> cloneTransactionType(@RequestParam Long id) {
        return new ResponseEntity<> (transactionTypeService.cloneTransactionType(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * PUT /transaction-type : Update transaction-type
     *
     * @RequestBody transaction-type information to be updated
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/transaction-type/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<Object> updateTransactionType(@Valid @PathVariable Long id, @RequestBody TransactionTypeVM transactionTypeVM) {
        TransactionTypeDTO transactionType = transactionTypeService.getTransactionById(id);
        if (transactionType.getId() != null) {
            return new ResponseEntity<>(transactionTypeService.updateTransactionType(id, transactionTypeVM), new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_TRANSACTION_TYPE_NOT_FOUND,
                "Transaction type ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE /transaction-type : Delete transaction-type
     *
     * @PathVariable id of transaction-type
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @DeleteMapping("/transaction-type/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<String> deleteRule(@Valid @PathVariable Long id) {
        transactionTypeService.deleteTransactionType(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
