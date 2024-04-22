package com.gitje.recipeat.models

import android.icu.util.MeasureUnit
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
class Recipe {
    @PrimaryKey
    var id: Int = 0

    @ColumnInfo(name = "Name")
    var name: String = ""

    @ColumnInfo(name = "Image")
    var imageUri: String = ""

    @Embedded
    @ColumnInfo(name = "Ingredients")
    var ingredients: List<Ingredient> = emptyList()

    @ColumnInfo(name = "Steps")
    var preparationSteps: List<Step> = emptyList()

    @ColumnInfo(name = "SourceURL")
    var sourceUrl: String = ""

    @ColumnInfo(name = "IsFavorite")
    var favorite: Boolean = false

    @ColumnInfo(name = "Category")
    var category: String = ""
}

class Ingredient {
    var name: String = ""
    var amount: Double = 0.0
    var unit: MeasureUnit = MeasureUnit.GRAM
}

class Step {
    var title: String = ""
    var description: String = ""
}