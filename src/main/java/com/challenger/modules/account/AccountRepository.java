package com.challenger.modules.account;

import com.challenger.modules.account.Account;
import com.challenger.modules.account.form.SignUpForm;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long>,
        QuerydslPredicateExecutor<Account> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Account findByEmail(String email);

    Account findByNickname(String nickname);

    @EntityGraph(attributePaths = {"tags"})
    Account findAccountWithTagsAndZonesById(Long id);


}




























