package ru.woowy.domain.model

class CharacterStateBuilder {
    private var need: Need? = null

    private fun isComplete(): Boolean = need != null

    fun build(): CharacterState = CharacterState(need = need)

    fun need(event: NeedUpdatedEvent): CharacterStateBuilder {
        need =
            Need(
                hunger = event.hunger,
                sleep = event.sleep,
                body = event.body,
                mental = event.mental,
                social = event.social,
                health = event.health,
                happiness = event.happiness,
            )

        return this
    }
}