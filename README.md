# TravelApp
Application for selling, buying and exchenging vacations.

# Main Features:

## Vacations are:
* Dates:Depature and landing dates
* Destination
* Price
* Number of tickets for sell
* Hotel details (optional)

## Users can:
* Post vacations for sell or exchange
* Buy\Exchange vacations
* Send buy\exchange requests for other users

## Main UI
* Display all available vacations for sell\exchange.
* Search users.
*Update user personal details.

# Backend:

## Users have:
* Display Name
* Avatar
### Authentication
  *Credentials (username\password)
* Full name
* City of residence
* Date of birth

## Vacations post have:
* Publisher username
* ID
* Vacations details
* Price

# DB Schema
The DB is based on SQLite, . There are 3 main schemas.

## Vacations
* Publisher username
* ID
* Dates:Depature and landing dates
* Destination
* Price
* Flight number
* Hotel details (optional)
* Number of tickets for sale
* Vacation type (Urban or Exotic)
* Publisher type (private or travel agency)


## Users
* Username
* Password
* Date of birth
* Full name
* City of residence
* Avatar/Profile picture

## Sales
* Vacation ID
* Seller username
* Buyer username
* Date of the sale
* Final price
* Buyer request id
* Seller approval id
