# TPCine (Spring Boot)

Proyecto con capas:
- Controller
- Service
- Repository
- Model (Entidades)

Entidades:
- Espectador
- Pelicula
- Entrada (relaciona Espectador ↔ Pelicula)

## Requisitos
- Java 17
- Maven
- MySQL (o cambiá el driver y la URL)

## Configuración
Editá `src/main/resources/application.properties`:
- spring.datasource.url
- spring.datasource.username
- spring.datasource.password

## Correr
```bash
mvn spring-boot:run
```

La API queda por default en:
- http://localhost:8080

## Endpoints
- /api/espectadores (GET, POST)
- /api/espectadores/{id} (GET, PUT, DELETE)
- /api/peliculas (GET, POST)
- /api/peliculas/{id} (GET, PUT, DELETE)
- /api/entradas (GET, POST)
- /api/entradas/{id} (GET, DELETE)
- /api/entradas/por-espectador/{espectadorId} (GET)
- /api/entradas/por-pelicula/{peliculaId} (GET)

## Ejemplos JSON
Crear Espectador (POST /api/espectadores)
```json
{ "nombre": "Juan Perez", "email": "juan@test.com" }
```

Crear Pelicula (POST /api/peliculas)
```json
{ "titulo": "Matrix", "genero": "Sci-Fi", "duracionMin": 136 }
```

Crear Entrada (POST /api/entradas)
```json
{ "espectadorId": 1, "peliculaId": 1, "asiento": "F12" }
```
