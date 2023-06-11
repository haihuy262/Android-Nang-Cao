package huynhph30022.fpoly.assignmentmob201.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.adapter.MusicAdapter;
import huynhph30022.fpoly.assignmentmob201.dao.MusicDAO;
import huynhph30022.fpoly.assignmentmob201.model.Song;

public class MusicFragment extends Fragment {
    protected RecyclerView recyclerViewMusic;
    protected MusicDAO musicDAO;
    protected ArrayList<Song> list;
    protected MusicAdapter adapter;
    protected TextView tvTitleBaiHat;
    protected RelativeLayout relativeLayoutControlMusic;
    protected LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        recyclerViewMusic = view.findViewById(R.id.recyclerMusic);
        tvTitleBaiHat = view.findViewById(R.id.song_title);
        relativeLayoutControlMusic = view.findViewById(R.id.controlMusic);

        musicDAO = new MusicDAO(requireContext());
        list = new ArrayList<>();
        adapter = new MusicAdapter(requireContext(), tvTitleBaiHat, relativeLayoutControlMusic);
        layoutManager = new LinearLayoutManager(requireContext());

        recyclerViewMusic.setLayoutManager(layoutManager);
        list = musicDAO.getAllDatabase();
        adapter.setData(list);
        recyclerViewMusic.setAdapter(adapter);
        return view;
    }
}