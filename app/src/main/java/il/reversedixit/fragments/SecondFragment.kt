package il.reversedixit.fragments

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import il.reversedixit.BuildConfig
import il.reversedixit.R
import il.reversedixit.databinding.FragmentSecondBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject
import java.net.URL

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val client = OkHttpClient()
    private lateinit var imageData: ByteArray
    private val api = BuildConfig.API_KEY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val description = requireArguments().get("description")!!
        val body = JSONObject(
            mapOf(
                "prompt" to description,
                "n" to 1,
                "size" to "1024x1024"
            )
        )
        val request =
            okhttp3.Request.Builder().url("https://api.openai.com/v1/images/generations").post(
                body.toString().toRequestBody("application/json".toMediaType())
            ).header(
                "Authorization",
                api
            ).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val json = JSONObject(response.body.string())
                val uri = json.getJSONArray("data").getJSONObject(0).getString("url")
                activity!!.runOnUiThread { Glide.with(binding.root).load(uri).into(binding.image) }

/*
                binding.image.setImageBitmap(
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            response.body.bytes()
                        )
                    )
                )
*/
            }

        })
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  binding.buttonSecond.setOnClickListener {
              findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
          }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}