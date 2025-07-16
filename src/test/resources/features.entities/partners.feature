@Partners
Feature: Partners

  Partner Section using Tenant Admin role

  Background:
    Given client has retrieved a new token as admin from "api.base.url" as user "test.admin.user" and password "admin.pass"
    And token should not be null

  @smoke
  @valid
  @createPartner
  Scenario: Create a valid partner with an existing schedule
    And "ScheduleAutomation" is retrieved
    When a request is sent to "api.endpoint.partners" with the following data:
      | name                  | contactName     | emails      | headerKey1 | headerValue1 | isSecret1 | headerKey2 | headerValue2 | isSecret2 | isAlertEnabled |
      | testAutomationPartner | testRestPartner | test@abv.bg | testKey    | testValue    | true      | testKey1   | testValue1   | false     | false          |
    Then the response status code should be 200
    And the response status message should be successful
    And the response should contain valid partner payload