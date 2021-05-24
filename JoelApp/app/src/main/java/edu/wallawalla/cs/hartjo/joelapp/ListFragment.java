package edu.wallawalla.cs.hartjo.joelapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import java.util.List;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        LinearLayout layout = (LinearLayout) view;

        // Create the buttons using the band names and ids from BandDatabase
        List<Page> pageList = PageDatabase.getInstance(getContext()).getPages();
        for (int i = 0; i < pageList.size(); i++) {
            Button button = new Button(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 10);   // 10 px
            button.setLayoutParams(layoutParams);

            // Set the text to the band's name and tag to the band ID
            Page page = PageDatabase.getInstance(getContext()).getPage(i + 1);
            button.setText(page.getName());
            button.setTag(Integer.toString(page.getId()));

            // All the buttons have the same click listener
            button.setOnClickListener(buttonClickListener);

            // Add the button to the LinearLayout
            layout.addView(button);
        }

        return view;
    }

    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Start DetailsActivity
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            String pageId = (String) view.getTag();
            intent.putExtra(DetailsActivity.EXTRA_PAGE_ID, Integer.parseInt(pageId));
            startActivity(intent);

            switch(pageId) {
                case "4":
                    MediaPlayer.create(getContext(), R.raw.secret).start();
            }
        }
    };
}