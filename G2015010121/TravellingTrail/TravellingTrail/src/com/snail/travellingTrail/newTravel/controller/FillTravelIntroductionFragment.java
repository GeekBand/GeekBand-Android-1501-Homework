
package com.snail.travellingTrail.newTravel.controller;


import com.snail.travellingTrail.R;
import com.snail.travellingTrail.common.wizard.ui.PageFragmentCallbacks;
import com.snail.travellingTrail.newTravel.model.FillTravelIntroductionPage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class FillTravelIntroductionFragment extends Fragment {
    private static final String ARG_KEY = "FillTravelIntroductionFragment";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private FillTravelIntroductionPage mPage;
    private TextView mNameView;

    public static FillTravelIntroductionFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        FillTravelIntroductionFragment fragment = new FillTravelIntroductionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FillTravelIntroductionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (FillTravelIntroductionPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fill_travel_introduction, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mNameView = ((TextView) rootView.findViewById(R.id.frag_fill_travel_introdution_edt_introdution));
        mNameView.setText(mPage.getData().getString(FillTravelIntroductionPage.INTRONDUCTION_DATA_KEY));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                    int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(FillTravelIntroductionPage.INTRONDUCTION_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if (mNameView != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
