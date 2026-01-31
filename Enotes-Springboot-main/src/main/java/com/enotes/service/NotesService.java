package com.enotes.service;

import com.enotes.entity.Notes;
import com.enotes.entity.User;
import org.springframework.data.domain.Page;

public interface NotesService {

    public Notes saveNotes(Notes notes);
    public Notes getNotesById(int id);
//    public List<Notes> getNotesByUser(User user);
    public Page<Notes> getNotesByUser(User user, int pageNo);
    public Notes updateNotes(Notes notes);
    public boolean deleteNotes(int id);
}
