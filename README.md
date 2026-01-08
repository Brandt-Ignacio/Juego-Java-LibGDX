# ⚽ Haxball Clone - Multiplayer Game (Java & LibGDX)

Este proyecto es un videojuego multijugador 2D inspirado en Haxball, desarrollado íntegramente en **Java**. Utiliza una arquitectura **Cliente-Servidor** para gestionar la lógica de juego y la sincronización de físicas en tiempo real.

## 🚀 Arquitectura del Proyecto

El sistema está dividido en dos módulos independientes, permitiendo una separación clara entre la interfaz de usuario y la lógica autoritativa del servidor:

### 🎮 Cliente (`/Cliente`)
* **Framework:** LibGDX 1.12.0
* **Físicas:** Box2D para interpolación de movimiento y respuesta inmediata
* **Renderizado:** Gestión de cámaras, texturas y fuentes dinámicas con FreeType.
* **Backend:** LWJGL3 para ejecución de alto rendimiento en escritorio.

### 🖥️ Servidor (`/Servidor`)
* **Motor de Lógica:** Basado en LibGDX Core para compartir el motor de físicas con el cliente.
* **Físicas Autoritativas:** Utiliza **Box2D** en el lado del servidor para validar posiciones y colisiones, garantizando una partida justa para todos los jugadores.
* **Persistencia:** Integración con base de datos para gestión de usuarios y estadísticas (configuración mediante variables de entorno seguras).

## 📂 Estructura del Repositorio
```
├── Cliente/       # Código fuente del cliente, assets y configuración de Gradle.
├── Servidor/      # Lógica del servidor, manejo de sockets y físicas globales.
└── README.md      # Documentación principal del proyecto.
```

## 🛠️ Requisitos e Instalación
1. Clonar el repositorio
2. Lanzar el Servidor: Navega a la carpeta Servidor y ejecuta: ./gradlew desktop:run
3. Lanzar el Cliente: Navega a la carpeta Cliente y ejecuta uno por cliente (2 en total): ./gradlew desktop:run

## 🛠️ Tecnologías y Herramientas
* **Lenguaje:** Java 8+.
* **Framework:** LibGDX 1.12.0 (Core, Box2D, FreeType).
* **Gestor de dependencias:** Gradle.
* **Arquitectura:** Cliente-Servidor autoritativo.

Desarrollado por Brandt Ignacio