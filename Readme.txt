Hi and thanks for taking the time to look at this. I'd be very much interested in your feedback.

Some aspects of the application were not addressed in the specs, hence I took some assumptions, and made some choice, that hopefully
make sense and meet your favour. I thought to mention them explicitly as they may clarify my choices in the application:

- JSON in specs: the JSON examples in the specs miss the double quotes in the values. I have worked on the assumption of that being a typo,
  after all that JSON results not well-formed upon parsing.
- Although the specs suggest the use of Spring MVC, they do not make explicit requirements on the actual views. I therefore worked on the
  assumption that views are not required, and used @RestController instead of @Controller. Hopefully I did not misunderstood the requirement.
- Message validation: I placed it at endpoint level as this would make the Message object more reusable -- in case in needs to be used
  in contexts with a different set of constraints in the future
- User validation: the specs do not explicitly mention that sender and receiver of a message should be validated, however I included
  basic validation to keep the system consistent.
- Special characters: the specs do not explicitly mention whether special characters should be allowed or not, I took the easiest assumption
  and ignored them altogether. That means that the application will handle messages with no spaces, special characters and/or symbols.
  It is my understanding, also looking at the examples provided, that this should be sifficient for the test.
- Equality of messages: Normally I would add a timestamp in the message structure to be used to understand whether two messages with same
  sender, receiver and text are instances of the same message, however nothing was requested in the specs so I avoided it. I am thus assuming
  that two messages are equal when they come from the same sender, to the same receiver with the same text -- which means messages between two
  parties cannot be the same. Hopefully this assumption is sensible for the sake of the test.
- Message obfuscation: I was not 100% sure of the real meaning of 'The obsfuscation should be applied on as the message object is created on
  the server'. I took the assumption of that meaning obfuscation needs to take place at the service level, hence I instantiate the message
  object at the endpoint level and pass it down to the service for obfuscation -- which return a new message with the text obfuscated
- The specs do not address cases where interactions do not go as expected, however I do believe these need to be handled and took assumptions
  on what codes to return in those cases, e.g. when a message is invalid (not all in upper cases). I have looked at HTTP specs to understand
  the best code to return in those cases -- hopefully they do make sense. Would be happy to elaborate further if some choices are ambiguous.
  Also, I am trying to return the right code for each possible occurrence, hence the finer-frained validations. In real life they may be
  perhaps aggregated.
- Hyphenated URI in specs: I looked at URI patterns before doing this, and as a result of my research I decided to remove the hyphen from
  the URI. Not sure it was a typo.