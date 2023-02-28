package il.reversedixit.repository.repoImage

import com.google.gson.Gson
import il.reversedixit.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject

interface RepoImage {

    fun getImage(body: JSONObject, callback: GetImageCallback)

    interface GetImageCallback {
        fun onSuccess(uri: String)
        fun onError(e: Exception)
    }


    class RepoImageImpl : RepoImage {
        private val client = OkHttpClient.Builder()
            .build()
        private val api = BuildConfig.API_KEY
        private val gson = Gson()


        companion object {
            private const val BASE_URL = "https://api.openai.com/v1/images/generations"
            private val jsonType = "application/json".toMediaType()

        }


        override fun getImage(body: JSONObject, callback: GetImageCallback) {
            val request: Request = Request.Builder()
                .url(BASE_URL)
                .post(body.toString().toRequestBody(jsonType)
                ).header(
                    "Authorization",
                    "Bearer $api"
                ).build()

            return client.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        callback.onError(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val json = JSONObject(response.body?.string())
                        if (!response.isSuccessful) {
                            throw RuntimeException("Something wrong on getImage response!")
                        } else {
                            val uri = json.getJSONArray("data")
                                .getJSONObject(0).getString("url")

                            callback.onSuccess(uri)
                        }
                    }
                })

        }
    }
}