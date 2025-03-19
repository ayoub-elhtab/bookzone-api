package com.ayoub.BookZone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ayoub.BookZone.model.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

}
