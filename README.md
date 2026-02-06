# BookStore Application

A full-stack web application for managing a bookstore, featuring a React frontend and a Jakarta EE (Jersey) backend.

## Technology Stack

- **Frontend**: React, Vite
- **Backend**: Java with Jakarta EE (using Jersey for REST API)
- **Containerization**: Docker & Docker Compose
- **Server**: Tomcat 10.1 (via Docker)

## Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running.
- [Git](https://git-scm.com/) installed.

## Getting Started

The easiest way to run the application is using Docker Compose, which sets up both the frontend and backend services automatically.

1.  **Clone the repository**
    ```bash
    git clone https://github.com/DihanWanasinghe/BookStore.git
    cd BookStore
    ```

2.  **Start the application**
    ```bash
    docker-compose up --build
    ```

3.  **Access the Application**
    - **Frontend**: Open [http://localhost:5173](http://localhost:5173) in your browser.
    - **Backend API**: The API is available at [http://localhost:8080/api](http://localhost:8080/api).

## Project Structure

- `backend/`: Java Maven project containing the REST API logic, models, and data access objects.
- `frontend/`: React application created with Vite.
- `docker-compose.yaml`: Configuration to orchestrate the backend and frontend containers.

## API Endpoints

The application provides RESTful endpoints for managing:
- Books (`/api/books`)
- Authors (`/api/authors`)
- Customers (`/api/customers`)

Check the `frontend/src/App.jsx` or the API code for detailed request/response formats.
