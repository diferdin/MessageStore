Hi and thanks for taking the time to look at this. I'd be very much interested in your feedback.

Some aspects of the application were not addressed in the specs, hence I took some assumptions, and made some choice, that hopefully
make sense and meet your favour. I thought to mention them explicitly as they may clarify my choices in the application:

- Message validation: I placed it at endpoint level as this would make the Message object more reusable -- in case in needs to be used
  in contexts with a different set of constraints in the future
- User validation: the specs do not explicitly mention that sender and receiver of a message should be validated, however I included
  basic validation to keep the system consistent.
- Special characters in alphabet: the specs do not explicitly mention whether special characters should be allowed or not, I took the
  easiest assumption and ignored them altogether. They would be handled in some way in real world.