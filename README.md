# Asteroids Game

## How to build
`./gradlew clean build`

## How to run
Run `desktop/src/com/mygdx/game/DesktopLauncher.java`

## Configuration Game
You can customize the game. Change constants in `core/src/com/mygdx/asteroids/screen/MainGameScreen.java` to change the number of asteroids,
the number of rounds or bullet speed. 
- `MAX_ASTEROIDS_COUNT` - the maximum number of asteroids on the screen.
- `MAX_BULLET_COUNT` - the maximum number of bullets that can be on the screen at the same time.
- `SHOOT_WAIT_TIME` - The maximum number of bullets that can be on the screen at the same time.
- `HEALTH_COUNT` - starting number of player's lives

## Controls
- `W` - move up
- `S` - move down
- `A` - move left
- `D` - move right
- `SPACE` - fire. You can press `SPACE` for single shot or hold for a series of shots.