Hi and thanks for taking the time to look at this.

In the course of development of the test I took some assumptions and made some choices that hopefully make sense and
will meet your favour. I thought to mention them explicitly as they may clarify my choices in the application:

- JSON in specs: the JSON examples in the specs miss the double quotes around values. I have worked on the assumption of that being a typo,
  and added them in the JSON I use in the test.

- Although the specs suggest the use of Spring MVC, they do not make explicit requirements on the actual views. I therefore worked on the
  assumption that views are not required, and used @RestController instead of @Controller. Hopefully I did not misunderstood the requirement.

- Message validation: I placed it at endpoint level as this would make the Message object more reusable -- in case in needs to be used
  in contexts with a different set of constraints in the future.

- User validation: the specs do not explicitly mention that sender and receiver of a message should be validated, however I included
  basic validation to keep the system consistent.

- Special characters: the specs do not explicitly mention whether special characters should be allowed or not, I took the easiest assumption
  and ignored them altogether. That means that the application will handle messages as one single word in capital letters, with no spaces,
  special characters and/or symbols. It is my understanding, also looking at the examples provided, that this should be sufficient
  for the test.

- Equality of messages: Normally I would add a timestamp in the message structure to be used to understand whether two messages with same
  sender, receiver and text are instances of the same message, however nothing was requested in the specs so I avoided it. I am thus assuming
  that two messages are equal when they come from the same sender, to the same receiver with the same text -- which means two parties cannot
  exchange the the same message in a conversation. Hopefully this assumption is sensible for the sake of the test.

- Message obfuscation: I was not 100% sure of the real meaning of 'The obfuscation should be applied on as the message object is created on
  the server'. I took the assumption of that meaning obfuscation needs to take place at the service level, hence I instantiate the message
  object at the endpoint level and pass it down to the service for obfuscation -- which return a new message with the text obfuscated.

- The specs do not explicitly request HTTP codes for failing, e.g. when a message is invalid (not all in upper cases). In the test I am
  returning a few codes to handle those occasiona. Whereas not explicitly requested, I based my choices for HTTP return codes specs
  on the description found in the HTTP specs -- hopefully they do make sense. Would be happy to elaborate further if some choices are
  ambiguous.
  Also, I am trying to return the right code for each possible occurrence, hence the finer-grained validations. In real life they may be
  perhaps aggregated.

- Hyphenated URI in specs: I looked at URI patterns before doing this, and as a result of my research I decided to remove the hyphen from
  the URI. Not sure it was a typo.

  I'd be very much interested in your feedback.

  Regards,

  Antonio.