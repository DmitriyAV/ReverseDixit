package il.reversedixit.viewmodels

import androidx.lifecycle.ViewModel
import il.reversedixit.RepositoryGame
import il.reversedixit.dto.Answer

class GameViewModel() : ViewModel() {

    private val repository = RepositoryGame.RepositoryGameImpl()

    fun putAnswer(answer: String) {

        val newAnswer = Answer(0, answer)
        repository.save(newAnswer)

    }

}

