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
                    "https://images.unsplash.com/photo-1601748361140-03605c34e843?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                    700f,
                    200.99f
                )
            )
            list.add(
                Meal(
                    2,
                    "Teriyaki Meat Loaf with something else, and salt and sauce, just to have a really long name here",
                    "https://images.unsplash.com/photo-1548807371-30dc1bbe6cb5?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                    2100f,
                    100.72f
                )
            )
            list.add(
                Meal(
                    3,
                    "Shrimp Noodle",
                    "https://images.unsplash.com/photo-1528344476859-60b256f33f6f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=680&q=80",
                    410f,
                    300.87f
                )
            )
            list.add(
                Meal(
                    4,
                    "Spicy Chicken",
                    "https://images.unsplash.com/photo-1491739481003-2991327b66e2?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1354&q=80",
                    999f,
                    400.11f
                )
            )
            list.add(
                Meal(
                    5,
                    "Broccoli",
                    "https://images.unsplash.com/photo-1611095758464-6ff38fe3ca65?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                    0f,
                    500.23f
                )
            )
            list.add(
                Meal(
                    6,
                    "Sausage Stew",
                    "https://images.unsplash.com/photo-1547714695-bed5d1fef848?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=634&q=80",
                    1f,
                    120.11f
                )
            )
            list.add(
                Meal(
                    7,
                    "Pizza",
                    "https://images.unsplash.com/photo-1611068120738-e3801fcaa00a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                    17f,
                    900.56f
                )
            )
            list.add(
                Meal(
                    8,
                    "Pasta",
                    "https://images.unsplash.com/photo-1608634070674-2db08b533d3a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80",
                    3140f,
                    700.9f
                )
            )
            return list
        }
    }
}