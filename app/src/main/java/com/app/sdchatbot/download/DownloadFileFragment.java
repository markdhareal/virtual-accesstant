package com.app.sdchatbot.download;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.sdchatbot.R;
import com.app.sdchatbot.download.DownloadActivity;

public class DownloadFileFragment extends Fragment {

    private Button goToDownloadButton;

    public DownloadFileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View downloadView =  inflater.inflate(R.layout.fragment_download_file, container, false);

        goToDownloadButton = downloadView.findViewById(R.id.goToDownloadActButtonId);

        goToDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDownload();
            }
        });

        return  downloadView;
    }

    private void goToDownload()
    {
        Intent goToDownloadAct = new Intent(getActivity(), DownloadActivity.class);
        startActivity(goToDownloadAct);
    }
}