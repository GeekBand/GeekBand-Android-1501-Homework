

package com.snail.travellingTrail.common.wizard.model;

/**
 * Callback interface connecting {@link Page}, {@link AbstractWizardModel}, and model container
 * objects (e.g. {@link com.snail.travellingTrail.newTravel.controller.CreateNewTravelActivity.android.wizardpager.MainActivity}.
 */
public interface ModelCallbacks {
    void onPageDataChanged(Page page);
    void onPageTreeChanged();
}
