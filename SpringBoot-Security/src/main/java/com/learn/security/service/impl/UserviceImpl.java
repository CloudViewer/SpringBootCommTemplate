package com.learn.security.service.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ale on 2020/9/14
 */
@Service
public class UserviceImpl {

    @PreAuthorize("hasRole('admin')")
    public List<String> findAll(){
        return null;
    }
}
