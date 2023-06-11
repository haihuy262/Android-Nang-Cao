package huynhph30022.fpoly.assignmentmob201.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.model.Song;
import huynhph30022.fpoly.assignmentmob201.service.MusicService;

public class MusicFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        AppCompatButton btnStartService = view.findViewById(R.id.btnStartService);
        AppCompatButton btnStopService = view.findViewById(R.id.btnStopService);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStartService();
            }
        });
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStopService();
            }
        });
        return view;
    }

    private void clickStartService() {
        Song objSong = new Song("Big city boy", "Nguyen Hai Huy", R.mipmap.img_nhac, R.raw.music);
        Intent intent = new Intent(requireContext(), MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song", objSong);
        intent.putExtras(bundle);
        requireContext().startService(intent);
    }

    private void clickStopService() {
        Intent intent = new Intent(requireContext(), MusicService.class);
        requireContext().stopService(intent);
    }
}