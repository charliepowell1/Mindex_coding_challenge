package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation createComp(Compensation compensation, String id) {
        LOG.debug("Creating compensation [{}]", compensation);
        

        return compensationRepository.save(compensation);
    }
    
    @Override
    public Compensation readComp(String id) {
    Compensation comp = compensationRepository.findByEmployeeId(id);
    	
    	if (comp == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
    	
    	return comp;
    }
    

    
   
}