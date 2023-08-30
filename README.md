# Simple project
During developing this simple CRUD webapp i just wanted to learn Spring-boot.

So i don't have good features :)

# What i have

- Integration tests
- Logging

- User can:
1. create (user, flight)
- 1.1. during creating of passenger, aircraft field is dynamic
2. book ticket
3. filter flights
4. after deleting flight all tickets with this flight are getting deleted
- 4.1. same thing with user
5. after deleting aircraft all flights which are associated with it are getting deleted
6. rest api all GETs (/aircraft, /flight, /flight/sorting-orders, /flight/{id}, /passenger/{id}, /passenger/tickets/{id}, )
 
  # Docker

I created docker-compose file so if u want to try to run this project u should:
- clone this repo to your pc:
  You can just download this project as .zip or You can use git:
  
  ```bash
  git clone https://github.com/Ord1naryMan/airportCRUD
  ```
- run it in docker:
  for this u should have docker locally installed.

  Execute this in terminal:
  ```bash
  docker compose up --build
  ```

- now u can go to localhost:8080 and look at it
