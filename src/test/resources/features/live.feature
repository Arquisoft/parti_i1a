Feature: Live votations 
  Scenario: client makes call to GET /
    When the client calls /
    Then the client receives status code of 200
    And the client is shown the page "Votations"