package com.challenger.modules.event.service.event;

import com.challenger.modules.event.domain.Enrollment;
import com.challenger.modules.event.domain.event.EnrollmentEvent;

public class EnrollmentRejectedEvent extends EnrollmentEvent {

    public EnrollmentRejectedEvent(Enrollment enrollment) {

        super(enrollment, "모임 참가 신청을 거절했습니다.");
    }
}
