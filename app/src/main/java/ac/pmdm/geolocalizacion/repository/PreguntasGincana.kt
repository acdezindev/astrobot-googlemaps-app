package ac.pmdm.geolocalizacion.repository

/**
 * Clase que gestiona las preguntas y respuestas de la gincana de AstroBot.
 * Cada lugar turístico de Andalucía tiene asociada una pregunta y su respuesta correcta.
 *
 * Actualmente utiliza una implementación en duro (hardcoded) para cada ubicación,
 * pero está preparada para ser extendida con una carga desde JSON o base de datos.
 */
class PreguntasGincana () {
    /**
     * Obtiene la pregunta asociada a un lugar según su título.
     *
     * @param titulo Título del marcador del lugar (ej: "La Giralda de Sevilla")
     * @return La pregunta correspondiente al lugar, o un mensaje por defecto si no se encuentra
     */
    fun getPregunta(titulo: String): String {
        if (titulo == "La Giralda de Sevilla") {
            return "¿Cual es la altura de la Giralda?"
        }
        if (titulo == "Mezquita de Cordoba") {
            return "¿Cuantas columnas tiene el bosque (interior) de la mezquita de cordoba?"
        }
        if (titulo == "La Alhambra de Granada") {
            return "¿Cuantos leones hay en el Patio de los Leones?"
        }
        if (titulo == "Alcazaba de Almeria") {
            return "¿Cuantas cañones hay en la torre de la Polvora?"
        }
        if (titulo == "Catedral de Cadiz") {
            return "¿Cuantas campanas tiene la catedral?"
        }
        if (titulo == "Teatro Romano de Malaga") {
            return "¿En que siglo se construye el Teatro Romano?"
        }
        if (titulo == "Catedral de Jaen") {
            return "¿Cuántas capillas tiene la Catedral de Jaen?"
        }
        if (titulo == "Muelle de las Carabelas en Huelva") {
            return "¿Cuantas carabelas hay en el Muelle?"
        }
        if (titulo == "IES Aguadulce") {
            return "¿En que año se fundo el IES Aguadulce?"
        }
        if (titulo == "Castillo de Santa Catalina en Jaen") {
            return "¿Cuantas torres tiene el Castillo?"
        }
        return "Completa la actividad en este lugar"
    }

    /**
     * Obtiene la respuesta correcta asociada a un lugar según su título.
     *
     * @param titulo Título del marcador del lugar (ej: "La Giralda de Sevilla")
     * @return La respuesta correcta como String, o cadena vacía si no se encuentra
     */
    fun getRespuesta(titulo: String): String {
        if (titulo == "La Giralda de Sevilla") {
            return "104"
        }
        if (titulo == "Mezquita de Cordoba") {
            return "856"
        }
        if (titulo == "La Alhambra de Granada") {
            return "12"
        }
        if (titulo == "Alcazaba de Almeria") {
            return "2"
        }
        if (titulo == "Catedral de Cadiz") {
            return "10"
        }
        if (titulo == "Teatro Romano de Malaga") {
            return "1"
        }
        if (titulo == "Catedral de Jaen") {
            return "25"
        }
        if (titulo == "Muelle de las Carabelas en Huelva") {
            return "3"
        }
        if (titulo == "IES Aguadulce") {
            return "1970"
        }
        if (titulo == "Castillo de Santa Catalina en Jaen") {
            return "6"
        }
        return ""
    }
}