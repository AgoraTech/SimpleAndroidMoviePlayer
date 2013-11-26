package pl.com.agora.videosample.model;

import java.io.Serializable;
import java.util.Arrays;

public class Movie implements Serializable {

	private String title;
	private String image;
	private int ageRestriction;
	private String[] genres;
	private long duration;
	private String movieFile;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getAgeRestriction() {
		return ageRestriction;
	}

	public void setAgeRestriction(int ageRestriction) {
		this.ageRestriction = ageRestriction;
	}

	public String[] getGenres() {
		return genres;
	}

	public void setGenres(String[] genres) {
		this.genres = genres;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getMovieFile() {
		return movieFile;
	}

	public void setMovieFile(String movieFile) {
		this.movieFile = movieFile;
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", image=" + image + ", ageRestriction=" + ageRestriction + ", genres=" + Arrays.toString(genres) + ", duration=" + duration + ", movieFile=" + movieFile + "]";
	}

}
