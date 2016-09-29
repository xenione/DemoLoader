package apps.xenione.com.demoloader.presentation;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;

import apps.xenione.com.demoloader.Domain.Note;
import apps.xenione.com.demoloader.Domain.NoteRepository;
import apps.xenione.com.demoloader.usecases.AddNoteUseCase;
import apps.xenione.com.demoloader.usecases.GetNoteUseCase;

/**
 * Created by Eugeni on 17/09/2016.
 */
public class App extends Application {

    private NoteRepository mNoteRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        mNoteRepository = new NoteRepository();
    }

    public static NoteRepository getNoteDao(Context context) {
        return ((App) context.getApplicationContext()).mNoteRepository;
    }

    public static LoaderManager getLoaderManager(FragmentActivity activity) {
        return activity.getSupportLoaderManager();
    }

    public static FragmentManager getFragmentManager(FragmentActivity activity) {
        return activity.getSupportFragmentManager();
    }

    public static AddNoteUseCase getAddNoteUseCase(FragmentActivity activity, Note note) {
        return new AddNoteUseCase(getNoteDao(activity), note);
    }

    public static GetNoteUseCase getGetNoteUseCase(FragmentActivity activity) {
        return new GetNoteUseCase(getNoteDao(activity));
    }
}
