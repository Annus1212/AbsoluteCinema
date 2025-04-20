# Absolute Cinema

## Project Overview
Absolute Cinema is a Java web application designed to manage a cinema's operations, including user management, ticket and session booking, loyalty programs, snacks inventory, movie catalog management, and feedback collection. The application utilizes PostgreSQL as its database and employs an ORM framework for data management, along with JTE as the template engine for rendering views.

## Features
- **User Management**: Manage user accounts, including registration, login, and profile management.
- **Ticket and Session Booking Management**: Handle ticket purchases and manage movie screening sessions.
- **Loyalty Program Integration**: Implement a loyalty program to reward frequent customers.
- **Snacks Inventory and Sales Management**: Manage the inventory of snacks and handle sales transactions.
- **Movie Catalog Management**: Maintain a catalog of movies available for screening.
- **Feedback Collection and Analysis**: Collect customer feedback and analyze it for service improvement.
- **Admin Analytics**: Provide analytics and reporting features for administrators to monitor operations.

## Technologies Used
- **Java**: The primary programming language for the application.
- **Spring Boot**: Framework for building the web application.
- **PostgreSQL**: Database management system for storing application data.
- **JTE**: Template engine for rendering dynamic web pages.
- **Maven**: Build automation tool for managing project dependencies.

## Setup Instructions
1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   ```
2. **Navigate to the Project Directory**:
   ```
   cd absolute-cinema
   ```
3. **Configure Database**: Update the `application.properties` file with your PostgreSQL database connection settings.
4. **Build the Project**: Use Maven to build the project:
   ```
   mvn clean install
   ```
5. **Run the Application**: Start the Spring Boot application:
   ```
   mvn spring-boot:run
   ```
6. **Access the Application**: Open your web browser and go to `http://localhost:8080`.

## Contribution
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.