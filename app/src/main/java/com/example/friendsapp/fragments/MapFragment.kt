package com.example.friendsapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.friendsapp.R
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint

import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import android.preference.PreferenceManager

import org.osmdroid.config.Configuration


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val LATITUDE = "latitude"
private const val LONGITUDE = "longitude"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var latitude: Float? = null
    private var longitude: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getFloat(LATITUDE)
            longitude = it.getFloat(LONGITUDE)
        }
    }

    lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Configuration.getInstance().load(requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext()))

        val view = inflater.inflate(R.layout.fragment_map, container, false)

        mapView = view.findViewById<MapView>(R.id.map)
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)
        val mapController = mapView.controller
        mapController.setZoom(9.5)
        val startPoint = longitude?.toDouble()?.let {
                lon -> latitude?.toDouble()?.let {
                lat -> GeoPoint(lat, lon)
            }
        }
        mapController.setCenter(startPoint)

        return view
    }

    override fun onResume() {
        super.onResume()

        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()

        mapView.onPause()
    }

    companion object {

        fun newInstance(latitude: Float, longitude: Float) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putFloat(LATITUDE, latitude)
                    putFloat(LONGITUDE, longitude)
                }
            }
    }
}