package br.com.brunocarvalhs.compracerta.features.home.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group

@Entity(tableName = "groups")
data class GroupModel(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0,
    @ColumnInfo(name = "name") override val name: String
) : Group