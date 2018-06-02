package br.edu.uni7.filmes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

public abstract class BaseFragment extends Fragment {

  protected ProgressBar mProgressBar = null;
  protected View mView = null;

  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  public void showProgress(final boolean show) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

      if (mView != null) {
        mView.setVisibility(show ? View.GONE : View.VISIBLE);
        mView.animate().setDuration(shortAnimTime).alpha(
            show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            mView.setVisibility(show ? View.GONE : View.VISIBLE);
          }
        });
      }

      if (mProgressBar != null) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressBar.animate().setDuration(shortAnimTime).alpha(
            show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
          }
        });
      }
    } else {

      if (mProgressBar != null) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
      }
      if (mView != null) {
        mView.setVisibility(show ? View.GONE : View.VISIBLE);
      }
    }
  }

  public View getView() {
    return mView;
  }
}

