package il.reversedixit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import il.reversedixit.R
import il.reversedixit.databinding.FragmentAnswersBinding
import il.reversedixit.viewmodels.GameViewModel

class AnswersFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAnswersBinding.inflate(inflater, container, false)


        with(binding){
            next.setOnClickListener{
                val answer = answers.text.toString()
                viewModel.putAnswer(answer)
            }
        }
        return binding.root
    }
}