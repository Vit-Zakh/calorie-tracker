package com.example.calorietracker

class DataSource {

    companion object {

        fun createDataSet(): ArrayList<RecyclerData> {
            val list = ArrayList<RecyclerData>()
            list.add(
                User(0, "Кошка Машка", "https://cataas.com/cat/cute", 5.4f, 0f)
            )
            list.add(TextLine)
            list.add(
                Meal(
                    1,
                    "Ginger Pork",
                    "https://picsum.photos/200/300",
                    700f,
                    200.99f
                )
            )
            list.add(
                Meal(
                    2,
                    "Teriyaki Meat Loaf with something else, and salt and sauce, just to have a really long name here",
                    "https://picsum.photos/200/300",
                    2100f,
                    100.72f
                )
            )
            list.add(
                Meal(
                    3,
                    "Shrimp Noodle",
                    "https://picsum.photos/200/300",
                    410f,
                    300.87f
                )
            )
            list.add(
                Meal(
                    4,
                    "Spicy Chicken",
                    "https://picsum.photos/200/300",
                    999f,
                    400.11f
                )
            )
            list.add(
                Meal(
                    5,
                    "Broccoli",
                    "https://picsum.photos/200/300",
                    0f,
                    500.23f
                )
            )
            list.add(
                Meal(
                    6,
                    "Sausage Stew",
                    "https://picsum.photos/200/300",
                    1f,
                    120.11f
                )
            )
            list.add(
                Meal(
                    7,
                    "Pizza",
                    "https://picsum.photos/200/300",
                    17f,
                    900.56f
                )
            )
            list.add(
                Meal(
                    8,
                    "Pasta",
                    "https://picsum.photos/200/300",
                    3140f,
                    700.9f
                )
            )
            return list
        }

        fun createUser(): User {
            return User(0, "Кошка Машка", "http://random.cat/view/1062", 5.4f, 0f)
        }
    }
}
