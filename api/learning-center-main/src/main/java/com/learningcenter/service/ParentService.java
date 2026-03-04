package com.learningcenter.service;

import com.learningcenter.dto.ChildResponse;
import com.learningcenter.dto.ParentResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;
import com.learningcenter.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


import java.util.List;
import java.util.Optional;

@Service
public class ParentService {
    private final ParentRepository parentRepository;
    private Parent parent;


    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public ParentResponse getParentByParentId(Long parentId) {
        var parent = parentRepository.findById(parentId);

        if (parent.isPresent()) {
            var p = parent.get();
            return buildParentResponse(p);
        }
        return null;
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

    public ParentResponse addCreditsByParentId(Long parentId, Integer credits) {
        parent = parentRepository.findById(parentId).get();
        var creditBalance = parent.getCredits();

        if (credits == null || credits <= 0) {
            return buildParentResponse(parent);
        };

        parent.setCredits(creditBalance + credits);
        parentRepository.save(parent);
        return buildParentResponse(parent);
    }

    private ParentResponse buildParentResponse(Parent p) {
        var childList = new ArrayList<ChildResponse>();

        for (var child : p.getChild()) {
            childList.add(new ChildResponse(child.getChildId(), child.getName(), child.getGradeLevel()));
        }

        ParentResponse parentResponse = new ParentResponse(p.getParentId(), childList, List.of(), p.getCredits());
        return parentResponse;
    }
}
