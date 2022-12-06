@wip@db
Feature: Books module
  As a student, I should be able to borrow a book
  Scenario: Students borrow new books
    Given I login as a student from Excel
    And I navigate to "Books" page
    And I search book name called "<Head First Java>" from Excel
    When I click Borrow Book
    Then  verify that book is shown in "Borrowing Books" page Excel
    And  verify logged student has same book in database Excel