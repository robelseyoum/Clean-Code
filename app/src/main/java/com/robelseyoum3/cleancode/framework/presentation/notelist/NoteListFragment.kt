package com.robelseyoum3.cleancode.notelist

import android.os.Bundle
import android.view.View
import com.robelseyoum3.cleancode.R
import com.robelseyoum3.cleancode.common.BaseNoteFragment

//const val NOTE_LIST_STATE_BUNDLE_KEY = "com.codingwithmitch.cleannotes.notes.framework.presentation.notelist.state"

class NoteListFragment : BaseNoteFragment(R.layout.fragment_note_list)
{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun inject() {
        TODO("prepare dagger")
    }

}
