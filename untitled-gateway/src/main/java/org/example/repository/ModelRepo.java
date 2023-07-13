package org.example.repository;

import org.example.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ModelRepo extends JpaRepository<Model, Long> {
}
