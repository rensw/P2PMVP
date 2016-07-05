package com.android.mvpp2p.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ManagerListData implements Parcelable {

	private String id;
	private String name;
	private String rate;
	private String deadline;
	private String main_title;
	private String sub_title;
	private String sub_desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getMain_title() {
		return main_title;
	}

	public void setMain_title(String main_title) {
		this.main_title = main_title;
	}

	public String getSub_title() {
		return sub_title;
	}

	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}

	public String getSub_desc() {
		return sub_desc;
	}

	public void setSub_desc(String sub_desc) {
		this.sub_desc = sub_desc;
	}

	@Override
	public String toString() {
		return "ManagerListData [id=" + id + ", name=" + name + ", rate=" + rate + ", deadline=" + deadline + ", main_title=" + main_title + ", sub_title=" + sub_title + ", sub_desc=" + sub_desc + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.rate);
		dest.writeString(this.deadline);
		dest.writeString(this.main_title);
		dest.writeString(this.sub_title);
		dest.writeString(this.sub_desc);
	}

	public ManagerListData() {
	}

	protected ManagerListData(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.rate = in.readString();
		this.deadline = in.readString();
		this.main_title = in.readString();
		this.sub_title = in.readString();
		this.sub_desc = in.readString();
	}

	public static final Creator<ManagerListData> CREATOR = new Creator<ManagerListData>() {
		@Override
		public ManagerListData createFromParcel(Parcel source) {
			return new ManagerListData(source);
		}

		@Override
		public ManagerListData[] newArray(int size) {
			return new ManagerListData[size];
		}
	};
}
