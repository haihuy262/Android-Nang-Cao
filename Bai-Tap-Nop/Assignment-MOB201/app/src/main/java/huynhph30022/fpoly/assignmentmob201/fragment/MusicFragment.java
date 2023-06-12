package huynhph30022.fpoly.assignmentmob201.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import huynhph30022.fpoly.assignmentmob201.R;
import huynhph30022.fpoly.assignmentmob201.adapter.MusicAdapter;
import huynhph30022.fpoly.assignmentmob201.dao.MusicDAO;
import huynhph30022.fpoly.assignmentmob201.model.Song;
import huynhph30022.fpoly.assignmentmob201.service.MusicService;

public class MusicFragment extends Fragment {
    protected RecyclerView recyclerViewMusic;
    protected MusicDAO musicDAO;
    protected ArrayList<Song> list;
    protected MusicAdapter adapter;
    protected TextView tvTitleBaiHat, tvCurrentTime, tvTotalTime;
    protected RelativeLayout relativeLayoutControlMusic;
    protected LinearLayoutManager layoutManager;
    protected SeekBar seekBar;
    protected MediaPlayer mediaPlayer;
    protected BroadcastReceiver updateSeekBarReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalTime = intent.getIntExtra("totalTime", 0);
            updateSeekBar(totalTime);
        }
    };
    protected BroadcastReceiver updateUIReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int currentTime = intent.getIntExtra("currentTime", 0);
            updateUI(currentTime);
        }
    };

    private void updateUI(int currentTime) {
        seekBar.setProgress(currentTime);
        tvCurrentTime.setText(formatTime(currentTime));
    }

    private String formatTime(int timeInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return dateFormat.format(new Date(timeInMillis));
    }

    private void updateSeekBar(int totalTime) {
        seekBar.setMax(totalTime);
        tvTotalTime.setText(formatTime(totalTime));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        recyclerViewMusic = view.findViewById(R.id.recyclerMusic);
        tvTitleBaiHat = view.findViewById(R.id.song_title);
        relativeLayoutControlMusic = view.findViewById(R.id.controlMusic);
        seekBar = view.findViewById(R.id.seekBar);
        tvCurrentTime = view.findViewById(R.id.current_time);
        tvTotalTime = view.findViewById(R.id.total_time);

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

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(updateSeekBarReceiver, new IntentFilter(MusicService.ACTION_UPDATE_SEEKBAR));
        requireActivity().registerReceiver(updateUIReceiver, new IntentFilter(MusicService.ACTION_UPDATE_UI));
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(updateSeekBarReceiver);
        requireActivity().unregisterReceiver(updateUIReceiver);
    }
}