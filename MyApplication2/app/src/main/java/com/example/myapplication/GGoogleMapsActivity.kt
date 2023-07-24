package com.example.myapplication

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class GGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermiso = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION

        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                nombrePermiso
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos=true
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    nombrePermiso, nombrePermisoCoarse
                ),
                1
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
        moverQuicentro()
        anadirPolilinea()
        anadirPoligono()
    }
    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{googleMap->
        with(googleMap){
            mapa = googleMap
            establecerConfiguracionMapa()
            }
        }
    }
    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled=true
        }
    }
    fun anadirMarcador(latLng: LatLng, title:String): Marker {
        return mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )!!
    }
    fun moverCamaraConZoom(latLng: LatLng, zoom:Float = 10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng,zoom)
        )
    }
    fun moverQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(
            -0.175567084902771092, -78.48014901143776
        )
        val titulo = "Quicentro"
        val markQuicentro = anadirMarcador(quicentro, titulo)
        markQuicentro.tag = titulo
        moverCamaraConZoom(quicentro, zoom)
    }
    fun anadirPolilinea(){
        with(mapa){
            val poliLineaUno = mapa
                .addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng(-0.1759187040647396,-78.48506472421384),
                            LatLng(-0.17632468492901104,-78.48265589308046),
                            LatLng(-0.17746143130181483, -78.4770533307815)
                        )
                )
            poliLineaUno.tag = "Linea-1"
        }
    }
    fun anadirPoligono(){
        with(mapa){
            val poligonoUno = mapa.addPolygon(
                PolygonOptions()
                    .clickable(true)
                    .add(
                        LatLng(-0.1771546902239471, -78.48344981495214),
                        LatLng(-0.1796891486125768, -78.48269198043828),
                        LatLng(-0.17710958124147777, -78.48142892291516)
                    )
            )
            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "poligono-2"
        }
    }
}