@Partners
Feature: Partners

  Partner section using Tenant Admin role

  Background:
    Given admin is logged in to "ui.base.url" with "test.admin.name" and password "admin.pass" and the token is stored
    And token should not be null

  @smoke
  @createPartner
  Scenario: Create a valid partner with an existing schedule
    And "ScheduleAutomation" is retrieved
    When a request is sent to partner "api.endpoint.partners" with the following data:
      | name                  | contactName     | emails       | headerKey1 | headerValue1 | isSecret1 | headerKey2 | headerValue2 | isSecret2 | isAlertsEnabled |
      | testAutomationPartner | testRestPartner | test@test.bg | testKey    | testValue    | true      | testKey1   | testValue1   | false     | false           |
    Then the response status code should be 200
    And the response status message should be successful
    And the response should contain valid partner payload