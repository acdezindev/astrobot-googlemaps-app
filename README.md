# 🗺️ AstroBot - Gincana Interactiva por Andalucía

Aplicación Android que implementa una gincana interactiva con Google Maps, donde los usuarios deben recorrer diferentes puntos emblemáticos de Andalucía, responder preguntas y superar desafíos para completar la aventura de AstroBot.

> Desarrollada en Kotlin como tarea del grado de Programación Multimedia y Dispositivos Móviles.

---

## 📱 Capturas de pantalla

| |                                  |                           |                              |
|:------------------------:|:-------------------:|:-------:|:------------------:|
| ![Mapa](Images/mapa.png) | ![MapaZoom](Images/mapaZoom.png) | ![Permisos](Images/permisos.png) | ![Ubicacion](Images/UbicacionMapa.png) |

|                                           |                                                |                                  |                                      |
|:-----------------------------------------:|:----------------------------------------------:|:--------------------------------:|:------------------------------------:|
| ![Pregunta Mapa](Images/preguntaMapa.png) | ![PreguntaDialogo](Images/preguntaDialogo.png) | ![Correcta](Images/correcta.png) | ![Incorrecta](Images/Incorrecta.png) |

---

## ✨ Características principales

### 🗺️ Mapa interactivo
- **10 localizaciones** distribuidas por Andalucía
- **Marcadores personalizados** con iconos temáticos para cada monumento
- **Zoom y navegación** fluidos por el mapa
- **Diferentes tipos de mapa**: Normal, Satélite, Terreno e Híbrido

### 🎯 Gincana de preguntas
- **10 desafíos** asociados a cada ubicación
- **Cuadro de diálogo** con pregunta y campo de respuesta (estilo contraseña)
- **Validación de respuestas** en tiempo real
- **Sistema de progreso** que cuenta los aciertos
- **Cambio de icono** al completar cada desafío

### 📍 Ubicación del usuario
- **Switch** para mostrar/ocultar la ubicación actual
- **Punto azul** en el mapa con la posición del dispositivo
- **Permisos en tiempo de ejecución** para acceso a la ubicación

### 🎨 Personalización
- **Iconos personalizados** para cada marcador
- **Estilo temático** de AstroBot en los diálogos
- **Toast informativos** para cada acción del usuario

---

## 🛠️ Tecnologías utilizadas

- **Kotlin** - Lenguaje de programación
- **Android SDK** - Desarrollo de la app (API 24+)
- **Google Maps SDK** - Visualización de mapas y ubicación
- **Google Location Services** - Obtención de la ubicación del usuario
- **ViewBinding** - Acceso seguro a vistas
- **AlertDialog** - Diálogos personalizados para las preguntas
- **Material Design** - Componentes y estilos visuales
- **Gson** - Parsing de JSON para los datos de los lugares
- 
### 🎯 ¿Qué he aprendido?

- ✅ Integrar **Google Maps SDK** en una aplicación Android
- ✅ Gestionar **permisos de ubicación** en tiempo de ejecución
- ✅ Crear **marcadores personalizados** con iconos temáticos
- ✅ Implementar **diálogos interactivos** con `AlertDialog`
- ✅ Manejar **eventos de clic** en marcadores y ventanas de información
- ✅ Aplicar una **arquitectura por capas** (data/repository/ui)
- ✅ Usar **ViewBinding** para acceso seguro a vistas
- ✅ Configurar **API Keys de forma segura** con `local.properties`
- ✅ Controlar la **ubicación del usuario** con `FusedLocationProviderClient`
---

## 🔑 Configuración de Google Maps

Esta aplicación utiliza el **Google Maps SDK** y requiere una **API Key** para funcionar correctamente.

### Obtener una API Key
1. Ve a la [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Habilita la API de **Maps SDK for Android**
4. Ve a "Credenciales" → "Crear credenciales" → "Clave de API"
5. Copia tu clave (ejemplo: `AIzaSyC1...`)

### Configurar en el proyecto
1. Abre el archivo `local.properties` (en la raíz del proyecto)
2. Añade esta línea al final:


## 📊 Estado de la tarea de formación académica

![Estado](https://img.shields.io/badge/ESTADO-FINALIZADO-brightgreen)
![Versión](https://img.shields.io/badge/Versión-1.0.0-blue)
![Android](https://img.shields.io/badge/Android-API%2024%2B-green)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-purple)
![Google Maps](https://img.shields.io/badge/Google%20Maps-API-orange)