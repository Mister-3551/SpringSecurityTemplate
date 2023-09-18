package com.springsecurity.template.repository;

import com.springsecurity.template.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT " +
            "    u.id, " +
            "    u.full_name, " +
            "    u.username, " +
            "    u.email_address, " +
            "    u.password, " +
            "    GROUP_CONCAT(a.authority) AS authorities, " +
            "    u.birth_date, " +
            "    u.country, " +
            "    u.account_locked, " +
            "    u.unlock_date, " +
            "    u.reports_number " +
            "FROM " +
            "    users u " +
            "INNER JOIN " +
            "    users_authorities ua ON ua.id_user = u.id " +
            "INNER JOIN " +
            "    authorities a ON a.id = ua.id_authority " +
            "WHERE " +
            "    u.username = :usernameOrEmailAddress OR u.email_address = :usernameOrEmailAddress " +
            "GROUP BY " +
            "    u.full_name, u.username, u.email_address, u.password, u.birth_date, u.country, u.account_locked, u.unlock_date, u.reports_number ", nativeQuery = true)
    Optional<User> findByUsernameOrEmailAddress(String usernameOrEmailAddress);
}