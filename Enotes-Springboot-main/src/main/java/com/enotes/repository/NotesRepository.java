package com.enotes.repository;

import com.enotes.entity.Notes;
import com.enotes.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes,Integer> {

    public Page<Notes> findByUser(User user, Pageable pageable);
}
