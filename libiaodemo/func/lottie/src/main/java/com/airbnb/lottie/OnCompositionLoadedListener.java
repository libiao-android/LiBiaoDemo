package com.airbnb.lottie;

import androidx.annotation.Nullable;

public interface OnCompositionLoadedListener {
  /**
   * Composition will be null if there was an error loading it. Check logcat for more details.
   */
  void onCompositionLoaded(@Nullable LottieComposition composition);
}
