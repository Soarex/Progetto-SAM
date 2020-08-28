package it.al.blockbreakerworld.map

data class Level (
    var title: String = "",
    var description: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var url: String = ""
)