package hook;

import note.AbstractNote;

import java.util.ArrayList;

public interface OnChordHook {
    void OnChord(ArrayList<AbstractNote> notes);
}
