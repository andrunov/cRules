package ru.gpb.crules.web;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.gpb.crules.businessModel.CreditProgram;
import ru.gpb.crules.businessModel.CreditRequest;
import ru.gpb.crules.businessModel.CreditType;
import ru.gpb.crules.ruleEngine.RuleEngine;
import ru.gpb.crules.validation.ErrorValidation;

import java.net.URI;

@RestController
@RequestMapping(value = RulesController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RulesController {

    private static final Logger LOG = LogManager.getLogger();
    static final String REST_URL = "/rules";

    @Autowired
    protected RuleEngine ruleEngine;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createWithLocation(@Valid @RequestBody CreditRequest creditRequest) {
        LOG.info(creditRequest);

        CreditProgram creditProgram = new CreditProgram();
        String rulesReport = ruleEngine.performRules(creditRequest , creditProgram);

        ErrorValidation errorValidation = new ErrorValidation(creditRequest, creditProgram);
        String errorMessage = errorValidation.validate();

        Response response = new Response();
        response.creditRequest = creditRequest;
        response.creditProgram = creditProgram;
        response.errors = errorMessage;
        response.warnings = "";
        response.info = rulesReport;

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).buildAndExpand().toUri();
        return ResponseEntity.created(uriOfNewResource).body(response);
    }

    @GetMapping
    public ResponseEntity<Response> get() {
        Response response = new Response();
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setCreditType(CreditType.CONSUMER);
        creditRequest.setCreditQty(100000);
        creditRequest.setProgramCode("1.24.01");
        response.creditRequest = creditRequest;
        return ResponseEntity.ok(response);
    }

}
