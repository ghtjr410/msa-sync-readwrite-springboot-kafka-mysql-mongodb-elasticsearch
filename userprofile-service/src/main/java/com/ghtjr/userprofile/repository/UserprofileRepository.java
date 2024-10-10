package com.ghtjr.userprofile.repository;

import com.ghtjr.userprofile.model.Userprofile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserprofileRepository extends JpaRepository<Userprofile, String> {
    Optional<Userprofile> findByUuid(String uuid);
}
