package org.thoughtcrime.securesms.jobmanager.impl;

import android.app.Application;
import android.app.job.JobInfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.thoughtcrime.securesms.jobmanager.Constraint;

public class NetworkConstraint implements Constraint {

  public static final String KEY = "NetworkConstraint";

  private final Application application;

  private NetworkConstraint(@NonNull Application application) {
    this.application = application;
  }

  @Override
  public boolean isMet() {
    return isMet(application);
  }

  @Override
  public @NonNull String getFactoryKey() {
    return KEY;
  }

  @RequiresApi(26)
  @Override
  public void applyToJobInfo(@NonNull JobInfo.Builder jobInfoBuilder) {
    jobInfoBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
  }

  @Override
  public String getJobSchedulerKeyPart() {
    return "NETWORK";
  }

  public static boolean isMet(@NonNull Application application) {
    return NetworkConstraintObserver.getInstance(application).hasInternet();
  }

  public static final class Factory implements Constraint.Factory<NetworkConstraint> {

    private final Application application;

    public Factory(@NonNull Application application) {
      this.application = application;
    }

    @Override
    public NetworkConstraint create() {
      return new NetworkConstraint(application);
    }
  }
}
