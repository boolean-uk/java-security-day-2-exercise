# Java Security Day 2 Exercise

## Learning Objectives
- Build an API
- Use Authentication and JWT to prevent unauthorised access to it
- Give different roles of user different levels of access to the library

## Instructions

1. Fork this repository
2. Clone your fork to your machine
3. Open the project in IntelliJ

## Core Activity

You are going to build on the activity that you worked on yesterday. You need to implement a basic online lending library which contains books, CDs, DVDs, Video Games and other board games, implement an admin level user who can log in and perform all of the normal CRUD tasks on the items contained within the library. Also implement a normal level of user who can interact with the GET endpoints of the API but cannot perform any of the other functions. Create an OpenAPI spec to show this too.

## Extension Activity

Extend the core functionality so that normal users can borrow and return items from the library, they should be able to item items out and return them via the API. They should also be able to see a list of their current items and a history of previously borrowed items. Admin users should additionally be able to see the history of items borrowed for any normal user, and what items they currently have on loan. They should also be able to view the history of who has borrowed a given item. As well as building the end points for this also create an OpenAPI spec for this.


