# Price Comparator

This is the backend for a "Price Comparator" application. It was created as a challenge for the Software Engineer Internship from Accesa.

## Table of contents

1. [Core Features](#core-features)
2. [Tools](#tools)
3. [Architecture](#architecture)
4. [Important Assumptions](#important-assumptions)
5. [Features explained and demonstrated](#features-explained-and-demonstrated)
6. [How to run](#how-to-run)
7. [Improvements and Extensibility](#improvements-and-extensibility)
8. [All API Endpoints](#all-endpoints)

# Core Features

This solution implements all proposed ideas, as listed below. The features are explain in
detail [below](#features-explained-and-demonstrated)

1. Splitting a basket into shopping lists by store
2. Finding the highest percentage discounts
3. Finding recently added discounts
4. Data points to plot graphs for price histories
5. Value per unit
6. Live price notifications using webSockets

# Tools

I used `Spring Framework` for dependency injections, instead of manually creating service objects.
Also, Spring comes with built-in support for REST and Scheduling.

`JPA (Java Persistence API) with PostgreSQL` for data persistence. Using ORM, JPA abstracts the DB layer, so it's easy
to use, no need to manually do SQL queries.

For live notification I chose to use `WebSockets`. It comes integrated with Spring and it's ideal for pushing
server-side notifications to clients.

The application is `Dockerized`, so it can be easily run on any machine.

# Architecture

The implementation follows a classic layered architecture with:

- controllers
- model
- persistence
- services
- validators

As for the Model, below is the class diagram. I chose these entities because it makes the app easily extensible.
If tomorrow we need to add more information about a Brand, we can do that easily. If new types of discounts appear, that
can integrated easily.

![Class Diagram](/Readme_Images/Diagram.png)

`Product` is the base class. It represents a product that exists regardless of whether it is sold somewhere or not.
It has simple Product information

1. `Brand` - eg: Zuzu, Milka
2. `Category` - eg: Lactate, Carne, Dulciuri
3. `Unit` - eg: l, ml, g, kg, buc
4. `Quantity` - eg: 100, 0.5, 3

`Market Product` is a Product that is on the Market. For example Milka Chocolate represents a Product, and the Milka
Chocolate that is sold at Lidl for 6RON, represents a Market Product.

1. `Retailer` - eg: Lidl, Kaufland, Auchan
2. `Curreny` - eg: RON, EUR
3. `Price with discount` - the entity has a method where it calculates its price with the current discount.
4. `Value per unit` - method that calculates a products price per unit, where the unit is either `kg`, `l` or `buc`.
   -eg: for a yogurt that has 250g and costs 3RON, the method will return `12RON per kg`

`Price History` keeps track of all the prices a MarketProduct has had, and all of its discounts.

# Important Assumptions

`Currency` - yes there exists a way to add multiple currencies in the app, but as of right now, for simplicity, when
comparing
prices, the currency is not taken in consideration. A good extension for the app would be to add a way to convert
currencies,
and then products could have actual different currencies.

`Discounts` - for simplicity, a MarketProduct can NOT have 2 or more discounts at a time. If it did, we would need to
establish a clear
rule for how discounts should be stacked.

`No CVS data` - I did not directly get the data from the CSV, as I thought for demo purpose it would be too much, to be
able to
observe it in Postman. But I did use the given data when testing the application. The data was added manually through
API calls,
and automated API calls.

`DTOs` - data from the APIs comes through DTOs, but the data that is sent as a result, is mostly raw data. In a real
world
scenario, this data would also have to be a DTO depending on what the FE requires.

# Features explained and demonstrated

## Splitting a Basket into shopping lists

A user can add products to the basket, products not marketProducts!

`Example` Maricica wants to make a cake, but she isn't rich, and so she wants to buy the cheapest products she can find.
She adds in the app to her basket, eggs, chocolate, flour and milk.
So Maricica wants a shopping list for each store, based on whichever one has the cheapest products.

This is the current situation at the stores regiestered inside the app:

![MarketProducts available at the retailers inside the app](/Readme_Images/Available_MarketProducts.png)

Maricica doesn't care, she doesn't want to manually find the cheapest products, she just wants some shopping lists,
so she generates them and this is what she gets:

![Maricica's Shopping Lists](/Readme_Images/ShoppingLists.png)

Now Maricica is happy she knows what to buy from where, and she can finally bake her cake!

## Best Discounts

Will returns the best discounts available inside the application. It will return X% of products,
based on what it is requested.

So if I am a FE dev and I want to make the UI display top 10% best discounts, I could use this API like this:

![Best Discounts API Call from Postman](/Readme_Images/Best-Discounts-API.png)

The app would sort the products in descending order based on current discount, and would return
a list of 10% of those products.

## New Discounts

Returns the discounts that started being in effect from yesterday on (so approximately 24hrs).
The current implementation only use LocalDate and no time (like the CSVs) so that's why this approximation is needed.

## Price History

Let`s take an example:

![Salami history of prices and discounts](/Readme_Images/Salami_Prices_Discounts.png)

This Salami has had a few changes over time. The prices started at 20, it then had 2 discounts, the prices went up to 40
and then had two more discounts.

This is what the graph for this product could look like:

![Salami history displayed in graph form](/Readme_Images/SalamiGraph.png)

Price starts at 20RON, has some discounts, but on 20.05 the prices jumps to 40RON, and simultaneously
a discount is added for 50%. This basically means there is no real change in the final price for the product,
so the graph remains unchanged from 16.05 to 23.05. The final value in the graph represents the end of the last discount
and is displayed regardless or whether we have reached that date (current date is 25.05).

This functionality can be easily extended to offer price histories for only the prices within discounts.
Or a history just for discounts, to observe the trends.

# Price per unit

The `MarketProduct` has a method that computes the value per unit where unit is `kg`if the item is measured in
kg or g, or it can be `l` if the item is measured in ml or l. And for `buc` it remains.

`Example` For the Salami above, for `500g`the price without discount is 40
But with the discount, the price per unit ends up being `72RON / kg`.

![API call to show the price per unit for the Salami above](/Readme_Images/SalamiValuePerUnit.png)

# Price Alerts

The user may set up an `Alert` for a `Product`, and when the price changes or a discount appears the users will receive
a notification.

The `Notification` is sent via `WebSocket` and it is also saved in the DB, to be able to show it to the user,
even if it is not currently within the app.

I have used a `Scheduler` to periodically check for price difference, for products that have Alerts.
And the checking for `Alerts` is also triggered when a product receives a price or discount update.

![Scheduler Implementation](/Readme_Images/Scheduler.png)

For testing purposes this Scheduler runs really often, in a normal scenario we could have ti run once every 24hrs.

To test the functionality, the notifications can be checked through an API call `http://localhost:8080/api/notis`.
A `Notification` has the MarketProduct for which it was sent along with a message and a date, so you can order them on
the FE.

# How to run

The app can be run using Docker. If you have Docker installed you can run the following command:

`docker compose up --build` To build your images and run the containers.
`docker compose up` You can run this command if you've already built the images and just want to restart the containers.

You can always start and restart the containers from within DockerHub.

![DockerHub](/Readme_Images/DockerHub.png)

# Improvements and Extensibility

There quite a few ways the app can be significantly improved, I will state a few here:

1. Adding update and delete options for all entities;
2. Adding options to filter the shopping lists by store;
3. Extending the information about retailers, to add their location, and be able to filter
   store by location;
4. Similar with currency;

The last go hand in hand, because if you go in vacation to somewhere you don't know, you could
ask the app to only give you stores from that area

5. Direct comparisons between stores, to let user see the differences not only for the same product.
6. Expand the discount types. There are lots of different types of discounts that can stack up in different ways.

# All Endpoints

### Users

> #### ➕ Create User
> **POST** `/api/auth/signup`  
> **Request Body (JSON):**
> ```json
> {
>   "username": "ana"
> }
> ```

> #### 📄 Get All Users
> **GET** `/api/user`

---

### Category

> #### ➕ Create Category
> **POST** `/api/category`  
> **Request Body (JSON):**
> ```json
> {
>   "name": "oua"
> }
> ```

> #### 📄 Get All Categories
> **GET** `/api/category`

---

### Brand

> #### ➕ Create Brand
> **POST** `/api/brand`  
> **Request Body (JSON):**
> ```json
> {
>   "name": "Agricola"
> }
> ```

> #### 📄 Get All Brands
> **GET** `/api/brand`

---

### Product

> #### ➕ Create Product
> **POST** `/api/product`  
> **Request Body (JSON):**
> ```json
> {
>   "name": "oua",
>   "categoryId": 5,
>   "brandId": 4,
>   "quantity": 10,
>   "unit": "buc"
> }
> ```

> #### 📄 Get All Products
> **GET** `/api/product`

---

### Retailer

> #### ➕ Create Retailer
> **POST** `/api/retailer`  
> **Request Body (JSON):**
> ```json
> {
>   "name": "Kaufland"
> }
> ```

> #### 📄 Get All Retailers
> **GET** `/api/retailer`

---

### Currency

> #### ➕ Create Currency
> **POST** `/api/currency`  
> **Request Body (JSON):**
> ```json
> {
>   "name": "RON"
> }
> ```

> #### 📄 Get All Currencies
> **GET** `/api/currency`

---

### Market Product

> #### ➕ Create MarketProduct
> **POST** `/api/market-product`  
> **Request Body (JSON):**
> ```json
> {
>   "productId": 7,
>   "price": 13,
>   "dateAddedPrice": "2025-05-08",
>   "currencyId": 1,
>   "retailerId": 1,
>   "startDateDiscount": "",
>   "endDateDiscount": "",
>   "valueDiscount": 0
> }
> ```

> #### Update Price
> **PUT** `/api/market-product/{{prod-id}}/price`  
> **Request Body (JSON):**
> ```json
> {
>   "price": 6.6,
>   "priceAddedDate": "2025-05-25"
> }
> ```

> #### Update Discount
> **PUT** `/api/market-product/{{prod-id}}/discount`  
> **Request Body (JSON):**
> ```json
> {
>   "startDateDiscount": "2025-05-20",
>   "endDateDiscount": "2025-05-29",
>   "valueDiscount": 25
> }
> ```

> #### New Discounts
> **GET** `/api/market-product/new-discounts`

> #### Best Discounts
> **PUT** `/api/market-product/best-discounts`  
> **Request Body (JSON):**
> ```json
> {
>   "howManyProducts": 10
> }
> ```

---

> #### 📄 A Product's History
> **GET** `/api/market-product/{{prod-id}}/history`

> #### 📄 Histories for All Products
> **GET** `/api/market-product/histories`

> #### 📄 Histories by Retailer
> **GET** `/api/market-product/histories?retailerId=1`

> #### 📄 Histories by Retailer & Category
> **GET** `/api/market-product/histories?retailerId={{id}}&categoryId={{id}}`

> #### 📄 Histories by Brand
> **GET** `/api/market-product/histories?brandId={{id}}`

> 💡 These filters can be stacked in any way.

---

> #### ➕ Create or Update Basket for User
> **POST** `/api/basket`  
> **Request Body (JSON):**
> ```json
> {
>   "userId": 1,
>   "productIds": [6]
> }
> ```

> #### 📄 Generate Shopping Lists
> **GET** `/api/basket/shopping-lists`  
> **Request Body (JSON):**
> ```json
> {
>   "userId": 1
> }
> ```

---

### Notifications

> #### ➕ Create an Alert for a Product
> **POST** `/api/alert`  
> **Request Body (JSON):**
> ```json
> {
>   "userId": 1,
>   "productId": 5,
>   "threshold": 16
> }
> ```

> #### 📄 Get a User's Notifications
> **GET** `/api/notis`  
> **Request Body (JSON):**
> ```json
> {
>   "userId": 1
> }
> ```


---

# Thank you!
