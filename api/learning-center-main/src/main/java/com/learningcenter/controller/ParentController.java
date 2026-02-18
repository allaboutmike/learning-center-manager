package com.learningcenter.controller;

public class ParentController {

/*
ParentController API Documentation
REST API controller for managing list of children in the Learning Center application.

-----Endpoints-----
1. Register a new child
    POST /api/sessions
    Creates a new child from a parent.

    Request Body:
    {
      "childId": 5,
      "name": "Tom",
      "grade_level": "5",
      "parentId": "4"
    }

    Expected Response: Status: 201 Created
    Body: Child object with generated ID and attributes

2. Retrieve List of children
    GET /api/children/{parentId}
    Retrieves all children with corresponding parent id.

    Path Parameters: parentId (required, integer): The unique identifier of the session
    Example Request: GET /api/sessions/111

    Expected Response: Status: 200 OK
    Body: Child object with details of the name and grade level of the child
*/

}
