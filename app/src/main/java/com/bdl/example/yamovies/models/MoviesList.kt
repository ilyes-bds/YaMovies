
import com.bdl.example.yamovies.models.Movie
import com.google.gson.annotations.SerializedName

data class MoviesList(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
