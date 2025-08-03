package com.example.journalApp.Repository;

import com.example.journalApp.Model.JournalModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JournalRepository extends MongoRepository<JournalModel,String> {

    List<JournalModel> findByUserId(String userId);
}
