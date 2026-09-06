# Indiana Food Locator

## Overview

Indiana Food Locator is a Java Spring Boot web application designed to help Indiana residents locate food assistance resources, including food pantries, soup kitchens, mobile pantries, and other food assistance services.

The application was developed as an IT capstone project and provides a centralized, user-friendly platform where visitors can search for food resources, view location details, create an account, and save frequently used resources. Administrators can securely manage food location information through a role-protected administrative portal.

## Project Status

**MVP Complete – Version 1.0**

The Minimum Viable Product (MVP) has been successfully implemented and tested. Core public, authenticated-user, and administrative functionality is operational.

## Features

### Public Features

- Search for active food assistance locations by city
- Search by ZIP code
- Filter locations by category
- View detailed food location information
- View About and Contact pages
- Submit the Contact form
- Responsive user interface

### User Features

- User registration
- Secure login and logout
- Password encryption using BCrypt
- Save food locations to Favorites
- Remove saved Favorites
- Role-based access control

### Administrative Features

- Secure ADMIN-only dashboard
- View food assistance locations
- Add new food locations
- Edit existing food locations
- Deactivate food locations
- Prevent inactive locations from appearing in public search results

## Technologies

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

### Frontend

- Thymeleaf
- HTML5
- CSS3
- Bootstrap 5

### Database

- MySQL

### Development Tools

- Visual Studio Code
- Git
- GitHub
- Maven Wrapper
- draw.io

## Application Architecture

Indiana Food Locator uses a layered Spring Boot architecture:

- **Presentation Layer** – Spring MVC controllers and Thymeleaf templates
- **Service Layer** – Business logic and application operations
- **Repository Layer** – Spring Data JPA repositories
- **Persistence Layer** – MySQL database and JPA entities
- **Security Layer** – Spring Security authentication and role-based authorization

The primary application entities are:

- `User`
- `FoodLocation`
- `Favorite`

A User can save multiple food locations through Favorite records.

## Security

The application uses Spring Security to provide authentication and authorization.

Two primary roles are supported:

- **USER** – Can access public functionality and manage personal Favorites.
- **ADMIN** – Can access all public functionality and the protected administrative portal.

Passwords are stored using BCrypt hashing. Administrative routes under `/admin/**` require the ADMIN role.

Sensitive local configuration information, including database credentials and administrative passwords, is excluded from Git using `.gitignore`.

## Testing

The completed MVP was validated using automated and manual functional testing.

### Automated Testing

The automated test suite contains **26 passing tests**, including:

- Food location service unit tests
- User registration service unit tests
- Favorites service unit tests
- Spring Security integration tests
- Food location repository/database integration tests
- Spring application context testing

Run the automated test suite with:

```powershell
.\mvnw.cmd test
```

### Functional Testing

The application also completed **14 manual functional tests** covering:

- Public home page access
- Food location search
- Location details
- User registration
- User login
- Adding Favorites
- Removing Favorites
- USER role restrictions
- ADMIN authorization
- Adding food locations
- Editing food locations
- Deactivating food locations
- Logout/session protection
- Contact form submission

All 14 functional tests passed.

Manual testing also identified minor user-interface issues involving the Location Details footer and successful-login feedback. Both issues were corrected and successfully retested before completion of the MVP.

## Local Setup

### Prerequisites

Before running the application, install:

- Java 21 or later
- MySQL
- Git

### Clone the Repository

```bash
git clone <repository-url>
cd indiana-food-locator
```

### Database

Create a MySQL database:

```sql
CREATE DATABASE indiana_food_locator;
```

Configure your local database credentials using the application's local configuration.

Do not commit database passwords or other credentials to GitHub.

### Run the Application

Using the Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

After startup, access the application at:

`http://localhost:8080`

## Future Enhancements

Potential enhancements beyond the MVP include:

- Interactive mapping and geolocation
- Distance-based food resource searches
- Expanded search and filtering options
- Email notifications
- Password reset functionality
- Email/account verification
- Improved accessibility features
- Additional administrative reporting
- Deployment to a production cloud environment
- Expansion beyond the initial Indiana food assistance dataset

## Project Documentation

The project was designed using supporting software engineering artifacts, including:

- System Architecture Diagram
- Entity Relationship Diagram (ERD)
- UML Class Diagram
- User Interface Wireframes
- Administrative Interface Wireframes
- Automated and Functional Testing Documentation

## Author

**Tevin Davis**

Indiana Food Locator  
IT Capstone Project
