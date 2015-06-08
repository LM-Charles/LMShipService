# LMShipService

### Devo 

Branch: release-devo

Endpoint:  http://lmshipservice-devo.elasticbeanstalk.com

#### Resource

##### Debug Test SMS Gateway

Request: PUT http://lmshipservice-devo.elasticbeanstalk.com/rest/debug/testSMS/{phone_number}/{body}

Response, text/html: Testing SMS to : +11234567 with body: test_message_from_lm
(test API only works with pre-approved phone numbers, contact desmondz to register)

#### Debug Test DB Hibernate Entity Management

Request: GET http://lmshipservice-devo.elasticbeanstalk.com/rest/debug/testDB/

Response, text/html: Testing DB Hibernate entity mapping... followed by a list of entity and their underlying table.
