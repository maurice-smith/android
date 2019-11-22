package com.kingmo.example.teamroster.viewmodels

import androidx.databinding.BaseObservable
import com.kingmo.example.teamroster.utils.EMPTY_STRING

class PlayerInfoFormViewModel: BaseObservable() {
    var firstName: String = EMPTY_STRING
    var lastName: String = EMPTY_STRING
    var position: String = EMPTY_STRING
    var jerseyNumber: String = EMPTY_STRING
    var playerBio: String = EMPTY_STRING
    var profileUrl: String = EMPTY_STRING
}