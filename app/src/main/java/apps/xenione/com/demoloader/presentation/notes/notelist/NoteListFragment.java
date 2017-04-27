package apps.xenione.com.demoloader.presentation.notes.notelist;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import apps.xenione.com.demoloader.R;
import apps.xenione.com.demoloader.data.Note;
import apps.xenione.com.demoloader.presentation.BaseFragment;
import apps.xenione.com.demoloader.presentation.notes.Navigation;
import apps.xenione.com.demoloader.presentation.notes.notelist.di.NoteListModule;
import apps.xenione.com.demoloader.presentation.view.Notifier;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteListFragment extends BaseFragment implements NoteListContract.View {

    public static final String TAG = "UserListFragment";

    public static Fragment newInstance() {
        return new NoteListFragment();
    }

    @BindView(R.id.user_list_list)
    RecyclerView mListView;

    @BindView(R.id.user_list_load_more)
    TextView mLoadMoreView;

    @Inject
    Navigation mNavigation;

    @Inject
    Notifier mNotifier;

    @Inject
    NoteListContract.Presenter mPresenter;

    private NoteListAdapter mNoteListAdapter;

    private List<Note> mNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNote = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getNoteComponent().plus(new NoteListModule()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    private void initViews() {
        mListView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mListView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());

        mListView.addItemDecoration(dividerItemDecoration);

        mPresenter.setView(this);

        mNoteListAdapter = new NoteListAdapter(mPresenter, mNote);
        mListView.setAdapter(mNoteListAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.setView(null);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mLoadMoreView.setText(active ? "loading" : "load more");
    }

    @Override
    public void showUsers(List<Note> notes) {
        mNoteListAdapter.setUsers(notes);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showDetail(Note note) {
        mNavigation.showNoteDetail(note.getId());
    }

    @Override
    public void showError(String error) {
        mNotifier.notify(error);
    }
}
