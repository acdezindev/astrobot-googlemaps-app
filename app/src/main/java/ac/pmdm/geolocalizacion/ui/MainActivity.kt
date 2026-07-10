package ac.pmdm.geolocalizacion.ui

import ac.pmdm.geolocalizacion.repository.PreguntasGincana
import ac.pmdm.geolocalizacion.R
import ac.pmdm.geolocalizacion.databinding.ActivityMainBinding
import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Actividad principal que implementa una gincana interactiva con Google Maps.
 * Los usuarios deben visitar diferentes ubicaciones de Andalucía y responder preguntas
 * para completar el recorrido de AstroBot.
 *
 * @see com.google.android.gms.maps.OnMapReadyCallback Interfaz que notifica cuando el mapa está listo
 */
class MainActivity : AppCompatActivity(), OnMapReadyCallback { // esto es como una implementacion.

    // ViewBinding para acceso eficiente a las vistas del layout
     private lateinit var  binding: ActivityMainBinding

    // Instancia principal del mapa de Google
    private lateinit var mMap: GoogleMap

    // Cliente para obtener la ubicación en tiempo real del dispositivo
    private lateinit var fusedLocationClient: FusedLocationProviderClient // 2

    //Contador de respuestas correctas para controlar el progreso de la gincana
    private  var totalRespuestas=0;


    // ============================
    // CICLO DE VIDA DE LA ACTIVIDAD
    // ============================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicialización del ViewBinding para trabajar con el layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // ==========================
        // Inizializacion del Mapa
        // ==========================

        // Configuración del mapa: se obtiene el fragmento y se prepara para uso asíncrono
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // ==========================
        // Inizializacion la Ubicacion
        // ==========================
        //Obtener la ubicacion. Inicialización del servicio de ubicación de Google Play Services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }


    // ============================
    // IMPLEMENTACIÓN DEL MAPA
    // ============================

    /**
     * Callback invocado cuando el mapa está completamente cargado y listo.
     * Aquí se configura toda la lógica de la gincana.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        // Configuración visual del mapa
             mMap.mapType = GoogleMap.MAP_TYPE_NORMAL      // Normal
            // mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE  // Satelite
            // mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN    // Terreno
            // mMap.mapType = GoogleMap.MAP_TYPE_HYBRID     // Hibrido


        //Funcion Carga de todos los puntos de interés de la gincana
        localizaciones()




        // ============================
        // CONTROL DE UBICACIÓN DEL USUARIO
        // ============================

        /**
         * Switch que permite al usuario mostrar u ocultar su ubicación actual.
         * Solicita permisos en tiempo de ejecución si es necesario.
         */

