package com.example.buttonnav2.ui.notifications;

import android.os.Bundle;
import android.content.BroadcastReceiver;
import  android.content.Context;
import  android.content.Intent;
import  android.content.IntentFilter;
import android.os.BatteryManager;
import  android.widget.Button;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.buttonnav2.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    IntentFilter intentFilter;

    int deviceHealth;

    String currentBatteryHealth="Battery Health";
    int batteryLevel;

    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView = binding.textNotifications;
        final  Button checkStatus = binding.buttonNoti;
        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        checkStatus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                getActivity().registerReceiver(broadcastReceiver, intentFilter);
            }
        });
        return root;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            deviceHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_COLD){
                textView.setText(currentBatteryHealth+" = Cold");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_DEAD){
                textView.setText(currentBatteryHealth+" = Dead");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_GOOD){
                textView.setText(currentBatteryHealth+"= Good");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE){
                textView.setText(currentBatteryHealth+" = OverVoltage");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT){
                textView.setText(currentBatteryHealth+"= OverHeat");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN){
                textView.setText(currentBatteryHealth+"= Unknown");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE){
                textView.setText(currentBatteryHealth+"= Failure");
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}