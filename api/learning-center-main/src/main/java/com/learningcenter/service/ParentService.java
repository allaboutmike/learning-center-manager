package com.learningcenter.service;

import com.learningcenter.dto.ChildResponse;
import com.learningcenter.dto.CreateChildRequest;
import com.learningcenter.dto.CreateParentRequest;
import com.learningcenter.dto.ParentResponse;
import com.learningcenter.entities.Child;
import com.learningcenter.entities.Parent;
import com.learningcenter.repository.ParentRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
        parent = parentRepository.save(parent);
        return buildParentResponse(parent);
    }

    public ParentResponse createParent(CreateParentRequest request) {
        if (parentRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        var parent = new Parent(request.name(), request.email(), request.phone());
        parent = parentRepository.save(parent);
        return buildParentResponse(parent);
    }

    private ParentResponse buildParentResponse(Parent p) {
        var childList = new ArrayList<ChildResponse>();

        for (var child : p.getChild()) {
            childList.add(new ChildResponse(child.getChildId(), child.getName(), child.getGradeLevel()));
        }

        return new ParentResponse(p.getParentId(), childList, List.of(), p.getCredits(), p.getEmail(), p.getPhone());
    }

    public ChildResponse createChild(Long parentId, CreateChildRequest request) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent not found"));

        Child child = new Child();
        child.setName(request.name());
        child.setGradeLevel(request.gradeLevel());
        child.setParent(parent);

        parent.getChild().add(child);
        parentRepository.save(parent);

        return new ChildResponse(
                child.getChildId(),
                child.getName(),
                child.getGradeLevel()
        );
    }

    public List<ParentResponse> getRecentParents() {

        List<Parent> parents = parentRepository.findTop3ByOrderByParentIdDesc();

        List<ParentResponse> responses = new ArrayList<>();

        for (Parent parent : parents) {
            responses.add(getParentByParentId(parent.getParentId()));
        }

        return responses;
    }
}
