# Webshop Project

This project is a full-stack e-commerce platform developed using Java and Spring Boot for the backend, and Vue.js for the frontend. The application supports multiple user roles, each with specific functionalities, creating a dynamic and user-friendly shopping experience.

## Project Overview

The Webshop platform allows unsigned users to browse products and categories, while signed users with different roles (Buyer, Customer, Admin) have access to a variety of features such as purchasing, rating, and managing products. The platform is designed with pagination for better product viewing and incorporates robust filtering and searching capabilities.

## Technologies Used

- **Backend**: Java, Spring Boot
- **Frontend**: Vue.js
- **Database**: SQLite

## Features

### Unsigned User

- **View Products**: Browse available products with pagination for easy navigation.
- **Filter and Search**: Filter products by various criteria and search for specific items.
- **View Categories**: Browse and search product categories.
- **Register and Login**: Create an account or log in to access more features.

### Signed User: Buyer Role

- **Update Profile**: Edit personal information.
- **View Other Profiles**: See the profiles of other users.
- **Purchase Products**: Buy products either at a fixed price or through an auction.
- **Rate Users**: Provide ratings for other users.
- **View Reviews**: See reviews from other users.
- **Report Users (Customer Role)**: Report users who have the Customer role.

### Signed User: Customer Role

- **Update Profile**: Edit personal information.
- **View Other Profiles**: See the profiles of other users.
- **Place Sales**: List products for sale.
- **End Auctions**: Conclude auctions for products.
- **Update Product Information**: Modify details of listed products.
- **Rate Users (Buyer Role)**: Provide ratings for users with the Buyer role.
- **View Reviews**: See reviews from other users.
- **Report Users (Buyer Role)**: Report users who have the Buyer role.

### Signed User: Admin Role

- **Manage Reviews**: View, edit, and delete user reviews.
- **Handle Reports**: Accept or decline user reports.

## Setup and Installation

1. **Clone the Repository**:
2. **Backend Setup**:
- Navigate to the backend directory.
- Configure the database settings in `application.properties`.
- Run the Spring Boot application:


3. **Frontend Setup**:
- Navigate to the frontend directory.
- Install dependencies:
  ```
  npm install
  ```
- Run the Vue.js application:
  ```
  npm run serve
  ```

4. **Access the Application**:
- Open a browser and navigate to `http://localhost:[port]`.
