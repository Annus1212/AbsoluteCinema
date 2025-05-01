# Absolute Cinema

## Project Overview
Absolute Cinema is a Java web application designed to manage a cinema's operations, including user management, ticket and session booking, loyalty programs, snacks inventory, movie catalog management, and feedback collection. The application utilizes PostgreSQL as its database and employs an ORM framework for data management, along with Pebble as the template engine for rendering views.

## Features
- **User Management**: Manage user accounts, including registration, login, and profile management.
- **Ticket and Session Booking Management**: Handle ticket purchases and manage movie screening sessions.
- **Loyalty Program Integration**: Implement a loyalty program to reward frequent customers.
- **Snacks Inventory and Sales Management**: Manage the inventory of snacks and handle sales transactions.
- **Movie Catalog Management**: Maintain a catalog of movies available for screening.
- **Feedback Collection and Analysis**: Collect customer feedback and analyze it for service improvement.
- **Admin Analytics**: Provide analytics and reporting features for administrators to monitor operations.
- **Responsive UI**: Modern, user-friendly interface with dark mode support and gradient designs.

## Technologies Used
- **Java**: The primary programming language for the application.
- **Spring Boot**: Framework for building the web application.
- **PostgreSQL**: Database management system for storing application data.
- **Pebble**: Template engine for rendering dynamic web pages.
- **Gradle**: Build automation tool for managing project dependencies.
- **Bootstrap 5**: Frontend framework for responsive design.
- **CSS3**: Custom styling with gradients, animations, and dark theme.

## UI Design
- **Modern Dark Theme**: Sleek dark mode interface with purple and blue gradients.
- **Responsive Design**: Fully responsive layout that works on mobile and desktop.
- **Gradient Accents**: Custom gradient effects for buttons, headings, and interactive elements.
- **Animations**: Subtle animations and transitions for enhanced user experience.
- **Custom Components**: Tailored UI elements for cinema-specific features like seat selection.

## Setup Instructions
1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   ```
2. **Navigate to the Project Directory**:
   ```
   cd AbsoluteCinema
   ```
3. **Configure Database**: Update the `application.properties` file with your PostgreSQL database connection settings.
4. **Build the Project**: Use Gradle to build the project:
   ```
   ./gradlew build
   ```
5. **Run the Application**: Start the Spring Boot application:
   ```
   ./gradlew bootRun
   ```
6. **Access the Application**: Open your web browser and go to `http://localhost:8080`.
7. **Default Accounts**: Use the following test accounts:
   - Admin: username `admin`, password `admin`
   - Employee: username `employee`, password `employee`
   - Regular User: Register a new account

## Authentication
The application uses Spring Security for authentication and authorization:
- **User Roles**: Admin, Employee, and User roles with different access levels
- **Secure Password Storage**: Passwords are stored using a secure password encoder
- **Login/Registration**: Custom login and registration flows with validation

## Contribution
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.