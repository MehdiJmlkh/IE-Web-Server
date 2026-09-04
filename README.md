# Custom Web Server & Article Sharing System

This repository contains an implementation of a custom web server and an article-sharing system developed as an Internet Engineering course assignment at the University of Tehran.

The project focuses on implementing an HTTP server from scratch while keeping web-server functionality separate from the application's business logic. The application provides a platform for publishing, searching, viewing, and referencing articles.

## Features

### Custom Web Server

A web server is implemented from scratch to handle client HTTP requests and responses.<br>
The web server uses Reflection to dynamically create responses.<br>
Web-server logic is separated from the application logic.<br>

### Article-Sharing System

An article-sharing application is implemented with HTML-based pages.<br>
New articles can be created with a title, abstract, and body.<br>
Articles can be searched by their content.<br>
Cited articles are displayed on the article page and linked to their respective pages.<br>
Articles are ordered by their number of references.

## How to Run

### Using the Docker Hub Image

Pull and run the latest image from Docker Hub:

```bash
docker pull mehdijmlkh/ie-web-server:latest
docker run -p 8080:8080 mehdijmlkh/ie-web-server:latest
```

### Building the Image Locally

Alternatively, you can build the Docker image locally:

```bash
git clone https://github.com/MehdiJmlkh/IE-Web-Server.git
cd IE-Web-Server

docker build -t ie-web-server:latest .
docker run -p 8080:8080 ie-web-server:latest
```

Once the server is running, open http://localhost:8080 in your browser to access the application.
