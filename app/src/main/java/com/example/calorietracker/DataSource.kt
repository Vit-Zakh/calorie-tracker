package com.example.calorietracker

class DataSource {

    companion object {

        fun createDataSet(): ArrayList<Meal> {
            val list = ArrayList<Meal>()
            list.add(
                Meal(
                    0,
                    "Ginger Pork",
                    "https://picsum.photos/200/300",
                    200f
                )
            )
            list.add(
                Meal(
                    2,
                    "Teriyaki Meat Loaf.",
                    "https://picsum.photos/200/300",
                    100f
                )
            )
            list.add(
                Meal(
                    3,
                    "Shrimp Noodle",
                    "https://picsum.photos/200/300",
                    300f
                )
            )
            list.add(
                Meal(
                    4,
                    "Spicy Chicken",
                    "https://picsum.photos/200/300",
                    400f
                )
            )
            list.add(
                Meal(
                    5,
                    "Broccoli",
                    "https://picsum.photos/200/300",
                    500f
                )
            )
            list.add(
                Meal(
                    6,
                    "Sausage Stew",
                    "https://picsum.photos/200/300",
                    120f
                )
            )
            list.add(
                Meal(
                    7,
                    "Pizza",
                    "https://picsum.photos/200/300",
                    900f
                )
            )
            list.add(
                Meal(
                    8,
                    "Pasta",
                    "https://picsum.photos/200/300",
                    700f
                )
            )
            return list
        }
    }
}
