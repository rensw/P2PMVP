package com.android.mvpp2p.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsConsultData implements Parcelable {
	private String aid;
	private String title;
	private long dateline;
	private String pic;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getDateline() {
		return dateline;
	}

	public void setDateline(long dateline) {
		this.dateline = dateline;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return "NewsConsultData [aid=" + aid + ", title=" + title + ", dateline=" + dateline + ", pic=" + pic + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.aid);
		dest.writeString(this.title);
		dest.writeLong(this.dateline);
		dest.writeString(this.pic);
	}

	public NewsConsultData() {
	}

	protected NewsConsultData(Parcel in) {
		this.aid = in.readString();
		this.title = in.readString();
		this.dateline = in.readLong();
		this.pic = in.readString();
	}

	public static final Creator<NewsConsultData> CREATOR = new Creator<NewsConsultData>() {
		@Override
		public NewsConsultData createFromParcel(Parcel source) {
			return new NewsConsultData(source);
		}

		@Override
		public NewsConsultData[] newArray(int size) {
			return new NewsConsultData[size];
		}
	};
}
