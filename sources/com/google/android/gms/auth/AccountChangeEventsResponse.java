package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.List;

public class AccountChangeEventsResponse implements SafeParcelable {
    public static final Creator<AccountChangeEventsResponse> CREATOR;
    final int mVersion;
    final List<AccountChangeEvent> zzpH;

    static {
        zzc zzc;
        zzc zzc2 = zzc;
        zzc zzc3 = new zzc();
        CREATOR = zzc2;
    }

    AccountChangeEventsResponse(int i, List<AccountChangeEvent> list) {
        List<AccountChangeEvent> list2 = list;
        this.mVersion = i;
        this.zzpH = (List) zzx.zzz(list2);
    }

    public AccountChangeEventsResponse(List<AccountChangeEvent> list) {
        List<AccountChangeEvent> list2 = list;
        this.mVersion = 1;
        this.zzpH = (List) zzx.zzz(list2);
    }

    public int describeContents() {
        return 0;
    }

    public List<AccountChangeEvent> getEvents() {
        return this.zzpH;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }
}
