package com.chute.android.photopickerplus.util.intent;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.chute.android.photopickerplus.app.ChooseServiceActivity;
import com.chute.sdk.collections.GCAccountMediaCollection;
import com.chute.sdk.model.GCAccountMediaModel;

public class IntentUtil {

    public static void deliverDataToInitialActivity(final Activity context,
	    final GCAccountMediaModel model) {
	deliverDataToInitialActivity(context, model, null, null);
    }

    public static void deliverDataToInitialActivity(final Activity context,
	    final GCAccountMediaModel model, final String albumId, final String accountId) {
	GCAccountMediaCollection mediaCollection = new GCAccountMediaCollection();
	mediaCollection.add(model);
	deliverDataToInitialActivity(context, mediaCollection);
    }

    public static void deliverDataToInitialActivity(final Activity context,
	    final GCAccountMediaCollection collection) {
	deliverDataToInitialActivity(context, collection, null, null);
    }

    public static void deliverDataToInitialActivity(final Activity context,
	    final GCAccountMediaCollection collection, final String albumId, final String accountId) {
	final PhotoActivityIntentWrapper wrapper = new PhotoActivityIntentWrapper(new Intent(
		context, ChooseServiceActivity.class));
	if (!TextUtils.isEmpty(accountId)) {
	    wrapper.setAccountId(accountId);
	}
	if (!TextUtils.isEmpty(albumId)) {
	    wrapper.setAlbumId(albumId);
	}
	wrapper.setMediaCollection(collection);
	wrapper.getIntent().addFlags(
		Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	wrapper.startActivity(context);
    }
}
