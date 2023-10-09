package com.example.savannahwonders.data.temp

import androidx.compose.ui.graphics.painter.Painter
import com.example.savannahwonders.R

object TempData {
    val listOfDestinations: List<Destination> = listOf(
        Destination(
            image = R.drawable.elephants,
            name= "Maasai Mara",
            price= 1200.00
        ),
        Destination(
            image = R.drawable.elephants,
            name= "Tsavo East",
            price= 2200.00
        ),
        Destination(
            image = R.drawable.elephants,
            name= "Tsavo West",
            price= 3200.00
        ),
        Destination(
            image = R.drawable.elephants,
            name= "Nairobi National Park",
            price= 5200.00
        ),
        Destination(
            image = R.drawable.elephants,
            name= "Maasai Mara",
            price= 1200.00
        ),
        Destination(
            image = R.drawable.elephants,
            name= "Maasai Mara",
            price= 1200.00
        )
    )
}

data class Destination(
    val image: Int,
    val name: String,
    val price: Double
)