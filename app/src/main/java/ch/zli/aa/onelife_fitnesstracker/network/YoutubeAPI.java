package ch.zli.aa.onelife_fitnesstracker.network;
/**
 * Hilfsmittel: https://www.youtube.com/watch?v=W4hTJybfU7s
 * https://www.googleapis.com/youtube/v3/
 * **/
import ch.zli.aa.onelife_fitnesstracker.models_tutorials.JSON_TutorialResponseModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class YoutubeAPI {

    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static final String search = "search?";
    public static final String part = "part=snippet&";
    public static final String max = "maxResults=40&";
    public static final String  order = "order=relevance&";


    public static final String StretchingAfter_query = "q=Stretching%20After%20Workout&";
    public static final String StretchingBefore_query = "q=Stretching%20Before%20Workout&";
    public static final String MeditationAfter_query = "q=Meditation$20After%20Workout&";

    public static final String Yoga_query = "q=Fitness%20Workout&";
    public static final String Pilates_query = "q=Pilates%20Workout&";

    public static final String Workout_query = "q=Workout&";


    public static final String type = "type=video&";
    public static final String API_KEY = "key=";

    public interface VideoAPI {
        @GET
        Call<JSON_TutorialResponseModel> getYT(@Url String url);
    }

    private static VideoAPI videoAPI = null;
    public static VideoAPI getVideoAPI() {
        if(videoAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            videoAPI = retrofit.create(VideoAPI.class);
        }
        return videoAPI;
    }
}
