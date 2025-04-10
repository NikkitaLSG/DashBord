package org.example.vkr3test.Error;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorRepository extends JpaRepository<ApplicationError, Long> {
}
