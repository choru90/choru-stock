package com.choru.stockquery.adapter.out

import com.choru.stockquery.domain.TickSummary
import com.choru.stockquery.domain.TickSummaryId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TickSummaryRepository : JpaRepository<TickSummary, TickSummaryId> {
    @Query("select t from TickSummary t where t.id.symbol = :symbol and t.id.interval = :interval and t.id.time between :from and :to order by t.id.time")
    fun findBars(
        @Param("symbol") symbol: String,
        @Param("interval") interval: String,
        @Param("from") from: LocalDateTime,
        @Param("to") to: LocalDateTime
    ): List<TickSummary>
}
