# Price Comparator

This is the backend for a "Price Comparator" application. It was created as a challenge for the Software Engineer Internship from Accesa.

# Core Features

This solution implements all proposed ideas:

1. Splitting a basket into shopping lists by store
2. Finding the highest percentage discounts
3. Finding recently added discounts
4. Data points to plot graphs for price histories
5. Value per unit
6. Live price notifications using webSockets

# Tools & Architecture

The implementation follows a classic layered architecture with:

- controllers
- model
- persistence
- services
- validators

I used Spring Framework, with JPA and Postgres for data persistence.

For Live notification I chose to use WebSockets.

The application is Dockerized, so it can be easily run on any machine.
