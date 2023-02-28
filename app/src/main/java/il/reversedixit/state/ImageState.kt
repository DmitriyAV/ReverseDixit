package il.reversedixit.state

data class ImageState (
    val image: String? = " ",
    val loading: Boolean = false,
    val serverError: Boolean = false
)