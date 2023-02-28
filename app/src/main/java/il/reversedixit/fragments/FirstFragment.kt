package il.reversedixit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import il.reversedixit.R
import il.reversedixit.databinding.FragmentFirstBinding
import il.reversedixit.viewmodels.imageviewmodel.ImageViewModel
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: ImageViewModel by viewModels(ownerProducer = ::requireParentFragment)


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.ok.setOnClickListener {
            val description = binding.hints.text.toString()
            val body = JSONObject(
                mapOf(
                    "prompt" to description,
                    "n" to 1,
                    "size" to "1024x1024"
                )
            )
            viewModel.getImage(body)
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment
            )
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}