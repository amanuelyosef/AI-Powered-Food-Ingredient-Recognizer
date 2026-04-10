# 🍔 AI-Powered Food Ingredient Recognizer

A modern, AI-driven Android application built with **Kotlin** and **Jetpack Compose** that allows users to snap or upload photos of meals and instantly discover their core ingredients using artificial intelligence helping users with dietary restrictions, allergies, and food sensitivity concerns make safer eating decisions..


## 🌟 Overview
Many people struggle to confidently eat unfamiliar dishes due to allergies, intolerances, or dietary preferences (e.g., gluten-free, vegan, nut-free).  
This project aims to reduce that uncertainty by combining:

- **Image-based food understanding**
- **API-driven ingredient extraction**
- **Mobile-first UX for quick decision-making**

The result is a practical assistant that gives users instant visibility into what may be in their meal.


## 🛠️ Tech Stack & Libraries
This application is built on a cutting-edge Android technology stack:

*   **Language:** [Kotlin](https://kotlinlang.org/) (JVM Target 11)
*   **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3 & Extended Icons)
*   **Architecture:** MVVM (Model-View-ViewModel) + Clean Architecture approach
*   **Dependency Injection:** [Dagger Hilt](https://dagger.dev/hilt/)
*   **Networking:** [Retrofit2](https://square.github.io/retrofit/) & OkHttp3 Logging Interceptor for seamless REST API communication with the AI models.
*   **Image Loading:** [Coil](https://coil-kt.github.io/coil/compose/) (Image loading for Compose)
*   **Serialization:** Kotlinx Serialization & Gson
*   **Navigation:** Jetpack Navigation Compose

## 🚀 Features
*   📸 **Image Capture & Gallery:** Take real-time photos of your food or select images from your device gallery.
*   🧠 **AI-Powered Analysis:** Securely send images to a backend AI/ML model via REST API to detect patterns and recognize food items.
*   🥗 **Detailed Ingredient Breakdown:** View a structured list of ingredients, dietary flags (e.g., contains dairy, nuts), and potential recipe matches.
*   📱 **Responsive Modern UI:** A beautiful, accessible, and fluid user interface adhering to Material Design 3 guidelines.


## 📐 Architecture Setup
The project structure is optimized for scalability and separation of concerns. It is divided into distinct layers (currently scaffolding starting with the `presentation` layer):

```text
app/src/main/java/com/nisrmarket/foodingredientrecognizer/
│
├── FIRApplication.kt       # Application class (Hilt Entry Point)
├── presentation/           # UI Layer: Activities, Compose Screens, ViewModels
│   ├── MainActivity.kt     # Single Activity Entry
│   └── ui/                 # Reusable Compose widgets, themes, and screens
├── domain/                 # Use cases and domain models
└── data/                   # Repositories, Retrofit API interfaces, DTOs
```

## ⚙️ Getting Started

### Prerequisites
- Android Studio (latest stable)
- JDK 11
- Android SDK platform matching compile SDK (36)
- Gradle (via wrapper included)

### Steps

1. Clone the repository
   ```bash
   git clone https://github.com/amanuelyosef/AI-Powered-Food-Ingredient-Recognizer.git
   cd AI-Powered-Food-Ingredient-Recognizer
   ```

2. Open in Android Studio

3. Sync Gradle

4. Set up any required API keys or endpoint configuration (if required by your implementation)

5. Run on:
    - Emulator (API 24+)
    - Physical Android device


## 🛣️ Future Improvements

- Ingredient confidence visualization
- Allergen profile personalization (e.g., “avoid tree nuts”)
- History of analyzed meals
- On-device model fallback for offline use
- Multi-language ingredient explanations
- Explainability mode (“why this ingredient was predicted”)

---


## 🤝 Contributing
Contributions, issues, and feature requests are welcome!
If you want to contribute to the UI, API integration, or the upcoming ML features:
1. Fork the project.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

## 📝 License
Distributed under the Apache License. See `LICENSE` for more information.


---

## Summary

This project is a strong foundation for an impactful real-world mobile AI assistant.  
Its current technology choices (Compose, Hilt, Retrofit, ViewModel, Coil) are modern, maintainable, and scalable.  
With documented API contracts, safety messaging, and test expansion, it can evolve into a production-grade dietary transparency tool.