        binding.switchIdioma.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Mostrar ubicacion actual  (Activar ubicación: comprobar permisos)
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        1)
                } else {
                    // Ya hay permisos , mostramos la ubicacion la funcion shoMyLocationOnMap()
                    showMyLocationOnMap()
                    Toast.makeText(this, "AstroBot acaba de Aterrizar!!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Ocultar ubicacion  ( Desactivar ubicación: ocultar el punto azul)
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(false)
                }
                Toast.makeText(this, "Ubicacion oculta", Toast.LENGTH_SHORT).show()
            }
        }


        // ============================
        // INTERACCIÓN CON MARCADORES
        // ============================

        /**
         * Listener que maneja los clics en las ventanas de información de los marcadores.
         * Cada marcador representa un punto de la gincana con su propia pregunta.
         */
            mMap.setOnInfoWindowClickListener { vaderMarcador ->

                // Verificar si el desafío ya fue completado anteriormente
                if (vaderMarcador.tag == "completado") {
                    Toast.makeText(this, "Ya completaste este desafío", Toast.LENGTH_SHORT).show()

                } else { // inicio else tag completado

                    // Obtener la pregunta y respuesta asociadas al marcador
                    val preguntasGincana = PreguntasGincana()
                    var titulo = vaderMarcador.title.toString()
                    val pregunta = preguntasGincana.getPregunta(titulo)
                    val respuestaCorrecta = preguntasGincana.getRespuesta(titulo)
                    val escribeRespuesta = EditText(this)
                    escribeRespuesta.hint = "Escribe tu respuesta (Contraseña)"
                    escribeRespuesta.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                    // Construir el diálogo con la pregunta
                    val dialogo = AlertDialog.Builder(this)
                    dialogo.setTitle(vaderMarcador.title)
                    dialogo.setMessage("PREGUNTA:\n\n$pregunta\n\nTU RESPUESTA:")
                    dialogo.setView(escribeRespuesta)

                    dialogo.setPositiveButton("Finalizar Actividad") { dialog, _ ->
                        if (totalRespuestas < 10) {
                            // Respuesta correcta: avanzar en el juego
                            val respuestaUsuario = escribeRespuesta.text.toString()
                            if (respuestaUsuario.equals(respuestaCorrecta)) {
                                totalRespuestas++
                                vaderMarcador.tag = "completado"
                                vaderMarcador.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.astro))
                                Toast.makeText(
                                    this,
                                    "¡RESPUESTA CORRECTA!, ve a la siguiente Ubicacion!!",
                                    Toast.LENGTH_LONG
                                ).show()
                                dialog.dismiss() //Cerrar y eliminar dialogo

                            // Respuesta incorrecta
                            } else {

                                Toast.makeText(
                                    this,
                                    "¡RESPUESTA INCORRECTA!!!, vuelve a intentarlo!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            } else { // Completar desafios ( Gana el juego )
                            Toast.makeText(
                                this,
                                "ENHORABUENA ASTROBOT!!! YA CONOCES ALGUNOS DE LOS LUGARES MAS EMBLEMATICOS DE ANDALUCIA",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }

                    // Boton para cancelar (opcional)
                    dialogo.setNegativeButton("Cancelar") { dialog, _ ->
                        dialog.dismiss()
                    }



                    dialogo.show()

                }


            }

    }


    /**
     * Activa la visualización de la ubicación actual en el mapa.
     * Mueve la cámara a la posición del usuario con un zoom adecuado.
     */
    // Activar localizador posicion ( punto azul )
    private fun showMyLocationOnMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true)
            fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, { location ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.getLatitude(), location.getLongitude())

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 7f))
                        //     Zoom 1  = Mundo muy alejado
                        //     Zoom 5  = Continentes/regiones grandes
                        //     Zoom 10 = Ciudad o zona ampliada
                        //     Zoom 15 = Calles y barrios (RECOMENDADO)
                        //     Zoom 18 = Detalle de calle (coches, edificios)
                        //     Zoom 21 = Máximo zoom (edificios o interiores)
                    }
                })
        }
    }


    /**
     * Maneja la respuesta del usuario a la solicitud de permisos.
     * Si concede el permiso, activa la ubicación automáticamente.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                   grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // ***********************************
        // Respuesta a la peticion de permisos
        // ***********************************

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                    showMyLocationOnMap()
                }
            }
        }
    }


    // ============================
    // PUNTOS DE INTERÉS DE LA GINCANA
    // ============================

    /**
     * Define los 10 puntos de interés de la gincana distribuidos por Andalucía.
     * Cada ubicación incluye un marcador con título, descripción e icono temático.
     */

    private fun localizaciones () {

        // Ubicación Sevilla - La Giralda
            val giralda= LatLng(37.3858, -5.9931) // Ajusta la longitud
            mMap.addMarker(
                MarkerOptions()
                    .position(giralda)
                    .title("La Giralda de Sevilla")
                    .snippet("Catedral gotica y antigua torre almohade")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.giralda))
            )

        // Ubicación MEZQUITA DE CORDOBA (Córdoba)
            val mezquita = LatLng(37.8789, -4.7797)
            mMap.addMarker(
                MarkerOptions()
                    .position(mezquita)
                    .title("Mezquita de Cordoba")
                    .snippet("Monumento unico del arte omeya y cristiano")
                 .icon(BitmapDescriptorFactory.fromResource(R.drawable.mezquita))
            )
        // Ubicación LA ALHAMBRA (Granada)
            val alhambra = LatLng(37.1760, -3.5881)
            mMap.addMarker(
                MarkerOptions()
                    .position(alhambra)
                    .title("La Alhambra de Granada")
                    .snippet("Ciudad palatina nazari y Jardines del Generalife")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.leones1))
            )

        // Ubicación ALCAZABA DE ALMERIA (Almeria)
            val alcazabaAlmeria = LatLng(36.841369, -2.473919)
            mMap.addMarker(
                MarkerOptions()
                    .position(alcazabaAlmeria)
                    .title("Alcazaba de Almeria")
                    .snippet("Fortaleza defensiva con vistas al Mediterraneo")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.alcazaba))
            )

        // Ubicación CATEDRAL DE CADIZ (Cadiz)
            val catedralCadiz = LatLng(36.5289, -6.2950)
            mMap.addMarker(
                MarkerOptions()
                    .position(catedralCadiz)
                    .title("Catedral de Cadiz")
                    .snippet("La Catedral de la Santa Cruz sobre el Mar")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.catedralc))
            )

        // Ubicación TEATRO ROMANO (Malaga)
            val teatroRomano = LatLng(36.7211, -4.4167)
            mMap.addMarker(
                MarkerOptions()
                    .position(teatroRomano)
                    .title("Teatro Romano de Malaga")
                    .snippet("Restos arqueologicos de la Malaca romana")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.teatro1))
            )

        // Ubicación CATEDRAL DE JAEN (Jaen)
            val catedralJaen = LatLng(37.7650, -3.7897)
            mMap.addMarker(
                MarkerOptions()
                    .position(catedralJaen)
                    .title("Catedral de Jaen")
                    .snippet("Obra maestra del Renacimiento español")
                     .icon(BitmapDescriptorFactory.fromResource(R.drawable.catedralj))
            )

        // Ubicación MUELLE DE LAS CARABELAS (Huelva)
            val carabelas = LatLng(37.2086, -6.9269)
            mMap.addMarker(
                MarkerOptions()
                    .position(carabelas)
                    .title("Muelle de las Carabelas en Huelva")
                    .snippet("Replicas de los barcos del Descubrimiento")
                     .icon(BitmapDescriptorFactory.fromResource(R.drawable.carabela1))
            )

        // Ubicación IES AGUADULCE (Almeria)
            val aguadulce = LatLng(36.8093953, -2.5829074,)
            mMap.addMarker(
                MarkerOptions()
                    .position(aguadulce)
                    .title("IES Aguadulce")
                    .snippet("Lindsay nos enseño KOTLIN! y sobrevivio a nuestro Codigo!")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.aguadulce1))
            )

        // Ubicación CASTILLO SANTA CATALINA (Jaen - Ubeda)
            val ubedaSalvador = LatLng(38.0078, -3.3669)
            mMap.addMarker(
                MarkerOptions()
                    .position(ubedaSalvador)
                    .title("Castillo de Santa Catalina en Jaen")
                    .snippet("Construccion defensiva cristiano-medieval")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.castillo))
            )

    }



}// fin main