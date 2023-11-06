package com.androdu.foody.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androdu.foody.models.Recipe
import com.androdu.foody.utils.Constants.FAVORITES_TABLE

@Entity(tableName = FAVORITES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var recipe: Recipe
)
