package edu.wallawalla.cs.hartjo.joelapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import edu.wallawalla.cs.hartjo.joelapp.Page;
import edu.wallawalla.cs.hartjo.joelapp.PageDatabase;

public class DetailsFragment extends Fragment {

    private Page mPage;

    public static DetailsFragment newInstance(int pageId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("pageId", pageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the page ID from the intent that started DetailsActivity
        int pageId = 1;
        if (getArguments() != null) {
            pageId = getArguments().getInt("pageId");
        }

        mPage = PageDatabase.getInstance(getContext()).getPage(pageId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView nameTextView = view.findViewById(R.id.pageName);
        nameTextView.setText(mPage.getName());

        TextView descriptionTextView = view.findViewById(R.id.pageDescription);
        descriptionTextView.setText(mPage.getDescription());

        return view;
    }
}