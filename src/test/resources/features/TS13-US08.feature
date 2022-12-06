
@db
Feature: Updating user status as librarian should change current user status in DB

#  @wip
  Scenario: Updating user status as librarian should change current user status in DB
    Given user login as a librarian
    And the user navigates to "Users" page
    When the user clicks Edit User button
    And the user changes user status "ACTIVE" to "INACTIVE"
    And the user clicks save changes button
    Then "The user updated." message should appear
    And the users should see same status for related user in database
    And the user changes current user status "INACTIVE" to "ACTIVE"