
# 🚀 Job Portal Web Application

**Spring Boot | Docker | GitHub Actions CI/CD | AWS EC2**

## 📌 Overview

A **production-deployed Job Portal web application** built using **Spring Boot MVC** and **Thymeleaf**, designed to support Job Seekers and Recruiters with secure authentication, role-based access control, and database-backed job management.

The application is **fully containerized using Docker**, deployed on **AWS EC2**, and updated automatically via a **GitHub Actions CI/CD pipeline** — enabling zero manual deployment after code changes.

---

## 🛠 Tech Stack

**Backend**

* Java 21
* Spring Boot (MVC, Security)
* Maven

**Frontend**

* Thymeleaf
* HTML, CSS
* Bootstrap

**Database**

* MySQL

**DevOps & Cloud**

* Docker
* Docker Compose
* GitHub Actions (CI/CD)
* AWS EC2 (Ubuntu)
* Elastic IP

**Security**

* Spring Security (Authentication & Authorization)
* Externalized configuration & environment-based secrets

---

## 🧱 System Architecture

```
Client (Browser)
     ↓
Spring Boot MVC Application (Docker Container)
     ↓
MySQL Database (Docker Volume)
```

---

## ✨ Key Features

* User authentication and role-based authorization (Job Seeker / Recruiter)
* Job posting and management for recruiters
* Job browsing and application flow for job seekers
* Secure session handling with Spring Security
* Persistent MySQL storage using Docker volumes
* Dockerized application and database
* Fully automated CI/CD deployment pipeline

---

## 🚀 CI/CD Pipeline (GitHub Actions)

The project uses **GitHub Actions** for automated build and deployment.

**Workflow file:**
`.github/workflows/deploy.yml`

### CI/CD Flow

1. Code push to `main` branch triggers the pipeline
2. Spring Boot application is built using Maven
3. Docker image is built from the Dockerfile
4. Docker image is pushed to Docker Hub
5. AWS EC2 pulls the latest image
6. Containers are restarted using Docker Compose
7. Application updates automatically with **no manual SSH deployment**

> ✅ **Zero manual deployment**
> ✅ **Production-ready automation**

---

## 🔐 Configuration & Secrets Management

* Sensitive values are **not hardcoded**
* Environment variables are injected during deployment
* GitHub Actions Secrets are used for credentials
* MySQL data is persisted using Docker volumes

---

---

## 🌍 Live Deployment

The application is **live and publicly accessible**, deployed on **AWS EC2** using Docker and GitHub Actions CI/CD.

> Deployed with Elastic IP for stable public access.

---






