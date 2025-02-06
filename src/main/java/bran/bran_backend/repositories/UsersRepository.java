package bran.bran_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bran.bran_backend.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}