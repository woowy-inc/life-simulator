package ru.woowy.presentation.web.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.woowy.presentation.web.RestEndpoint

@RestController
@RequestMapping(RestEndpoint.BASE_URL)
internal class CharacterController