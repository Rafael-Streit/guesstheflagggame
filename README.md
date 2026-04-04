# 🏳️ Flag Guesser

> A learning project built to get familiar with **Spring Boot**, **Vaadin**, **Dependency Injection** and **REST APIs**.

---

## 🎯 About this project

This is a hands-on exercise project — not a production app. The goal was to learn how Spring Boot and Vaadin work together by building something fun: a flag guessing game.

**What I practised:**
- Spring Boot fundamentals (application context, component scan, auto-configuration)
- Dependency Injection with `@Service`, `@Autowired` and constructor injection
- Building REST APIs with `@RestController`
- Creating UI components with Vaadin

---

## 🎮 Features

- 🌍 A random country flag is displayed each round
- 🔤 Choose the correct country from multiple options
- 🏆 Score tracker keeps count of correct answers

---

## 🛠️ Tech stack

| Technology | Version |
|------------|---------|
| Java       | 25+     |
| Spring Boot | 4.0x    |
| Vaadin     | 24.0x   |
| Build tool | Maven   |

---

## 🚀 Getting started

### Prerequisites

- Java 25 or higher
- Maven 3.8+

### Run locally

```bash
git clone https://github.com/your-username/flag-guesser.git
cd flag-guesser
mvn spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080) in your browser.

---

## 📝 Notes & known issues

This project is intentionally kept simple as a learning exercise. A few things I'd improve with more time:

- [ ] Add more answer options per round
- [ ] Difficulty levels (easy = big countries, hard = small/obscure ones)
- [ ] Highscore / leaderboard
- [ ] Unit tests for the service layer

---

*Built for learning purposes — feedback welcome!*