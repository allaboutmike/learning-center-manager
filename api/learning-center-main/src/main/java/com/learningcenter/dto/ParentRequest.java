package com.learningcenter.dto;

import java.util.List;

public  record ParentRequest( Long parentId,
                              List<ChildResponse> children,
                              List<SessionResponse> childSessions,
                              Integer creditBalance) {

}
