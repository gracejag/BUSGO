package com.jaggagrace.busgo.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.app.FragmentTransaction;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jaggagrace.busgo.Fragmentfirst;
import com.jaggagrace.busgo.R;
import com.jaggagrace.busgo.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransaction ft = getParentFragmentManager()  .beginTransaction();
        ft.add(R.id.fragment_container, new Fragmentfirst(), "FragmentFirst");
        ft.setReorderingAllowed(true);
        ft.commit();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}