package com.app.weather.core

object MockResponses {
    val forecastResponse = """
        {
            "list": [
                {
                    "dt": 1661870400,
                    "main": {
                        "temp": 293.25,
                        "temp_min": 293.25,
                        "temp_max": 293.25,
                        "pressure": 1013,
                        "humidity": 88
                    },
                    "weather": [
                        {
                            "id": 500,
                            "main": "Rain",
                            "description": "light rain",
                            "icon": "10d"
                        }
                    ],
                    "dt_txt": "2024-11-25 15:00:00"
                }
            ],
            "city": {
                "name": "New York",
                "country": "US"
            }
        }
    """.trimIndent()
}
