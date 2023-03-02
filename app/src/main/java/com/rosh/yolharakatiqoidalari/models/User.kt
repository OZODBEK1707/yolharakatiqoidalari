package com.rosh.yolharakatiqoidalari.models

class User:java.io.Serializable {
    var id: Int? = null
    var name: String? = null
    var about: String? = null
    var whichType: Int? = null
    var likedState: Int? = null
    var photoPath: String? = null

    constructor(
        id: Int?,
        name: String?,
        about: String?,
        whichType: Int?,
        likedState: Int?,
        photoPath: String?
    ) {
        this.id = id
        this.name = name
        this.about = about
        this.whichType = whichType
        this.likedState = likedState
        this.photoPath = photoPath
    }

    constructor(
        name: String?,
        about: String?,
        whichType: Int?,
        likedState: Int?,
        photoPath: String?
    ) {
        this.name = name
        this.about = about
        this.whichType = whichType
        this.likedState = likedState
        this.photoPath = photoPath
    }

    override fun toString(): String {
        return "User(id=$id, name=$name, about=$about, whichType=$whichType, likedState=$likedState, photoPath=$photoPath)"
    }


}