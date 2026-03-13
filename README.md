# ci-cd-ssd-practical

A Spring Boot REST API built for a DevSecOps assignment. The project demonstrates how to embed security gates into a CI/CD pipeline using real tools — not theory.

---

## What this project does

The app is a minimal Java API with two endpoints:

- `GET /api/search?username=` — searches for a user (intentionally vulnerable in the original version)
- `GET /api/health` — returns a status check

It was built with deliberate security flaws — a hardcoded credential and a raw SQL query — so the pipeline tools had something real to find and report.

---

## Pipeline

Three security gates run automatically on every push via GitHub Actions.

| Gate | Tool | What it checks |
|---|---|---|
| 1 | Gitleaks | Scans git history for hardcoded secrets and credentials |
| 2 | Semgrep | Static analysis of Java source for injection flaws and insecure patterns |
| 3 | Trivy | Scans `pom.xml` dependencies against the CVE database |

Each gate must pass before the next one runs.

---

## Finding and fix

Trivy detected two HIGH severity vulnerabilities in `jackson-core 3.0.4`:

- **CVE-2026-29062** — Denial of Service via excessive JSON nesting
- **GHSA-72hv-8253-57qq** — Number Length Constraint Bypass in Async Parser

**Fix:** Pinned `jackson-bom.version` to `2.17.2` in `pom.xml`. The following pipeline run showed zero vulnerabilities.

---

## Stack

- Java 17
- Spring Boot 3.x
- Maven
- H2 in-memory database
- GitHub Actions

---

## Running locally

```bash
git clone https://github.com/gandhidev1113-glitch/ci-cd-ssd-practical.git
cd ci-cd-ssd-practical
./mvnw spring-boot:run
```

Then hit `http://localhost:8080/api/health` to confirm it's running.

---

## Project structure

```
.
├── .github/
│   └── workflows/
│       └── devsecops-pipeline.yml   # 3-gate security pipeline
├── src/
│   └── main/
│       ├── java/com/example/secureapi/
│       │   ├── SecureApiApplication.java
│       │   └── UserController.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## Assignment context

Built for the DevSecOps and Secure CI/CD practical (TD Session 4). Covers threat modeling, shift-left security, and the difference between SAST, SCA, and DAST in a real pipeline context.
