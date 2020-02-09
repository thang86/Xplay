package thang86.github.io.xplay.network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import thang86.github.io.xplay.model.Response;

/**
 * Created by TranThang on 2/8/2020.
 */

public interface MovieApiService {

    @GET("movie/popular")
    Observable<Response> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/now_playing")
    Observable<Response> getNowPlayingMovies(@Query("api_key") String apiKey);
}
