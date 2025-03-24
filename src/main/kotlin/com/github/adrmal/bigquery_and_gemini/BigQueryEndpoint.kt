package com.github.adrmal.bigquery_and_gemini

import com.google.cloud.bigquery.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class BigQueryEndpoint(
    @Value("\${big-query.project-id}") private val projectId: String,
) {

    @GetMapping(
        value = ["/bigQuery"],
        produces = [APPLICATION_JSON_VALUE],
    )
    fun getBigQueryResponse(
        @RequestBody requestDto: BigQueryRequestDto,
    ): BigQueryResponseDto {
        try {
            val bigQuery = BigQueryOptions.getDefaultInstance().service

            val query = requestDto.query
            val queryConfig = QueryJobConfiguration.newBuilder(query)
                .setUseLegacySql(false)
                .build()

            val jobId = JobId.newBuilder().setProject(projectId).build()
            val jobInfo = JobInfo.newBuilder(queryConfig).setJobId(jobId).build()
            var queryJob = bigQuery.create(jobInfo)

            queryJob = queryJob.waitFor()

            if (queryJob == null) {
                throw RuntimeException("Job no longer exists")
            } else if (queryJob.status.error != null) {
                throw RuntimeException(queryJob.status.error.toString())
            }

            val queryResult = queryJob.getQueryResults()

            return BigQueryResponseDto(
                result = queryResult.iterateAll().joinToString(),
            )
        } catch (e: BigQueryException) {
            throw RuntimeException("Searching in GCP failed", e)
        } catch (e: InterruptedException) {
            throw RuntimeException("Searching in GCP failed", e)
        }
    }
}

data class BigQueryRequestDto(
    val query: String,
)

data class BigQueryResponseDto(
    val result: String,
)
