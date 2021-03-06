package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AuthAccountRequest implements SafeParcelable {
    public static final Creator<AuthAccountRequest> CREATOR;
    final int mVersionCode;
    final Scope[] zzafT;
    final IBinder zzakA;
    Integer zzakB;
    Integer zzakC;

    static {
        zzc zzc;
        zzc zzc2 = zzc;
        zzc zzc3 = new zzc();
        CREATOR = zzc2;
    }

    AuthAccountRequest(int i, IBinder iBinder, Scope[] scopeArr, Integer num, Integer num2) {
        IBinder iBinder2 = iBinder;
        Scope[] scopeArr2 = scopeArr;
        Integer num3 = num;
        Integer num4 = num2;
        this.mVersionCode = i;
        this.zzakA = iBinder2;
        this.zzafT = scopeArr2;
        this.zzakB = num3;
        this.zzakC = num4;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }
}
