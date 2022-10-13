package com.gemjarwallet.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gemjarwallet.app.BuildConfig;
import com.gemjarwallet.app.R;
import com.gemjarwallet.app.entity.DApp;
import com.gemjarwallet.app.ui.widget.OnDappClickListener;
import com.gemjarwallet.app.ui.widget.adapter.MyDappsListAdapter;
import com.gemjarwallet.app.util.DappBrowserUtils;
import com.gemjarwallet.app.util.KeyboardUtils;
import com.gemjarwallet.app.widget.AWalletAlertDialog;

import java.util.List;

import static com.gemjarwallet.app.ui.DappBrowserFragment.DAPP_CLICK;

import timber.log.Timber;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyDappsFragment extends Fragment implements OnDappClickListener {
    private MyDappsListAdapter adapter;
    private AWalletAlertDialog dialog;
    private TextView noDapps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_my_dapps, container, false);
        adapter = new MyDappsListAdapter(
                getData(),
                this,
                this::onDappRemoved,
                this::onDappEdited);
        RecyclerView list = view.findViewById(R.id.my_dapps_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        noDapps = view.findViewById(R.id.no_dapps);
        showOrHideViews();
        KeyboardUtils.hideKeyboard(view);
        return view;
    }

    private List<DApp> getData() {
        return DappBrowserUtils.getMyDapps(getContext());
    }

    private void onDappEdited(DApp dapp) {
        Intent intent = new Intent(getActivity(), AddEditDappActivity.class);
        intent.putExtra("mode", 1);
        intent.putExtra("dapp", dapp);
        getActivity().startActivity(intent);
    }

    private void onDappRemoved(DApp dapp) {
        dialog = new AWalletAlertDialog(getActivity());
        dialog.setTitle(R.string.title_remove_dapp);
        dialog.setMessage(getString(R.string.remove_from_my_dapps, dapp.getName()));
        dialog.setIcon(AWalletAlertDialog.NONE);
        dialog.setButtonText(R.string.action_remove);
        dialog.setButtonListener(v -> {
            removeDapp(dapp);
            dialog.dismiss();
        });
        dialog.setSecondaryButtonText(R.string.dialog_cancel_back);
        dialog.show();
    }

    private void removeDapp(DApp dapp) {
        try
        {
            List<DApp> myDapps = DappBrowserUtils.getMyDapps(getContext());
            for (DApp d : myDapps)
            {
                if (d.getName().equals(dapp.getName())
                        && d.getUrl().equals(dapp.getUrl()))
                {
                    myDapps.remove(d);
                    break;
                }
            }
            DappBrowserUtils.saveToPrefs(getContext(), myDapps);
        }
        catch (Exception e)
        {
            Timber.e(e);
        }
        finally
        {
            updateData();
        }
    }

    private void updateData() {
        adapter.setDapps(DappBrowserUtils.getMyDapps(getContext()));
        showOrHideViews();
    }

    private void showOrHideViews() {
        if (adapter.getItemCount() > 0) {
            noDapps.setVisibility(View.GONE);
        } else {
            noDapps.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    @Override
    public void onDappClick(DApp dapp)
    {
        Bundle result = new Bundle();
        result.putParcelable(DAPP_CLICK, dapp);
        getParentFragmentManager().setFragmentResult(DAPP_CLICK, result);
    }
}
