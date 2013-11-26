package pl.com.agora.videosample;

import java.util.List;

import pl.com.agora.videosample.model.Movie;
import pl.com.agora.videosample.model.MovieList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.Transformer;
import com.google.gson.Gson;

public class MainActivity extends Activity {

	private ListView mListView;
	private AQuery aq;
	private ProgressBar mLoadingBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(android.R.id.list);
		mLoadingBar = (ProgressBar) findViewById(R.id.loadingBar);
		aq = new AQuery(this);
		asyncJson();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void asyncJson() {

		String url = "http://192.168.2.101:8080/video5/index.json";//"http://kuzkuz.pl/video5/index.json";
		GsonTransformer t = new GsonTransformer();
		aq.transformer(t).ajax(url, MovieList.class, new AjaxCallback<MovieList>() {

			@Override
			public void callback(String url, MovieList json, AjaxStatus status) {

				if (json != null) {

					// successful ajax call, show status code and json content
					Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();
					mListView.setAdapter(new MovieAdapter(MainActivity.this, json.getMovies()));
					mLoadingBar.setVisibility(View.GONE);
					mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                                intent.putExtra("movie", (Movie) mListView.getAdapter().getItem(position));
                                startActivity(intent);
                        }
                });
				} else {

					// ajax error, show error code
					Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private static class GsonTransformer implements Transformer {

		public <T> T transform(String url, Class<T> type, String encoding, byte[] data, AjaxStatus status) {
			Gson g = new Gson();
			return g.fromJson(new String(data), type);
		}
	}
	
	private static class MovieAdapter extends BaseAdapter {

		private List<Movie> movies;
		private Context context;

		public MovieAdapter(final Context context, final List<Movie> movies) {
			this.movies = movies;
			this.context = context;
		}
		
		@Override
		public int getCount() {
			
			return movies.size();
		}

		@Override
		public Object getItem(int position) {
			
			return movies.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.list_item, parent, false);
            } else {
                    view = convertView;
            }
            Movie currentMovie = movies.get(position);
            ((TextView)view.findViewById(R.id.title)).setText(currentMovie.getTitle());
            AQuery aq = new AQuery(view);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            aq.id(imageView).image(currentMovie.getImage());
			return view;
		}
		
	}

}
