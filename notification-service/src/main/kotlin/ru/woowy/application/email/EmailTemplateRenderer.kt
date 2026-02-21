package ru.woowy.application.email

import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.woowy.domain.model.EmailBody

@Component
internal class EmailTemplateRenderer(
    private val templateEngine: SpringTemplateEngine,
) {
    fun render(body: EmailBody): String {
        val context = Context().apply { setVariables(body.variables) }
        return templateEngine.process(body.templateName, context)
    }
}