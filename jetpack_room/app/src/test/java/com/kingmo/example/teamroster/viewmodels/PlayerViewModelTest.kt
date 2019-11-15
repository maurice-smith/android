package com.kingmo.example.teamroster.viewmodels

import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.kingmo.example.teamroster.R
import com.kingmo.example.teamroster.database.Player
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class PlayerViewModelTest {
    private lateinit var playerViewModel: PlayerViewModel

    @Before
    fun setUp() {
        playerViewModel = PlayerViewModel(Player(123, "Mike", "Jones", null, "RF", null, null))
    }

    @Test
    fun shouldReturnViewModelPropertiesNull() {
        assertNull(playerViewModel.getPlayerBio())
        assertNull(playerViewModel.getPlayerPhotoUrl())
        assertNull(playerViewModel.getJerseyNumber())
    }

    @Test
    fun shouldReturnViewModelVisibilitiesGone() {
        assertVisibilitiesGone(playerViewModel.getJerseyNumberVisibility())
        assertVisibilitiesGone(playerViewModel.getBioVisibility())
        assertVisibilitiesGone(playerViewModel.getPlayerPhotoVisibility())
    }

    @Test
    fun shouldReturnViewModelVisibilitiesVisible() {
        playerViewModel = PlayerViewModel(Player(123, "Mike", "Jones", "15", "RF", "/img.png", "Aye"))

        assertVisibilitiesVisible(playerViewModel.getJerseyNumberVisibility())
        assertVisibilitiesVisible(playerViewModel.getBioVisibility())
        assertVisibilitiesVisible(playerViewModel.getPlayerPhotoVisibility())
    }

    @Test
    fun shouldReturnAllValues() {
        playerViewModel = PlayerViewModel(Player(123, "Paul", "Wall", "15", "LF", "/img.png", "Bio Text"))

        assertEquals("Paul", playerViewModel.getFirstName())
        assertEquals("Wall", playerViewModel.getLastName())
        assertEquals("15", playerViewModel.getJerseyNumber())
        assertEquals("LF", playerViewModel.getRosterPosition())
        assertEquals("/img.png", playerViewModel.getPlayerPhotoUrl())
        assertEquals("Bio Text", playerViewModel.getPlayerBio())
    }

    @Test
    fun shouldReturnCorrectLayoutResId() = assertEquals(R.layout.player_item_view, playerViewModel.getLayoutResId())

    @Test
    fun shouldReturnCorrectBindingVariable() = assertEquals(BR.playerViewModel, playerViewModel.getBindingVariable())

    private fun assertVisibilitiesGone(visibilityInt: Int) = assertEquals(View.GONE, visibilityInt)
    private fun assertVisibilitiesVisible(visibilityInt: Int) = assertEquals(View.VISIBLE, visibilityInt)
}