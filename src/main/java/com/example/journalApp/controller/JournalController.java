package com.example.journalApp.controller;

import com.example.journalApp.Model.JournalModel;
import com.example.journalApp.Repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/journals")
    public class JournalController {

        @Autowired
        private JournalRepository journalRepository;

        // Post journals
        @PostMapping
        public JournalModel createJournal(@RequestBody JournalModel journal)
        {
            return journalRepository.save(journal);
        }

        // Get all journals
        @GetMapping
        public List<JournalModel> getAllJournals()
        {
            return journalRepository.findAll();
        }
        // Get Journals by id
       @GetMapping("/user/{userId}")
       public ResponseEntity<List<JournalModel>> getJournalsByUserId(@PathVariable String userId) {
        List<JournalModel> journals = journalRepository.findByUserId(userId);

        if (journals.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(journals);
        }
    }

    @PutMapping("/{journalId}")
    public ResponseEntity<JournalModel> updateJournal(
            @PathVariable String journalId,
            @RequestBody JournalModel updatedJournal) {

        Optional<JournalModel> existingJournalOpt = journalRepository.findById(journalId);

        if (existingJournalOpt.isPresent()) {
            JournalModel existingJournal = existingJournalOpt.get();

            // Update fields
            existingJournal.setTitle(updatedJournal.getTitle());
            existingJournal.setContent(updatedJournal.getContent());
            // optionally: don't allow userId to be updated

            JournalModel savedJournal = journalRepository.save(existingJournal);
            return ResponseEntity.ok(savedJournal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // delete by id
      @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteJournal(@PathVariable String id)
      {
        Optional<JournalModel> journalOpt = journalRepository.findById(id);

        if (journalOpt.isPresent()) {
            journalRepository.deleteById(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
    }



}


