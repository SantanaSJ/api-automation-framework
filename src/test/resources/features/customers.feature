@Customers
Feature: Customers

  Customers section using User role

  Background:
    Given user is logged in to "ui.base.url" with username "test.user.name" and password "user.pass" and the token is stored
    And token should not be null

  @valid
  @createCustomer
  Scenario:
    When a request is sent to "api.endpoint.customers" with the following data:
      | webUsernames            | companyName            |
      | testAutomationCustomer  | testAutomationCompany  |
      | testAutomationCustomer2 | testAutomationCompany2 |
    Then the response status code should be 200
    And the response status message should be successful
    And the response should contain valid customer payload

    @getAllCustomers
    Scenario: Get all customers
      When a request is sent to "api.endpoint.customers"
      Then the response status code should be 200