package com.google.ads.mediation;

import android.location.Location;
import com.google.ads.AdRequest.Gender;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Deprecated
public class MediationAdRequest {
    private final Date zzbf;
    private final Gender zzbg;
    private final Set<String> zzbh;
    private final boolean zzbi;
    private final Location zzbj;

    public MediationAdRequest(Date date, Gender gender, Set<String> set, boolean z, Location location) {
        Gender gender2 = gender;
        Set<String> set2 = set;
        boolean z2 = z;
        Location location2 = location;
        this.zzbf = date;
        this.zzbg = gender2;
        this.zzbh = set2;
        this.zzbi = z2;
        this.zzbj = location2;
    }

    public Integer getAgeInYears() {
        if (this.zzbf == null) {
            return null;
        }
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        instance.setTime(this.zzbf);
        Integer valueOf = Integer.valueOf(instance2.get(1) - instance.get(1));
        if (instance2.get(2) < instance.get(2) || (instance2.get(2) == instance.get(2) && instance2.get(5) < instance.get(5))) {
            valueOf = Integer.valueOf(-1 + valueOf.intValue());
        }
        return valueOf;
    }

    public Date getBirthday() {
        return this.zzbf;
    }

    public Gender getGender() {
        return this.zzbg;
    }

    public Set<String> getKeywords() {
        return this.zzbh;
    }

    public Location getLocation() {
        return this.zzbj;
    }

    public boolean isTesting() {
        return this.zzbi;
    }
}
