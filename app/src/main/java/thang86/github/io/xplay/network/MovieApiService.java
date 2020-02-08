package thang86.github.io.xplay.network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import thang86.github.io.xplay.model.Response;

public interface MovieApiService {

    @GET("movie/popular")
    Observable<Response> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);
}
