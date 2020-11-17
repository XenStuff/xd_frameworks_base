/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.app.people;

import android.annotation.NonNull;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;

/**
 * The People Space tile contains all relevant information to render a tile in People Space: namely
 * the data of any visible conversation notification associated, associated statuses, and the last
 * interaction time.
 *
 * @hide
 */
public class PeopleSpaceTile implements Parcelable {

    private String mId;
    private CharSequence mUserName;
    private Icon mUserIcon;
    private int mUid;
    private Uri mContactUri;
    private String mPackageName;
    private long mLastInteractionTimestamp;
    private boolean mIsImportantConversation;
    private boolean mIsHiddenConversation;
    private StatusBarNotification mNotification;
    private Intent mIntent;
    // TODO: add a List of the Status objects once created

    private PeopleSpaceTile(Builder b) {
        mId = b.mId;
        mUserName = b.mUserName;
        mUserIcon = b.mUserIcon;
        mContactUri = b.mContactUri;
        mUid = b.mUid;
        mPackageName = b.mPackageName;
        mLastInteractionTimestamp = b.mLastInteractionTimestamp;
        mIsImportantConversation = b.mIsImportantConversation;
        mIsHiddenConversation = b.mIsHiddenConversation;
        mNotification = b.mNotification;
        mIntent = b.mIntent;
    }

    public String getId() {
        return mId;
    }

    public CharSequence getUserName() {
        return mUserName;
    }

    public Icon getUserIcon() {
        return mUserIcon;
    }

    /** Returns the Uri associated with the user in Android Contacts database. */
    public Uri getContactUri() {
        return mContactUri;
    }

    public int getUid() {
        return mUid;
    }

    public String getPackageName() {
        return mPackageName;
    }

    /** Returns the timestamp of the last interaction. */
    public long getLastInteractionTimestamp() {
        return mLastInteractionTimestamp;
    }

    /**
     * Whether the conversation is important.
     */
    public boolean isImportantConversation() {
        return mIsImportantConversation;
    }

    /**
     * Whether the conversation should be hidden.
     */
    public boolean isHiddenConversation() {
        return mIsHiddenConversation;
    }

    /**
     * If a notification is currently active that maps to the relevant shortcut ID, provides the
     * {@link StatusBarNotification} associated.
     */
    public StatusBarNotification getNotification() {
        return mNotification;
    }

    /**
     * Provides an intent to launch. If present, we should manually launch the intent on tile
     * click, rather than calling {@link android.content.pm.LauncherApps} to launch the shortcut ID.
     *
     * <p>This field should only be used if manually constructing a tile without an associated
     * shortcut to launch (i.e. birthday tiles).
     */
    public Intent getIntent() {
        return mIntent;
    }

    /** Builder to create a {@link PeopleSpaceTile}. */
    public static class Builder {
        private String mId;
        private CharSequence mUserName;
        private Icon mUserIcon;
        private Uri mContactUri;
        private int mUid;
        private String mPackageName;
        private long mLastInteractionTimestamp;
        private boolean mIsImportantConversation;
        private boolean mIsHiddenConversation;
        private StatusBarNotification mNotification;
        private Intent mIntent;

        /** Builder for use only if a shortcut is not available for the tile. */
        public Builder(String id, String userName, Icon userIcon, Intent intent) {
            mId = id;
            mUserName = userName;
            mUserIcon = userIcon;
            mIntent = intent;
            mPackageName = intent == null ? null : intent.getPackage();
        }

        public Builder(ShortcutInfo info) {
            mId = info.getId();
            mUserName = info.getLabel();
            mUserIcon = info.getIcon();
            mUid = info.getUserId();
            mPackageName = info.getPackage();
        }

        /** Sets the ID for the tile. */
        public Builder setId(String id) {
            mId = id;
            return this;
        }

        /** Sets the user name. */
        public Builder setUserName(CharSequence userName) {
            mUserName = userName;
            return this;
        }

        /** Sets the icon shown for the user. */
        public Builder setUserIcon(Icon userIcon) {
            mUserIcon = userIcon;
            return this;
        }

        /** Sets the Uri associated with the user in Android Contacts database. */
        public Builder setContactUri(Uri uri) {
            mContactUri = uri;
            return this;
        }

        /** Sets the associated uid. */
        public Builder setUid(int uid) {
            mUid = uid;
            return this;
        }

        /** Sets the package shown that provided the information. */
        public Builder setPackageName(String packageName) {
            mPackageName = packageName;
            return this;
        }

        /** Sets the last interaction timestamp. */
        public Builder setLastInteractionTimestamp(long lastInteractionTimestamp) {
            mLastInteractionTimestamp = lastInteractionTimestamp;
            return this;
        }

        /** Sets whether the conversation is important. */
        public Builder setIsImportantConversation(boolean isImportantConversation) {
            mIsImportantConversation = isImportantConversation;
            return this;
        }

        /** Sets whether the conversation is hidden. */
        public Builder setIsHiddenConversation(boolean isHiddenConversation) {
            mIsHiddenConversation = isHiddenConversation;
            return this;
        }

        /** Sets the associated notification. */
        public Builder setNotification(StatusBarNotification notification) {
            mNotification = notification;
            return this;
        }

        /** Sets an intent to launch on click. */
        public Builder setIntent(Intent intent) {
            mIntent = intent;
            return this;
        }

        /** Builds a {@link PeopleSpaceTile}. */
        @NonNull
        public PeopleSpaceTile build() {
            return new PeopleSpaceTile(this);
        }
    }

    private PeopleSpaceTile(Parcel in) {
        mId = in.readString();
        mUserName = in.readCharSequence();
        mUserIcon = in.readParcelable(Icon.class.getClassLoader());
        mUid = in.readInt();
        mPackageName = in.readString();
        mLastInteractionTimestamp = in.readLong();
        mIsImportantConversation = in.readBoolean();
        mIsHiddenConversation = in.readBoolean();
        mNotification = in.readParcelable(StatusBarNotification.class.getClassLoader());
        mIntent = in.readParcelable(Intent.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeCharSequence(mUserName);
        dest.writeParcelable(mUserIcon, flags);
        dest.writeInt(mUid);
        dest.writeString(mPackageName);
        dest.writeLong(mLastInteractionTimestamp);
        dest.writeParcelable(mNotification, flags);
        dest.writeBoolean(mIsImportantConversation);
        dest.writeBoolean(mIsHiddenConversation);
        dest.writeParcelable(mIntent, flags);
    }

    public static final @android.annotation.NonNull
            Creator<PeopleSpaceTile> CREATOR = new Creator<PeopleSpaceTile>() {
                public PeopleSpaceTile createFromParcel(Parcel source) {
                    return new PeopleSpaceTile(source);
                }

                public PeopleSpaceTile[] newArray(int size) {
                    return new PeopleSpaceTile[size];
                }
            };
}
