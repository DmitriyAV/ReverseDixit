package il.reversedixit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import il.reversedixit.dto.Answer


interface RepositoryGame {

    val newId: Long
    val data: LiveData<List<Answer>>
    fun save(answer: Answer)


    class RepositoryGameImpl() : RepositoryGame {

        override lateinit var data: MutableLiveData<List<Answer>>
        override var newId = 0L

        init {
            val answer = listOf(Answer(
                newId,
                " "
            ))
            data = MutableLiveData(answer)
        }

        private val answers get() = data.value

        override fun save(answer: Answer) {
            data.value = answers?.plus(answer.copy(
                newId++
            ))
        }

    }
}