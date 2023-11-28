package com.example.savannahwonders.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DestinationModel(
    @Json(name = "Description")
    var description: String? = "",
    @Json(name = "Location")
    var location: String? = "",
    @Json(name = "Main_Image")
    var mainImage: String? = "",
    @Json(name = "Name")
    var name: String? = "",
    @Json(name = "Other_Images")
    var otherImages: String? = "",
    @Json(name = "Place ID")
    var placeID: String? = "",
    @Json(name = "Rating")
    var rating: Double? = 0.0,
    @Json(name = "Reviews")
    var reviews: Int? = 0,
    @Json(name = "Trip ID")
    var tripID: Int? = 0
)