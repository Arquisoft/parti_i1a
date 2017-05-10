Feature: Being able to comment a proposal
	As a logged user
	I want to write a comment
	Because i have an something to say about a proposal

Scenario: Comment a proposal
    Given Im a logged user "dani@gmail.com" with password "password"
     When i open the list of proposals
     When i open the proposal "Title1"
      And i write "Comment body example" as comment body
      And i submit the comment
     Then a comment wîth the body "Comment body example" will be published