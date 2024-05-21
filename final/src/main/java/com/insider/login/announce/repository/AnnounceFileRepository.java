package com.insider.login.announce.repository;

import com.insider.login.announce.entity.AncFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnounceFileRepository extends JpaRepository<AncFile, Integer> {

    List<AncFile> findByAncNo(int ancNo);
}
