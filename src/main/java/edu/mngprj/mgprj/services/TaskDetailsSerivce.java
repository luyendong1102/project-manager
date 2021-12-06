package edu.mngprj.mgprj.services;

import edu.mngprj.mgprj.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDetailsSerivce {
    
    @Autowired
    private UserRepository userRepository;

}
