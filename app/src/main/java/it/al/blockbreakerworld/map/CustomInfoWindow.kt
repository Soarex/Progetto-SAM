package it.al.blockbreakerworld.map

import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import it.al.blockbreakerworld.R
import kotlinx.android.synthetic.main.custom_info_window.view.*

class CustomInfoWindow(private val inflater: LayoutInflater): GoogleMap.InfoWindowAdapter{
    override fun getInfoWindow(p0: Marker?): View? {
        if(p0 == null) return null
        val level = p0.tag as Level
        val view = inflater.inflate(R.layout.custom_info_window, null)

        view.title.text = level.title
        view.description.text = level.description
        return view
    }

    override fun getInfoContents(p0: Marker?): View? {
        return null
    }
}