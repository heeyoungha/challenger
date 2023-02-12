package com.challenger.modules.account;
import com.querydsl.core.types.Predicate;
import com.challenger.modules.tag.Tag;

import java.util.Set;

public class AccountPredicates {

    public static Predicate findByTagsAndZones(Set<Tag> tags) {
        QAccount account = QAccount.account;
        return account.tags.any().in(tags);
    }

}
