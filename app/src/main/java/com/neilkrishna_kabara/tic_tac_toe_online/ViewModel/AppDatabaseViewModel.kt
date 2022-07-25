package com.neilkrishna_kabara.tic_tac_toe_online.ViewModel

import androidx.lifecycle.*
import com.neilkrishna_kabara.tic_tac_toe_online.repository.AppDatabaseRepository
import com.neilkrishna_kabara.tic_tac_toe_online.room_database.InvitedPlayer
import com.neilkrishna_kabara.tic_tac_toe_online.room_database.PreviousPlayer
import kotlinx.coroutines.launch

class AppDatabaseViewModel(private val repository: AppDatabaseRepository): ViewModel() {

    val allPreviousPlayer: LiveData<List<PreviousPlayer>> = repository.allPreviousPlayers.asLiveData()
    val allInvitedPlayer: LiveData<List<InvitedPlayer>> = repository.allInvitedPlayers.asLiveData()

    fun insertPreviousPlayer(player: PreviousPlayer) = viewModelScope.launch {
        repository.insertPreviousPlayer(player)
    }

    fun insertInvitedPlayer(player: InvitedPlayer) = viewModelScope.launch {
        repository.insertInvitedPlayer(player)
    }
}

class AppDatabaseViewModelFactory(private val repository: AppDatabaseRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppDatabaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppDatabaseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}