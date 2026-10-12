# 🍽️ Restaurant Customer Service Desktop Application

A modern desktop application built with **JavaFX** for the frontend and powered by **LangChain4j** for intelligent, context-aware AI conversational support. This application serves as an interactive customer service hub where users can ask menu questions, make reservations, inquire about operating hours, and receive real-time assistance powered by Large Language Models (LLMs).

---

## ✨ Key Features

*   **🤖 AI-Powered Chat Assistant:** Integrated with LangChain4j to deliver natural, responsive customer service interactions.
*   **🖥️ Modern JavaFX UI:** Clean, responsive desktop user interface designed for ease of use.
*   **💬 Context-Aware Conversations:** Maintains chat history and leverages system prompts tailored specifically to restaurant operations (e.g., menu items, dietary restrictions, booking policies).
*   **⚡ Real-Time Response Streaming:** Seamless UI updates as the AI generates replies.
*   **🛠️ Extensible Tool Integration:** Easily connect the LLM to local databases or backend services (such as reservation systems or inventory lookups) using LangChain4j tools.

---

## 🛠️ Tech Stack

*   **Language:** Java (JDK 17 or higher)
*   **Frontend UI:** JavaFX (FXML / CSS)
*   **AI Framework:** LangChain4j
*   **Build Tool:** Maven / Gradle

---

## 📂 Project Structure

```text
Restaurant-Customer-Service-Desktop-Application/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/restaurant/app/
│   │   │       ├── controller/   # JavaFX UI Controllers
│   │   │       ├── service/      # LangChain4j AI & Business Logic Services
│   │   │       ├── model/        # Data models & DTOs
│   │   │       └── MainApp.java  # Application Entry Point
│   │   └── resources/
│   │       ├── view/             # FXML UI Layouts
│   │       └── styles/           # CSS Stylesheets
└── pom.xml / build.gradle        # Project dependencies
```

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed on your system:
*   **Java Development Kit (JDK 17+)**
*   **Maven** or **Gradle**
*   An API Key from your preferred LLM provider (e.g., OpenAI, Anthropic, or a local model via Ollama).

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/winstonhhz/Restaurant-Customer-Service-Desktop-Application.git
   cd Restaurant-Customer-Service-Desktop-Application
   ```

2. Configure your environment variables or configuration file to include your LLM API key. For example, create an `.env` file or set your system environment variable:
   ```bash
   export OPENAI_API_KEY="your-api-key-here"
   ```

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn javafx:run
   ```

---

## ⚙️ Configuration

In your LangChain4j service configuration, you can customize the AI assistant's persona, system prompt, and underlying model parameters:

```java
ChatLanguageModel model = OpenAiChatModel.builder()
    .apiKey(System.getenv("OPENAI_API_KEY"))
    .modelName("gpt-4o-mini")
    .temperature(0.7)
    .build();
```

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/winstonhhz/Restaurant-Customer-Service-Desktop-Application/issues).

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## Notice

This is a GROUP Work, not a project done individually.
