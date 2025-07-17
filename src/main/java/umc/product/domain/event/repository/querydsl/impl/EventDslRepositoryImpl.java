package umc.product.domain.event.repository.querydsl.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.entity.event.QEvent;
import umc.product.domain.event.repository.querydsl.EventDslRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventDslRepositoryImpl implements EventDslRepository {
    private final JPAQueryFactory queryFactory;
    private final QEvent event = QEvent.event;

    @Override
    public Page<Event> findByFilter(Pageable pageable, Integer month, String semester, EventType eventType) {

        List<Event> events = queryFactory
                .selectFrom(event)
                .where(
                        eqMonth(month),
                        eqSemester(semester),
                        eqType(eventType)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(
                queryFactory
                        .select(event.count())
                        .from(event)
                        .where(
                                eqMonth(month),
                                eqSemester(semester),
                                eqType(eventType)
                        )
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(events, pageable, total);

    }

    private BooleanExpression eqMonth(Integer month) {
        return (month != null) ? event.eventStartDate.month().eq(month) : null;
    }

    private BooleanExpression eqSemester(String semester) {
        return (semester != null) ? event.semester.name.eq(semester) : null;
    }

    private BooleanExpression eqType(EventType type) {
        return (type != null) ? event.eventType.eq(type) : null;
    }
}
