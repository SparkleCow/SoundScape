package com.sparklecow.soundscape.repositories;

import com.sparklecow.soundscape.entities.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

}
