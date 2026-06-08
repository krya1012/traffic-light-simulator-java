# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Hyperskill project — a console-based Traffic Light Simulator built in Java. Implements a multi-threaded road management system with a circular queue, progressing through 5 stages from a simple menu to a full animated traffic light.

## Commands

```bash
# Run all tests for the active task module
./gradlew :Traffic_Light_Simulator_with_Java-task:test

# Build without running tests
./gradlew :Traffic_Light_Simulator_with_Java-task:build

# Discover all Gradle modules
./gradlew projects
```

There is no `run` task configured; the program is exercised exclusively through the Hyperskill test harness (`StageTest` / `TestedProgram`).

## Structure

```
Traffic Light Simulator with Java/   ← lesson directory (Hyperskill)
  task/
    src/traffic/Main.java            ← sole production source file
    test/GlobalTests.java            ← actual stage-gated test runner (see Test Framework)
    test/SystemOutput.java           ← parses/validates "System" view output (time, settings, road lines)
  Open the control panel/task.html   ← Stage 1 requirements
  Set up the traffic light/task.html ← Stage 2 requirements
  Oops, wrong button/task.html       ← Stage 3 requirements
  Like a clockwork/task.html         ← Stage 4 requirements
  Over and over again/task.html      ← Stage 5 requirements
  Red, yellow, green/task.html       ← Stage 6 requirements (final)
build.gradle                         ← root build; applies hyperskill plugin
settings.gradle                      ← auto-discovers task dirs by src/test presence
util/                                ← Hyperskill test infrastructure (do not modify)
```

Gradle module name is derived by sanitising the relative path: the active module is always **`:Traffic_Light_Simulator_with_Java-task`**.

## Test Framework

Tests use the Hyperskill `hs-test` library (`com.github.hyperskill:hs-test`). Key classes:

- `StageTest` — base class; `@DynamicTest` methods are the test entries.
- `TestedProgram` — launches `Main.main()` in a subprocess; `pr.start()` gets stdout, `pr.execute(input)` sends stdin and returns the next output block.
- `CheckResult.correct()` / `CheckResult.wrong(message)` — pass/fail a test case.

`GlobalTests.java` is the single test runner that covers every stage at once: a `STAGE` field (currently `5`) selects which assertions are active, and each `@DynamicTest` method calls `ForStages(new int[]{...})`, which throws `TestPassed` (skips the test) when `STAGE` isn't in the list. **To advance to the next stage, bump `STAGE` in `GlobalTests.java`** — this is what activates the new assertions; `Main.java` must then be updated to satisfy them. `SystemOutput.parseStringInfo(...)` is a shared helper that parses the "System" view block (elapsed seconds, road count, interval, road lines) and is reused across the stage-4/5/6 assertions.

## Stage Summary

| Stage | Key requirement |
|-------|----------------|
| 1 – Open the control panel | Print 6-line welcome + menu (no input) |
| 2 – Set up the traffic light | Read `numberOfRoads` + `interval`; loop menu; options print stub text |
| 3 – Oops, wrong button | Validate positive-int inputs; validate menu option 0-3; clear console after each action |
| 4 – Like a clockwork | Spawn `QueueThread` (named `"QueueThread"`); it increments elapsed time every 1 s and prints system info when in System state; option 3 switches to System state; Enter returns to Menu |
| 5 – Over and over again | Implement circular queue (capacity = numberOfRoads) in `QueueThread`; options 1/2 add/delete roads; System view lists all roads |
| 6 – Red, yellow, green | Each road displays open/closed state and countdown; front of queue is open; timing recalculates on add/delete; ANSI colour optional |

## Workflow (per stage)

1. Read `task.html` (strip HTML) to understand requirements.
2. Bump `STAGE` in `test/GlobalTests.java` to the new stage number, then read the `@DynamicTest` methods gated by that number (via `ForStages`) to see exact assertions and input sequences.
3. Read `src/traffic/Main.java` before editing.
4. Implement only what the current stage requires.
5. Add one-sentence JavaDoc to every new or modified public class and method.
6. Run `./gradlew :Traffic_Light_Simulator_with_Java-task:test`.
7. Fix failures; repeat until `BUILD SUCCESSFUL`.
8. Commit: `"Stage N: <title> — <one-line summary>"`.
9. Wait for user confirmation before starting the next stage.
