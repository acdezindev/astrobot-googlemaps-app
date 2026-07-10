package ac.pmdm.geolocalizacion.data

/**
 * Clase de datos que representa un lugar de la gincana con su pregunta asociada.
 *
 * @property titulo Nombre del lugar o monumento que se muestra en el marcador
 * @property pregunta Enunciado de la pregunta que debe responder el usuario
 * @property respuesta Clave o contraseña correcta para superar el desafío
 *
 * @see ac.pmdm.geolocalizacion.repository.PreguntasGincana Clase que gestiona la lista de lugares y sus preguntas
 */
data class LugaresMapa(
    val titulo: String,
    val pregunta: String,
    val respuesta: String
)