package thang86.github.io.xplay.network.utils;

import thang86.github.io.xplay.network.ApiClient;
import thang86.github.io.xplay.network.MovieApiService;

public class ApiUtils {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    public static MovieApiService getMoviePopularRespone() {
        return ApiClient.getApiClient(BASE_URL).create(MovieApiService.class);
    }

}
