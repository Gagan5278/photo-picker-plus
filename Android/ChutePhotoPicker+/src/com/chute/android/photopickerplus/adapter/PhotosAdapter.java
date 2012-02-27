package com.chute.android.photopickerplus.adapter;

import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.chute.android.photopickerplus.R;
import com.chute.sdk.collections.GCAccountMediaCollection;
import com.chute.sdk.model.GCAccountMediaModel;
import com.darko.imagedownloader.ImageLoader;

public class PhotosAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private static final String TAG = PhotosAdapter.class.getSimpleName();
	private static LayoutInflater inflater;
	public ImageLoader loader;
	private GCAccountMediaCollection collection;
	public HashMap<Integer, GCAccountMediaModel> tick;
	private final DisplayMetrics displayMetrics;
	private final Activity context;

	public PhotosAdapter(final Activity context, final GCAccountMediaCollection collection) {
		if (collection == null) {
			this.collection = new GCAccountMediaCollection();
		} else {
			this.collection = collection;
		}
		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loader = ImageLoader.getLoader(context);
		displayMetrics = context.getResources().getDisplayMetrics();
		tick = new HashMap<Integer, GCAccountMediaModel>();
	}

	@Override
	public int getCount() {
		return collection.size();
	}

	@Override
	public GCAccountMediaModel getItem(final int position) {
		return collection.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	public static class ViewHolder {

		public ImageView image;
		public ImageView tick;
	}

	public void changeData(final GCAccountMediaCollection collection) {
		this.collection = collection;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.photos_select_adapter, null);
			holder = new ViewHolder();
			holder.image = (ImageView) vi.findViewById(R.id.imageViewThumb);
			holder.image.setLayoutParams(new RelativeLayout.LayoutParams(
					displayMetrics.widthPixels / 3,
					displayMetrics.widthPixels / 3));
			holder.image.setScaleType(ScaleType.CENTER_CROP);
			holder.tick = (ImageView) vi.findViewById(R.id.imageTick);
			holder.tick.setTag(position);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}

		if (tick.containsKey(position)) {
			holder.tick.setVisibility(View.VISIBLE);
			vi.setBackgroundColor(context.getResources().getColor(
					R.color.orange));
		} else {
			holder.tick.setVisibility(View.GONE);
			vi.setBackgroundColor(context.getResources().getColor(
					R.color.transparent));
		}
		loader.displayImage(getItem(position).getThumbUrl(), holder.image);
		return vi;
	}

	public GCAccountMediaCollection getPhotoCollection() {
		final GCAccountMediaCollection photos = new GCAccountMediaCollection();
		final Iterator<GCAccountMediaModel> iterator = tick.values().iterator();
		while (iterator.hasNext()) {
			photos.add(iterator.next());
		}
		return photos;
	}

	public boolean hasSelectedItems() {
		return tick.size() > 0;
	}

	public int getSelectedItemsCount() {
		return tick.size();
	}

	public void toggleTick(final int position) {
		if (tick.containsKey(position)) {
			tick.remove(position);
		} else {
			tick.put(position, getItem(position));
		}
		notifyDataSetChanged();
	}

}
