package com.focus.lit.repository;

import com.focus.lit.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {

}
