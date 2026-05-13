package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByIdentificationId(String identificationId);

    boolean existsByIdentificationId(String identificationId);

    boolean existsByUserName(String userName);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.userStatus = :status WHERE u.id = :id")
    void updateStatus(@Param("id") long id, @Param("status") String status);
}