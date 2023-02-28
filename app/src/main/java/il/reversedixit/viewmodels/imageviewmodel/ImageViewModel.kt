package il.reversedixit.viewmodels.imageviewmodel

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import il.reversedixit.repository.repoImage.RepoImage
import il.reversedixit.state.ImageState
import il.reversedixit.util.SingleLiveEvent
import org.json.JSONObject
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.concurrent.thread

class ImageViewModel: ViewModel() {

    private val empty: ImageState = ImageState("", false, false)
    private val repository = RepoImage.RepoImageImpl()
    private val _data = MutableLiveData(ImageState())
    val data: LiveData<ImageState>
    get() = _data
    /*private val newImage = MutableLiveData(empty)
    private val _imageGenereted = SingleLiveEvent<Unit>()
    val imageGenereted: LiveData<Unit>
    get() = _imageGenereted*/


/*    private val mutableSelectedItem = MutableLiveData<ClipData.Item>()
    val selectedItem: LiveData<ClipData.Item> get() = mutableSelectedItem

    fun selectItem(item: ClipData.Item) {
        mutableSelectedItem.value = item
    }*/


    fun getImage(body: JSONObject){
        data.value.let {
            if (it != null) {
                repository.getImage(body, object : RepoImage.GetImageCallback{
                    override fun onSuccess(uri: String) {
                        _data.postValue(ImageState(image = uri))
                       // _data.value = empty
                    }

                    override fun onError(e: Exception) {
                        _data.postValue(ImageState(serverError = true))
                    }

                })
            } else throw RuntimeException("Something went wrong on viewmodel fun getImage")
        }
    }

}
