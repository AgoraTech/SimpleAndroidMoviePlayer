package pl.com.agora.videosample;

import pl.com.agora.videosample.model.Movie;
import android.widget.TextView;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

public class MovieActivity extends Activity {

	private Movie mMovie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		Bundle bundle = getIntent().getExtras();
		if (bundle.containsKey("movie")) {
			mMovie = (Movie) bundle.get("movie");
			TextView title = (TextView) findViewById(R.id.movieTitle);
			title.setText(mMovie.getTitle());
		} else {
			finish();
			return;
		}
		//Use a media controller so that you can scroll the video contents
		//and also to pause, start the video.
		VideoView videoView = (VideoView) findViewById(R.id.videoView);
		MediaController mediaController = new MediaController(this); 
		mediaController.setAnchorView(videoView);
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(Uri.parse(mMovie.getMovieFile()));
		videoView.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie, menu);
		return true;
	}

}
