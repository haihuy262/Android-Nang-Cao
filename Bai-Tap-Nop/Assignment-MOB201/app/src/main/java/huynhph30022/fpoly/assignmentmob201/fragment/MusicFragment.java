package huynhph30022.fpoly.assignmentmob201.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    protected ImageView imgPre, imgNext, imgPlayOrPause;
    protected MusicService musicService;
    protected boolean isBound = false;
    protected ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyBinder binder = (MusicService.MyBinder) service;
            musicService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
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
        imgPlayOrPause = view.findViewById(R.id.img_play_pause);
        imgNext = view.findViewById(R.id.imgNext);
        imgPre = view.findViewById(R.id.imgPrevious);

        musicDAO = new MusicDAO(requireContext());
        list = new ArrayList<>();
        adapter = new MusicAdapter(requireContext(), tvTitleBaiHat, relativeLayoutControlMusic, imgPlayOrPause);
        layoutManager = new LinearLayoutManager(requireContext());

        recyclerViewMusic.setLayoutManager(layoutManager);
        list = musicDAO.getAllDatabase();
        adapter.setData(list);
        recyclerViewMusic.setAdapter(adapter);
        imgPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicService.isPlaying()) {
                    musicService.pauseOrPlay();
                    imgPlayOrPause.setImageResource(R.drawable.baseline_play_circle_outline_24);
                } else {
                    musicService.pauseOrPlay();
                    imgPlayOrPause.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (musicService.mediaPlayer != null && fromUser) {
                    musicService.mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(requireContext(), MusicService.class);
        requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isBound) {
            requireContext().unbindService(connection);
            isBound = false;
        }
    }
}