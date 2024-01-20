package com.example.libraryapp.ui.dashboard

import androidx.lifecycle.*
import com.example.libraryapp.data.position.Position
import com.example.libraryapp.data.position.PositionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PositionsViewModel @Inject constructor(
    private val repository: PositionRepository
    ) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val allPositions: LiveData<List<Position>> = repository.allPositions.asLiveData()
    val text: LiveData<String> = _text

    fun insert(position: Position) = viewModelScope.launch { repository.insert(position) }
    fun delete(position: Position) = viewModelScope.launch { repository.delete(position) }

}

//class PositionViewModelFactory(
//    private val repository: PositionRepository
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(PositionsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return PositionsViewModel(repository) as T
//        }
//        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
//    }
//}