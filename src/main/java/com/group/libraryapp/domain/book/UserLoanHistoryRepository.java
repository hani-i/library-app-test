package com.group.libraryapp.domain.book;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {
    boolean existsByBookNameAndIsReturn(String name, boolean isReturn);
    Optional<UserLoanHistory> findByUserIdAndBookName(Long id, String bookName);
}
