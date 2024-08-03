package com.mindex.challenge.controller;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    
    @PostMapping("/compensation/{id}")
    public Compensation createComp(@RequestBody Compensation compensation, @PathVariable String id) {
    	LOG.debug("Received compensation create request for id [{}] and employee [{}]", compensation, id);

        return compensationService.createComp(compensation, id);
    }
    
    @GetMapping("/compensation/{id}")
    public Compensation readComp(@PathVariable String id) {
        LOG.debug("Received compensation search request for id [{}]", id);

        return compensationService.readComp(id);
    }
    
   
    
}
