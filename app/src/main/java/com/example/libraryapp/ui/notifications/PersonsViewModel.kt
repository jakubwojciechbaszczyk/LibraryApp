package com.example.libraryapp.ui.notifications

import androidx.lifecycle.*
import com.example.libraryapp.data.*
import com.example.libraryapp.data.person.Person
import com.example.libraryapp.data.person.PersonRepository
import com.example.libraryapp.data.position.Position
import com.example.libraryapp.data.position.PositionRepository
import com.example.libraryapp.data.rent.Rent
import com.example.libraryapp.data.rent.RentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val positionRepository: PositionRepository,
    private val rentRepository: RentRepository,
) : ViewModel() {


    val allPersons: LiveData<List<Person>> = personRepository.allPerson().asLiveData()
    var person: Person? = null
    var position: Position? = null
    val positions: LiveData<List<Position>> = positionRepository.allPositions.asLiveData()
    val freePositions: LiveData<List<Position>> = positionRepository.allFreePositions.asLiveData()

//    fun insert(person: Person) = viewModelScope.launch {
//        personRepository.insert(person)
//    }

    fun rentPosition(position: Position) {
        this.position = position
        if (this.position != null && person != null) {
            val rentTime = LocalDateTime.now()
            val rent = Rent(position, person!!, rentTime, rentTime.plusMonths(3), status = true)
            viewModelScope.launch {
                rentRepository.insert(rent)
            }
        }
    }

    fun editPosition(position: Position) {
        position.isRented = true
        viewModelScope.launch { positionRepository.edit(position) }
    }
}

//class PersonViewModelFactory(
//    private val personRepository: PersonRepository,
//    private val positionRepository: PositionRepository,
//    private val rentRepository: RentRepository
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(PersonsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return PersonsViewModel(personRepository, positionRepository, rentRepository) as T
//        }
//        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
//    }
//}