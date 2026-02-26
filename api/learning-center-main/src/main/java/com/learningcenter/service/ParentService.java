package com.learningcenter.service;

import com.learningcenter.dto.ChildResponse;
import com.learningcenter.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


import java.util.List;

@Service
public class ParentService {
    private final ParentRepository parentRepository;


    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<ChildResponse> getChildrenByParent(Long parentId) {
        var children = parentRepository.listOfChildrenByParentId(parentId);
        var responseList = new ArrayList<ChildResponse>();

        for (var child : children) {
            responseList.add(new ChildResponse(child.getChildId(), child.getName(), child.getGradeLevel()));
        }
        return responseList;
    }
    public Integer getCreditsByParentId(Long parentId) {
        return parentRepository.findById(parentId).get().getCredits();
}

    public Integer addCreditsByParentId(Long parentId, Integer credits) {
    var creditBalance = parentRepository.findById(parentId).get().getCredits();
    creditBalance += credits;
    return creditBalance;

    }
}
