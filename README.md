# Traffic Light Simulator with Java

A console-based Traffic Management System built in Java as part of a
[Hyperskill](https://hyperskill.org/) project. It simulates a road junction
where roads are added to a circular queue and opened/closed in turn at a
user-defined interval, with a background thread tracking elapsed time and
rendering live system status.

## Features

- Interactive menu for adding/removing roads and viewing live system status
- Validated input (positive integers, menu range 0-3)
- A dedicated `QueueThread` that ticks every second, independent of the menu
- A fixed-capacity circular queue of roads with FIFO add/delete
- Rotating "open" road with a per-road open/closed countdown that recalculates
  automatically as roads are added or removed

## Running

The project is built with Gradle and exercised through the Hyperskill test
harness rather than a standalone `run` task:

```bash
# Run the test suite for the active stage
./gradlew :Traffic_Light_Simulator_with_Java-task:test

# Build without running tests
./gradlew :Traffic_Light_Simulator_with_Java-task:build
```

## Project layout

```
Traffic Light Simulator with Java/
  task/
    src/traffic/Main.java   ← production source (entry point)
    test/                   ← Hyperskill test suite (GlobalTests, SystemOutput)
  */task.html               ← per-stage requirements
build.gradle, settings.gradle
util/                       ← Hyperskill test infrastructure
```

See [`CLAUDE.md`](CLAUDE.md) for a deeper architectural walkthrough and
[`CHANGELOG.md`](CHANGELOG.md) for the stage-by-stage history.

## Stages

The project was built incrementally across six Hyperskill stages, each adding
a layer of functionality on top of the last — from a static welcome menu to a
fully animated, multi-threaded traffic light. All six stages are implemented
and pass the bundled test suite.
