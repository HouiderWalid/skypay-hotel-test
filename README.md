## Test The Application

```bash
mvn test
```

## Run The Application

```bash
mvn exec:java
```

Or as a jar

```bash
mvn package
java -jar target/skypaybanktest-1.0-SNAPSHOT.jar
```

## Design Explanation

1/- Suppose we put all the functions inside the same service. Is this the
recommended approach ? Please explain.

- No, it's not the recommended approach, because it violates the single responsibility principle, which states that every class or function have to have a single job, this makes the code easy to maintain, the problem is if we want to update the rooms or users code we have to update the service class, which is not the job of the service class, it is the job of the userService's or roomService's job, so we can just create a class for room service and a class for user service so when we want to update for example the display of the user or any other thing that is related to users we only have to touch the userService class.

2/- In this design, we chose to have a function setRoom(..) that should
not impact the previous bookings. What is another way ? What is your
recommendation ? Please explain and justify.

- The other way is to find the room by the given id and update it, which will break previous bookings, because users had already been charged by the old room's price, and when you change the room price per night you will have unconsitencies, and when you display the the bookings you wouldn't be able to justify the balance from the price of the room, so my recommendation is to avoid mutating rooms and throw an error if the room with same id already exist, and force the user the choose a different room id.