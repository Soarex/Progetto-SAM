package it.al.blockbreakerworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import it.al.blockbreakerworld.game.getBitmapDescriptor
import it.al.blockbreakerworld.map.CustomInfoWindow
import it.al.blockbreakerworld.map.Level
import it.al.blockbreakerworld.map.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel: MainViewModel by viewModels()
    private val _mapView: MapView by lazy(LazyThreadSafetyMode.NONE) { mapView }
    private val _loadingBar: View by lazy(LazyThreadSafetyMode.NONE) { loadingBar }
    private val _errorBar: View by lazy(LazyThreadSafetyMode.NONE) { errorBar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _mapView.onCreate(savedInstanceState)
        _mapView.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        val map = p0!!
        map.setOnInfoWindowClickListener {
            val i = Intent(this, GameActivity::class.java)
            i.putExtra("url", (it.tag as Level).url)
            startActivity(i)
        }

        map.uiSettings.apply {
            isCompassEnabled = false
            isMapToolbarEnabled = false
            isZoomControlsEnabled = false
            isMyLocationButtonEnabled = false
        }

        viewModel.averageLocation.observe(this, Observer {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        it.latitude,
                        it.longitude
                    ), 5f
                )
            )
        })

        viewModel.levels.observe(this, Observer {
            _loadingBar.visibility = View.VISIBLE
            map.clear()
            for(i in 0 until it.size)
                map.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            it[i].lat,
                            it[i].lng
                        )
                    )
                ).apply {
                    tag = it[i]

                    setIcon(
                        getBitmapDescriptor(
                            R.drawable.icon_marker,
                            ResourcesCompat.getColor(resources, R.color.colorPrimary, null),
                            this@MainActivity
                        )
                    )
                }

            map.setInfoWindowAdapter(
                CustomInfoWindow(
                    layoutInflater
                )
            )
            _loadingBar.visibility = View.GONE
        })

        viewModel.downloadError.observe(this, Observer {
            _errorBar.visibility = if(it) View.VISIBLE else View.GONE
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        _mapView.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        _mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        _mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        _mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        _mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        _mapView.onLowMemory()
    }
}