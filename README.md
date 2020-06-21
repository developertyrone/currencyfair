# Currencyfair
//Assumption
1. Assume seldom database product change on microservice, Simpler design for demo and amount of time, service layer has been skipped.
2. Since Service Layer skipped, a simpler approach on testing by API layer
3. Currency should use BigDecimal/Custom type to avoid calculation error, for demo purpose use double instead
4. Assume the time is sychronized even with different origin country
