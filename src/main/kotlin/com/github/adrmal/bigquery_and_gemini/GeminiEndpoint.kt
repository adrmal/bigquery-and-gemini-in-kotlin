package com.github.adrmal.bigquery_and_gemini

import com.google.cloud.vertexai.VertexAI
import com.google.cloud.vertexai.generativeai.GenerativeModel
import com.google.cloud.vertexai.generativeai.ResponseHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class GeminiEndpoint(
    @Value("\${gemini.project-id}") private val projectId: String,
    @Value("\${gemini.location}") private val location: String,
    @Value("\${gemini.model}") private val model: String,
) {

    @GetMapping(
        value = ["/gemini"],
        produces = [APPLICATION_JSON_VALUE],
    )
    fun getGeminiResponse(
        @RequestBody requestDto: GeminiRequestDto,
    ): GeminiResponseDto {
        val vertexAI = VertexAI(projectId, location)
        val geminiModel = GenerativeModel(model, vertexAI)

        val prompt = requestDto.prompt
        val response = geminiModel.generateContent(prompt)
        val textResponse = ResponseHandler.getText(response)

        return GeminiResponseDto(
            response = textResponse,
        )
    }
}

data class GeminiRequestDto(
    val prompt: String,
)

data class GeminiResponseDto(
    val response: String,
)
