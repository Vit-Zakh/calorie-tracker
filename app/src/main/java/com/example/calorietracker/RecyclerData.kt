package com.example.calorietracker

import android.os.Parcel
import android.os.Parcelable

sealed class RecyclerData {

    data class Meal(
        val id: Int,
        val mealName: String,
        val imageUrl: String,
        val mealWeight: Float = 0f,
        val mealCalories: Float,
        val protein: Float? = null,
        val fat: Float? = null,
        val carbs: Float? = null
    ) : RecyclerData()

    data class User(
        val id: Int,
        val userName: String?,
        val userImage: String?,
        var userWeight: Float,
        var userIntake: Float,
        var plannedIntake: Float
    ) : RecyclerData(), Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readValue(Float::class.java.classLoader) as Float
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(userName)
            parcel.writeString(userImage)
            parcel.writeFloat(userWeight)
            parcel.writeFloat(userIntake)
            parcel.writeValue(plannedIntake)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<User> {
            override fun createFromParcel(parcel: Parcel): User {
                return User(parcel)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class Food(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val calories: Float,
        val protein: Float? = null,
        val fat: Float? = null,
        val carbs: Float? = null
    ) : RecyclerData()

    object TextLine : RecyclerData()
}
