package com.example.springwebfluxfirebasebackend.repository;

import com.example.springwebfluxfirebasebackend.model.Person;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends FirestoreReactiveRepository<Person> {
}
