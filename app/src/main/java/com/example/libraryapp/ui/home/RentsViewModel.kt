package com.example.libraryapp.ui.home

import androidx.lifecycle.*
import com.example.libraryapp.data.rent.Rent
import com.example.libraryapp.data.rent.RentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RentsViewModel @Inject constructor(
    private val repository: RentRepository
    ) : ViewModel() {
    val rents: LiveData<List<Rent>> = repository.rents.asLiveData()

    fun insert(rent: Rent) = viewModelScope.launch {
        repository.insert(rent)
    }

    fun editRent(rent: Rent) {
        rent.status = false
        rent.return_date = LocalDateTime.now()
        viewModelScope.launch {
            repository.edit(rent)
        }
    }
}

//class RentsViewModelFactory(
//    private val repository: RentRepository
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(RentsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return RentsViewModel(repository) as T
//        }
//        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
//    }
//}