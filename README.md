# 📅 Good Will App

A dynamic and user-friendly **Good Will App** for Android that allows users to explore, contribute, and manage events. Integrated with **Firebase** for real-time data and **Razorpay** for secure payments. Stay informed with **push notifications**.

---

## ✨ Features

- 🔐 Admin login for secure event management
- 📋 Add, and delete events
- 🧑‍🤝‍🧑 Contributor management screen
- 📄 Event list view with dynamic dropdown from Firebase
- 💳 Razorpay payment gateway integration
- 🔔 Push notifications via Firebase
- 💅 Clean and modern UI (Screens: Splash, Admin Login, Add Event, Events List, Contributors)

---

## 🛠️ Built With

- **Kotlin**

- **Firebase** – Realtime Database, Authentication, Cloud Messaging (Push Notifications)

- **Razorpay SDK** – Payment Integration

- **Material Design UI**

---

## 🚀 Screenshots

![1](https://github.com/user-attachments/assets/0b96e1f4-421a-4e72-8657-2a69917accef)


![2](https://github.com/user-attachments/assets/89c97412-bfbc-4f31-9fb1-13e69712855f)



---

## ⚙️ Setup Instructions

1. **Clone the Repository**

```bash
git clone https://github.com/your-username/event-share-app.git
cd event-share-app
```

2. **Open in Android Studio**

- File > Open > Select the project folder

3. **Configure Firebase**

- Add your `google-services.json` file to the `app` directory.
- Set up Firebase Realtime Database, Authentication, and Cloud Messaging.

4. **Add Razorpay Keys**

- Add your Razorpay API keys in `local.properties` or `strings.xml`.

5. **Build & Run**

- Run the app on an emulator or physical device.

---

## 📆 Dependencies

```groovy
// Firebase
implementation 'com.google.firebase:firebase-database:20.x.x'
implementation 'com.google.firebase:firebase-auth:21.x.x'
implementation 'com.google.firebase:firebase-messaging:23.x.x'

// Razorpay
implementation 'com.razorpay:checkout:1.x.x'

// AndroidX and Material
implementation 'androidx.appcompat:appcompat:1.x.x'
implementation 'com.google.android.material:material:1.x.x'
```

> 💡 *Replace versions with latest stable releases.*

---

## 🙌 Acknowledgements

- [Firebase](https://firebase.google.com/)
- [Razorpay](https://razorpay.com/docs/payment-gateway/android-integration/standard/)
- [Android Developers](https://developer.android.com)

