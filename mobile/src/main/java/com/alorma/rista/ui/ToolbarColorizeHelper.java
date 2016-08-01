/*
Copyright 2015 Michal Pawlowski

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.alorma.rista.ui;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

/**
 * Helper class that iterates through Toolbar views, and sets dynamically icons and texts color
 * Created by chomi3 on 2015-01-19.
 */
public class ToolbarColorizeHelper {

  /**
   * Use this method to colorize toolbar icons to the desired target color
   *
   * @param toolbarView toolbar view being colored
   * @param toolbarIconsColor the target color of toolbar icons
   */
  public static void colorizeToolbar(Toolbar toolbarView, int toolbarIconsColor) {
    final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(toolbarIconsColor, PorterDuff.Mode.MULTIPLY);

    for (int i = 0; i < toolbarView.getChildCount(); i++) {
      final View v = toolbarView.getChildAt(i);

      //Step 1 : Changing the color of back button (or open drawer button).
      if (v instanceof ImageButton) {
        //Action Bar back button
        ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
      }

      //Step 3: Changing the color of title and subtitle.
      toolbarView.setTitleTextColor(toolbarIconsColor);
      toolbarView.setSubtitleTextColor(toolbarIconsColor);
    }
  }
}