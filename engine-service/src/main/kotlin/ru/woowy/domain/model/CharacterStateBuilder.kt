package ru.woowy.domain.model

class CharacterStateBuilder {
    private var game: GamePreview? = null
    private var need: NeedPreview? = null

    fun game(event: WorldTickEvent): CharacterStateBuilder = apply {
        game = GamePreview(tickNumber = event.tickNumber, gameTime = event.gameTime)
    }

    fun need(event: NeedUpdatedEvent): CharacterStateBuilder = apply {
        need =
            NeedPreview(
                hunger = event.hunger,
                sleep = event.sleep,
                body = event.body,
                mental = event.mental,
                social = event.social,
                health = event.health,
                happiness = event.happiness,
            )
    }

    fun build(): CharacterState = CharacterState(game = game, need = need)
}